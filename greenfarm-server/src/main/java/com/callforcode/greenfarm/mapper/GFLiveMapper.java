package com.callforcode.greenfarm.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.callforcode.greenfarm.entity.GFLive;

@Mapper
public interface GFLiveMapper {
    int deleteByPrimaryKey(Integer liveId);

    int insert(GFLive record);

    int insertSelective(GFLive record);

    GFLive selectByPrimaryKey(Integer liveId);

    List<GFLive> selectByWhere(GFLive record);

    int updateByPrimaryKeySelective(GFLive record);

    int updateByPrimaryKey(GFLive record);

    List<GFLive> getLivesByMinutes(Date customDate);
    
    List<Integer> getLiveIdListBySeconds(Date customDate);
    
    int updateStatusByIdLiveList(@Param("idLiveList")List<Integer> idLiveList);
    
}
