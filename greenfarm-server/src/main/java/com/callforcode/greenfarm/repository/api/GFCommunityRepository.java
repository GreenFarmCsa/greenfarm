package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;

import java.util.List;

public interface GFCommunityRepository {

    GFCommunity queryCommunityInfoByFarmId(Integer farmId);

    List<GFCommunity> queryCommunityInfoByUsername(String username);

    int addCommunityMember(GFCommunityMember gfCommunityMember);

    int insertCommunityInfo(GFCommunity record);

    List<GFCommunity> selectByCommunityName(String communityName);

    int checkIfCommunityMemberExistsByIdAndUserName(GFCommunityMember gfCommunityMember);

}
