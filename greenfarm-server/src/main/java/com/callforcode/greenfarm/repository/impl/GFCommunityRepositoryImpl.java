package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;
import com.callforcode.greenfarm.mapper.GFCommunityMapper;
import com.callforcode.greenfarm.mapper.GFCommunityMemberMapper;
import com.callforcode.greenfarm.repository.api.GFCommunityRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFCommunityRepositoryImpl implements GFCommunityRepository {

    @Autowired
    private GFCommunityMapper mapper;

    @Autowired
    private GFCommunityMemberMapper gfCommunityMemberMapper;

    @Override
    public GFCommunity queryCommunityInfoByFarmId(Integer farmId) {
        return mapper.selectByFarmId(farmId);
    }

    @Override
    public List<GFCommunity> queryCommunityInfoByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    @Override
    public int addCommunityMember(GFCommunityMember gfCommunityMember) {
        gfCommunityMember.setCreateTime(DateToolUtils.getCurrDate());
        gfCommunityMember.setModifyTime(gfCommunityMember.getCreateTime());
        return gfCommunityMemberMapper.insertSelective(gfCommunityMember);
    }

    @Override
    public int insertCommunityInfo(GFCommunity record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return mapper.insertSelective(record);

    }

    @Override
    public List<GFCommunity> selectByCommunityName(String communityName) {
        return mapper.selectByCommunityName(communityName);
    }

    @Override
    public int checkIfCommunityMemberExistsByIdAndUserName(GFCommunityMember gfCommunityMember) {
        return gfCommunityMemberMapper.checkIfCommunityMemberExistsByIdAndUserName(gfCommunityMember);
    }

}
