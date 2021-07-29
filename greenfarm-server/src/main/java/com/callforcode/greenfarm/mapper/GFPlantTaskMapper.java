package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFPlantTask;

@Mapper
public interface GFPlantTaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(GFPlantTask record);

    int insertSelective(GFPlantTask record);

    GFPlantTask selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(GFPlantTask record);

    int updateByPrimaryKey(GFPlantTask record);

    int updateByPrimaryKeyWithBLOBs(GFPlantTask record);

    List<GFPlantTask> queryByUserName(String userName);
    
    GFPlantTask queryTaskByLandId(Integer landId);

    GFPlantTask queryPlantTaskByProductId(Integer productId);

}
