package com.callforcode.greenfarm.fabric;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Lazy
public class FabricClient {

    private FabricProp prop;

    @Value("${green-farm.ibm.blockchain.enable}")
    private Boolean enabled;

    private HFClient client;

    private Channel channel;

    public FabricClient(FabricProp prop) {
        this.prop = prop;
    }

    // Initialize the fabric client
    @PostConstruct
    public void init() {
        if (!enabled) {
            return;
        }

        // Build a fabric user
        LocalUser user = new LocalUser(prop.getName(), prop.getMspId(), FabricUtil.getFile(prop.getKeyPath()),
                FabricUtil.getFile(prop.getCertPath()));
        try {
            client = HFClient.createNewInstance();
            client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
            client.setUserContext(user);
            channel = client.newChannel(prop.getChannelName());
            // Load the peer node
            for (Node info : prop.getPeers()) {
                Properties peerProp = new Properties();
                peerProp.put("pemBytes", FabricUtil.getFile(info.getPemPath()).getBytes());
                peerProp.setProperty("sslProvider", "openSSL");
                peerProp.setProperty("negotiationType", "TLS");
                peerProp.setProperty("ssl-target-name-override", info.getHostName());
                peerProp.setProperty("hostnameOverride", info.getHostName());
                Peer peer = client.newPeer(info.getName(), info.getUrl(), peerProp);
                channel.addPeer(peer);
            }
            if (prop.getOrderers() != null) {
                // Load the orderer node
                for (Node info : prop.getOrderers()) {
                    Properties ordererProp = new Properties();
                    ordererProp.put("pemBytes", FabricUtil.getFile(info.getPemPath()).getBytes());
                    ordererProp.setProperty("sslProvider", "openSSL");
                    ordererProp.setProperty("negotiationType", "TLS");
                    ordererProp.setProperty("ssl-target-name-override", info.getHostName());
                    ordererProp.setProperty("hostnameOverride", info.getHostName());
                    Orderer orderer = client.newOrderer(info.getName(), info.getUrl(), ordererProp);
                    channel.addOrderer(orderer);
                }
            }
            channel.initialize();
            log.info("Initialization of the fabric client is complete");
        } catch (Exception e) {
            log.error("Initialization of the fabric client failed", e);
        }
    }

    // insertOrUpdate data
    // jsonStr:{"farmLocation":"location","farmName":"farmName","imageHash":"256
    // bits of
    // hash","landArea":1,"landId":1,"plantStepName":"stepName","time":"2012-12-13","userName":"yanan","vedioHash":"256
    // bits of hash"}
    public boolean insertOrUpdate(String jsonStr) {
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("mycon").build();
        req.setChaincodeID(cid);
        req.setFcn("insertOrUpdate");
        req.setArgs(jsonStr);
        try {
            Collection<ProposalResponse> rsp = channel.sendTransactionProposal(req);
            TransactionEvent event = channel.sendTransaction(rsp).get();
            if (rsp.iterator().next().getStatus().getStatus() == 200) {
                return true;
            } else {
                log.error("fabric select error:" + rsp.iterator().next().getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("fabric operate error", e);
        }
        return false;
    }

    // select data
    public List<Pojo> select(String productId) {
        if (productId == null || "".equals(productId)) {
            return null;
        }
        QueryByChaincodeRequest req = client.newQueryProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("mycon").build();
        req.setChaincodeID(cid);
        req.setFcn("select");
        req.setArgs(productId);
        try {
            ProposalResponse[] rsp = channel.queryByChaincode(req).toArray(new ProposalResponse[0]);
            if (rsp[0].getStatus().getStatus() == 200) {
                String content = rsp[0].getProposalResponse().getResponse().getPayload().toStringUtf8();
                List<Pojo> list = JSON.parseArray(content, Pojo.class);
                return list;
            } else {
                log.error("fabric select error:" + rsp[0].getMessage());
            }
        } catch (Exception e) {
            log.error("fabric select error.", e);
        }
        return null;
    }

}
