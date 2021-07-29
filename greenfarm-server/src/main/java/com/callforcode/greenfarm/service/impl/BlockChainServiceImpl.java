package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.dto.BlockChainProductModel;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.fabric.FabricClient;
import com.callforcode.greenfarm.fabric.Pojo;
import com.callforcode.greenfarm.service.api.BlockChainService;
import com.callforcode.greenfarm.service.api.GFFarmService;
import com.callforcode.greenfarm.service.api.GFLandService;
import com.callforcode.greenfarm.service.api.GFPlantTaskService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.GFBlockChainProductVo;
import com.callforcode.greenfarm.vo.RevisePlantTaskVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class BlockChainServiceImpl implements BlockChainService {

    @Value("${green-farm.ibm.blockchain.enable}")
    private Boolean enabled;

    @Autowired
    private GFPlantTaskService plantTaskService;

    @Autowired
    private GFLandService landService;

    @Autowired
    private GFFarmService farmService;

    @Autowired
    private FabricClient fabricClient;

    private Executor executor = Executors.newCachedThreadPool();

    @Override
    public List<BlockChainProductModel> record(Integer productId) {
        if (enabled) {
            List<BlockChainProductModel> productModels = generateProductInfo(productId);
            BlockChainRecorder recorder = new BlockChainRecorder(productModels);
            executor.execute(recorder);
            return productModels;
        }
        return null;
    }

    @Override
    public List<BlockChainProductModel> generate(Integer productId) {
        return generateProductInfo(productId);
    }

    @Override
    public List<GFBlockChainProductVo> query(Integer productId) {
        return enabled ? convertBlockChainProductModel(fabricClient.select(String.valueOf(productId))) : null;
    }

    private List<BlockChainProductModel> generateProductInfo(Integer productId) {
        List<BlockChainProductModel> productModels = new ArrayList<>();
        GFPlantTask gfPlantTask = plantTaskService.queryPlantTaskByProductId(productId);

        if (Objects.isNull(gfPlantTask)) {
            return productModels;
        }
        List<GFTaskStep> gfTaskSteps = plantTaskService.queryTaskStep(gfPlantTask.getTaskId());
        Assert.notNull(gfPlantTask, "Cannot find task_step info.taskId=" + gfPlantTask.getTaskId());
        gfTaskSteps.forEach(step -> {
            BlockChainProductModel product = new BlockChainProductModel();
            GFTaskDetail gfTaskDetail = plantTaskService.queryTaskDetail(step.getStepId());
            Assert.notNull(gfTaskDetail, "Cannot find task_detail info.stepId=" + step.getStepId());
            product.setProductId(String.valueOf(productId));
            product.setPlantStepName(step.getPlantStep());
            product.setUserName(gfTaskDetail.getUsername());
            product.setTime(DateToolUtils.getDateStr(gfTaskDetail.getCreateTime(), DateToolUtils.FORMAT_FULL));

            GFLand gfLand = landService.queryLandByLandId(gfPlantTask.getLandId());
            Assert.notNull(gfLand, "Cannot find land info.landId=" + gfPlantTask.getLandId());
            product.setLandArea(gfLand.getArea());
            product.setLandId(gfLand.getLandId());

            GFFarm gfFarm = farmService.queryFarmByFarmId(gfLand.getFarmId());
            Assert.notNull(gfFarm, "Cannot find farm info.farmId=" + gfLand.getFarmId());
            product.setFarmName(gfFarm.getFarmName());
            product.setFarmLocation(gfFarm.getLocation());

            List<RevisePlantTaskVo.FileInfo> fileInfos = BeanUtils.convertOperateRecord(gfTaskDetail.getOperRecord());
            List<String> videosHash = new ArrayList<>();
            List<String> imagesHash = new ArrayList<>();
            if (Objects.nonNull(fileInfos)) {
                fileInfos.forEach(info -> {
                    String hash = sha256(info.getUrl());
                    if (GreenFarmConst.GRF_OPERATE_RECORD_TYPE_VIDEO.equals(info.getType())) {
                        videosHash.add(hash);
                    }
                    if (GreenFarmConst.GRF_OPERATE_RECORD_TYPE_IMAGE.equals(info.getType())) {
                        imagesHash.add(hash);
                    }
                });
            }
            product.setVideoHash(StringUtils.collectionToCommaDelimitedString(videosHash));
            product.setImageHash(StringUtils.collectionToCommaDelimitedString(imagesHash));
            productModels.add(product);
        });

        return productModels;
    }

    private String sha256(String filePath) {
        try {
            return DigestUtils.sha256Hex(new FileInputStream(new File(filePath)));
        } catch (IOException e) {
            log.error("sha256 hash error.", e);
        }
        return "";
    }

    private List<GFBlockChainProductVo> convertBlockChainProductModel(List<Pojo> pojoList) {
        List<GFBlockChainProductVo> list = new ArrayList<>();
        if (Objects.nonNull(pojoList)) {
            pojoList.forEach(p -> {
                GFBlockChainProductVo productVo = BeanUtils.copyBean(p, GFBlockChainProductVo.class);
                productVo.setCreateTime(p.getTime());
                productVo.setLocation(p.getFarmLocation());
                list.add(productVo);
            });
        }
        return list;
    }

    @AllArgsConstructor
    private class BlockChainRecorder implements Runnable {

        private List<BlockChainProductModel> productModels;

        @Override
        public void run() {
            try {
                productModels.forEach(p -> {
                    fabricClient.insertOrUpdate(BeanUtils.convertJson(p));
                });
            } catch (Exception e) {
                log.error("Fabric block-chain insert error.content=" + BeanUtils.convertJson(productModels), e);
            }
        }
    }

}
