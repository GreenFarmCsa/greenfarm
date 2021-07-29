## Fabric chain code deployment process


1. Sign in to a server that has a fabric service deployed.
2. Copy the fabric chain code source file under the github.com/chaincode/mycc path of the cli container using the following command.

> docker cp sourceFilePath cli:targetFilePath

3. Use the docker command to enter the cli container of the fabric network.

> docker exec -it cli /bin/bash

4. Once inside the container, follow the command below to install the chain code.

> peer chaincode install -n mycc -v 1.0 -p github.com/chaincode/mycc

5. Use the following command to switch the current peer node and install the chain code at all the peer nodes that require endorsement.

> export CORE_PEER_LOCALMSPID=Org1MSP
> export CORE_PEER_ADDRESS=peer1.org1.example.com:8051
> export CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/fabric/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
> export CORE_PEER_TLS_ROOTCERT_FILE=/etc/hyperledger/fabric/crypto-config/peerOrganizations/org2.example.com/peers/peer1.org2.example.com/tls/ca.crt

6. Instantiation contracts require only instantiation at one peer node.

> peer chaincode instantiate -o orderer.example.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycon -v 1.1 -c '{"Args":["init"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')"

7. Chain code upgrade, install new chain code source files at all endorsement nodes, refer to step.

8. Perform the following command for chain code upgrades, and the upgrade contract does not need to be instantiated again.

> peer chaincode upgrade -o orderer.example.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc  -v 1.1 -c '{"Args":["init"]}' -P "OR ('Org1MSP.peer','Org2MSP.peer')"

9. Chain code operations can be performed by reference to the official fabric documentation.

http://www.fabfile.org/
