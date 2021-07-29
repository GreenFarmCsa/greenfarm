package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.api.GFRentController;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.CommunityNotFoundException;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.service.api.GFCommunityService;
import com.callforcode.greenfarm.service.api.GFPlantTaskService;
import com.callforcode.greenfarm.service.api.GFRentService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.GFBoxConfirmVo;
import com.callforcode.greenfarm.vo.GFRentAddVo;
import com.callforcode.greenfarm.vo.GFRentFarmVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class GFRentControllerImpl implements GFRentController {

    private GFRentService rentService;

    private GFPlantTaskService plantTaskService;

    private GFCommunityService communityService;

    @Transactional
    @Override
    public ResultVo createRent(GFRentAddVo gfRentVo) {
        if (gfRentVo.getLandId() == null || gfRentVo.getFarmId() == null
                ||
                gfRentVo.getUsername() == null || gfRentVo.getUsername().equals("")) {
            throw new GFIllegalArgumentException("add rent failed,landId/username/farmId can not be null!");
        }
        //init rent_start_time
        Date rentStartTime = DateToolUtils.getCurrDateWithoutTime();
        gfRentVo.setRentStartTime(rentStartTime);
        //init  rent_end_time , default : 365 days later
        gfRentVo.setRentEndTime(DateToolUtils.getAddDaysDateResult(rentStartTime, GreenFarmConst.GRF_RENT_DEFAULT_DAYS));
        rentService.addRent(BeanUtils.copyBean(gfRentVo, GFRent.class));
        //join community
        GFCommunityMember gfCommunityMember = new GFCommunityMember();
        GFCommunity community = communityService.queryCommunityInfoByFarmId(gfRentVo.getFarmId());
        if (Objects.isNull(community)) {
            throw new CommunityNotFoundException("the community info not found.farmId=" + gfRentVo.getFarmId());
        }
        gfCommunityMember.setCommunityId(community.getCommunityId());
        gfCommunityMember.setUsername(gfRentVo.getUsername());
        communityService.addCommunityMember(gfCommunityMember);
        //add plant task
        plantTaskService.addTask(gfRentVo.getLandId(), gfRentVo.getSeedId(), gfRentVo.getUsername(), GreenFarmConst.GRF_RENT_TYPE_SUBSCRIBE.equals(gfRentVo.getRemark()));
        return new ResultVo<>();
    }

    @Override
    public ResultVo<List<GFRentFarmVo>> queryRentLands(String userName) {
        if (userName == null || "".equals(userName)) {
            throw new GFIllegalArgumentException("query rentLand failed ,username can not be null!");
        }
        List<GFRentFarmVo> rentFarmVos = rentService.queryRentLands(userName);
        return BeanUtils.createResultVo(rentFarmVos);
    }

    @Override
    public ResultVo<GFBoxConfirmVo> querySubscriber(String taskId) {
        GFBoxConfirmInfo gfBoxConfirmInfo = rentService.queryLandSubscriber(taskId);
        GFBoxConfirmVo gfBoxConfirmVo = BeanUtils.copyBean(gfBoxConfirmInfo.getUser(), GFBoxConfirmVo.class);
        GFFarm farm = gfBoxConfirmInfo.getFarm();
        gfBoxConfirmVo.setFarmName(farm.getFarmName());
        gfBoxConfirmVo.setFarmOwner(farm.getUsername());
        gfBoxConfirmVo.setFarmLocation(farm.getLocation());
        return BeanUtils.createResultVo(gfBoxConfirmVo);
    }

}
