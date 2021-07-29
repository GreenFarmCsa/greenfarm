package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.*;
import com.callforcode.greenfarm.repository.api.*;
import com.callforcode.greenfarm.service.api.GFRentService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFRentFarmVo;
import com.callforcode.greenfarm.vo.GFRentVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GFRentServiceImpl implements GFRentService {

    @Autowired
    private GFRentRepository repository;

    @Autowired
    private GFFarmRepository farmRepository;

    @Autowired
    private GFLandRepository landRepository;

    @Autowired
    private GFUserRepository userRepository;

    @Autowired
    private GFSeedRepository seedRepository;

    @Autowired
    private GFPlantTaskRepository plantTaskRepository;

    @Override
    public void addRent(GFRent gfRent) {
        Boolean isRent = false;
        //land info
        GFLand gfLand = landRepository.queryLandById(gfRent.getLandId());
        //farm info
        GFFarmWithBLOBs gfFarmWithBLOBs = farmRepository.queryByFarmId(gfRent.getFarmId());
        GFSeed gfSeed = seedRepository.querySeedBySeedId(gfRent.getSeedId());
        if (Objects.isNull(gfFarmWithBLOBs)) {
            // farm not found
            throw new GFException("the farm info not found.farmId=" + gfRent.getFarmId());
        }
        if (Objects.nonNull(gfLand)) {
            isRent = gfLand.getIsRent();
        } else {
            //  land not found
            throw new GFException("the land info not found.landId=" + gfRent.getLandId() + "&farmId=" + gfRent.getFarmId());
        }
        if (Objects.isNull(gfSeed)) {
            // seed not found
            throw new GFException("the seed info not found.seedId=" + gfRent.getSeedId());
        }
        //land is rent
        if (isRent) {
            throw new GFException("the land is rent!.landId=" + gfLand.getLandId() + "&farmId=" + gfRent.getFarmId());
        }
        int res = repository.addRent(gfRent);

        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("add rent error", 1, res);
        } else {
            //update land rent status
            GFLand land = new GFLand();
            land.setIsRent(true);
            land.setLandId(gfRent.getLandId());
            int updRes = landRepository.updateLand(land);
            if (updRes != 1) {
                throw new UpdateRecordCountNotMatchException("update Land error", 1, res);
            }
        }
    }

    @Override
    public List<GFRentFarmVo> queryRentLands(String userName) {
        List<GFRent> gfRent = repository.queryRentLands(userName);
        final List<GFRentFarmVo> rentFarmVos = new ArrayList<>();
        Set<Integer> farmIds = new HashSet<>();
        if (gfRent != null && gfRent.size() > 0) {
            gfRent.forEach(f -> {
                farmIds.add(f.getFarmId());
            });
            if (farmIds.size() > 0) {
                farmIds.forEach(farmId -> {
                    GFFarmWithBLOBs gfFarm = farmRepository.queryByFarmId(farmId);
                    if (Objects.isNull(gfFarm)) {
                        throw new FarmNotFoundException("farm not found.farm_id is " + farmId);
                    }

                    //fill farm info
                    List<String> imageUrls = new ArrayList<String>();
                    GFRentFarmVo gfRentFarmVo = BeanUtils.copyBean(gfFarm, GFRentFarmVo.class);
                    if (gfFarm.getImageUrl() != null) {
                        for (String url : gfFarm.getImageUrl().split(",")) {
                            imageUrls.add(url);
                        }
                    }
                    gfRentFarmVo.setImageUrls(imageUrls);
                    //fill farmOwer info
                    GFUser gfUser = userRepository.queryUserInfo(gfFarm.getUsername());
                    if (Objects.isNull(gfUser)) {
                        throw new UserNotFoundException("user info not found.username=" + gfFarm.getUsername());
                    }
                    gfRentFarmVo.setPhone(gfUser.getPhone());
                    gfRentFarmVo.setEmail(gfUser.getEmail());
                    //fill rentland info
                    List<GFRent> rents = repository.queryRentLandsByUsernameAndFarmId(userName, farmId);
                    if (Objects.isNull(rents) || rents.size() == 0) {
                        throw new LandNotFoundException("query rentLands failed.userName=" + userName + "&farId=" + farmId);
                    } else {
                        gfRentFarmVo.setLands(BeanUtils.copyListBean(rents, GFRentVo.class));
                    }
                    rentFarmVos.add(gfRentFarmVo);
                });
            }
            return rentFarmVos;
        } else {
            throw new RentNotFoundException("rentFarm info not found.userName=" + userName);
        }

    }

    @Override
    public GFBoxConfirmInfo queryLandSubscriber(String taskId) {
        GFPlantTask gfPlantTask = plantTaskRepository.queryTaskByTaskId(Integer.valueOf(taskId));
        if (Objects.isNull(gfPlantTask)) {
            throw new PlantTaskNotFoundException("PlantTask info not found.taskId=" + taskId);
        }
        GFRent gfRent = repository.querySubscribeInfoByLandId(gfPlantTask.getLandId());
        if (Objects.isNull(gfRent)) {
            return new GFBoxConfirmInfo(new GFUser(), new GFFarm());
        }
        GFUser gfUser = userRepository.queryUserInfo(gfRent.getUsername());
        GFFarm gfFarm = farmRepository.queryByFarmId(gfRent.getFarmId());
        return new GFBoxConfirmInfo(gfUser, gfFarm);
    }

}
