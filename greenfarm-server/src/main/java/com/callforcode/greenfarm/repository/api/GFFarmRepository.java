package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFFarm;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;

import java.util.List;

public interface GFFarmRepository {

    int addFarmWithBLOBs(GFFarmWithBLOBs record);

    GFFarmWithBLOBs queryByFarmId(Integer farmId);

    List<GFFarmWithBLOBs> queryByFarmWithBLOBs(GFFarmWithBLOBs record);

    int updateByFarmWithBLOBsSelective(GFFarmWithBLOBs record);

    int updateByFarm(GFFarm record);

    List<GFFarmWithBLOBs> queryFarmInfoByCommentator(String userName);

    List<GFFarmWithBLOBs> queryByMulConditions(String searchText);

    List<GFFarmWithBLOBs> queryByTotalArea(String totalArea);

    int deleteFarmRlnInfoByFarmId(Integer farmId);

    GFFarm queryByLandId(Integer landId);
}
