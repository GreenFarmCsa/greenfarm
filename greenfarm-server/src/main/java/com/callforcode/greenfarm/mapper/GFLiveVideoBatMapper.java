package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFLiveVideoBat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GFLiveVideoBatMapper {

    int insert(GFLiveVideoBat record);

    int insertSelective(GFLiveVideoBat record);

    void deleteLiveVideoMappingByLiveId(Integer liveId);

}
