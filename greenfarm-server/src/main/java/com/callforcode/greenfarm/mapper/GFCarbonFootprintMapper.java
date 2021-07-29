package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFCarbonFootprintMapper {

    int deleteByPrimaryKey(Integer footprintId);

    int insert(GFCarbonFootprint record);

    int insertSelective(GFCarbonFootprint record);

    GFCarbonFootprint selectByPrimaryKey(Integer footprintId);

    int updateByPrimaryKeySelective(GFCarbonFootprint record);

    int updateByPrimaryKey(GFCarbonFootprint record);

    List<GFCarbonFootprint> selectByUserName(String userName);

}
