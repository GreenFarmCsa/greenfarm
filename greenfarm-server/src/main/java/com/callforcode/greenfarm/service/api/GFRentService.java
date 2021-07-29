package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFBoxConfirmInfo;
import com.callforcode.greenfarm.entity.GFRent;
import com.callforcode.greenfarm.vo.GFRentFarmVo;

import java.util.List;

public interface GFRentService {

    void addRent(GFRent gfRent);

    List<GFRentFarmVo> queryRentLands(String userName);

    GFBoxConfirmInfo queryLandSubscriber(String taskId);

}
