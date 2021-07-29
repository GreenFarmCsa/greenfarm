package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFFarm;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GFFarmMapper {
    int deleteByPrimaryKey(Integer farmId);

    int insert(GFFarmWithBLOBs record);

    int insertSelective(GFFarmWithBLOBs record);

    GFFarmWithBLOBs selectByPrimaryKey(Integer farmId);

    List<GFFarmWithBLOBs> selectByWhere(GFFarmWithBLOBs record);

    int updateByPrimaryKeySelective(GFFarmWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GFFarmWithBLOBs record);

    int updateByPrimaryKey(GFFarm record);

    List<GFFarmWithBLOBs> selectFarmInfoByCommentator(String userName);

    List<GFFarmWithBLOBs> selectByMulConditions(String searchText);

    List<GFFarmWithBLOBs> queryByTotalArea(@Param("startArea") Integer startArea, @Param("endArea") Integer endArea);

    int deleteFarmRlnTaskInfoByFarmId(@Param("farmId") Integer farmId);

    int deleteFarmRlnRentInfoByFarmId(@Param("farmId") Integer farmId);

    int deleteFarmRlnCommunityInfoByFarmId(@Param("farmId") Integer farmId);

    GFFarm queryByLandId(@Param("landId") Integer landId);
}
