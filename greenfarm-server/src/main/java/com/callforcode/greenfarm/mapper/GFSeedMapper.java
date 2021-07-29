package com.callforcode.greenfarm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFSeed;

import java.util.List;

@Mapper
public interface GFSeedMapper {

    int deleteByPrimaryKey(Integer seedId);

    int insert(GFSeed record);

    int insertSelective(GFSeed record);

    GFSeed selectByPrimaryKey(Integer seedId);

    int updateByPrimaryKeySelective(GFSeed record);

    int updateByPrimaryKeyWithBLOBs(GFSeed record);

    int updateByPrimaryKey(GFSeed record);

    List<GFSeed> selectAll();

}
