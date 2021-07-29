package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFLand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFLandMapper {

    int deleteByPrimaryKey(Integer landId);

    int insert(GFLand record);

    int insertSelective(GFLand record);

    GFLand selectByPrimaryKey(Integer landId);

    int updateByPrimaryKeySelective(GFLand record);

    int updateByPrimaryKey(GFLand record);

    List<GFLand> selectByFarmId(Integer farmId);

    GFLand selectByLandId(Integer landId);

}
