package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;
import com.callforcode.greenfarm.vo.GFCommunityTopicVo;
import com.callforcode.greenfarm.vo.GFCommunityVo;
import com.callforcode.greenfarm.vo.ResultVo;

import java.util.List;

public interface GFCommunityService {

    GFCommunity queryCommunityInfoByFarmId(Integer farmId);

    List<GFCommunityVo> queryCommunityInfoByUsername(String username);

    int addCommunityMember(GFCommunityMember gfCommunityMember);

    ResultVo<GFCommunityTopicVo> queryTopicCommunityByFarmId(Integer farmId);

}
