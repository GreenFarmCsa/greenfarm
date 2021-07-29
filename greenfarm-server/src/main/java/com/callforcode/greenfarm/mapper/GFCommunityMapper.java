package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFCommunity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFCommunityMapper {
    int deleteByPrimaryKey(Integer communityId);

    int insert(GFCommunity record);

    int insertSelective(GFCommunity record);

    GFCommunity selectByPrimaryKey(Integer communityId);

    int updateByPrimaryKeySelective(GFCommunity record);

    int updateByPrimaryKey(GFCommunity record);

    GFCommunity selectByFarmId(Integer farmId);

    List<GFCommunity> selectByUsername(String username);

    List<GFCommunity> selectByCommunityName(String communityName);

}
