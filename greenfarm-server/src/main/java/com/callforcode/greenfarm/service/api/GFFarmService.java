package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;

import java.util.List;

public interface GFFarmService {

    int addFarmWithBLOBs(GFFarmWithBLOBs record);

    GFFarmWithBLOBs queryByFarmId(Integer farmId);

    List<GFFarmWithBLOBs> queryByFarmWithBLOBs(GFFarmWithBLOBs record);

    int updateByFarmWithBLOBsSelective(GFFarmWithBLOBs record);

    List<GFFarmWithBLOBs> queryByMulConditions(String searchText);

    GFFarmWithBLOBs queryFarmByFarmId(Integer farmId);

    List<GFFarmWithBLOBs> queryByTotalArea(String totalArea);

    int deleteFarmRlnInfoByFarmId(Integer farmId);

}
