package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.repository.api.GFCommunityRepository;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.service.api.GFFarmService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFFarmServiceImpl implements GFFarmService {

    @Autowired
    private GFFarmRepository repository;

    @Autowired
    private GFCommunityRepository commRepository;

    @Override
    public int addFarmWithBLOBs(GFFarmWithBLOBs record) {
        int farmCount = repository.addFarmWithBLOBs(record);
        String communityName = record.getFarmName();
        List<GFCommunity> communityList = commRepository.selectByCommunityName(communityName);
        if (communityList.size() == 0) {
            //create community
            GFCommunity gfCommunity = new GFCommunity();
            gfCommunity.setCommunityImageUrl(record.getImageUrl());
            gfCommunity.setFarmId(record.getFarmId());
            gfCommunity.setIntroduction(record.getIntroduction());
            gfCommunity.setCommunityName(communityName);
            int communityCount = commRepository.insertCommunityInfo(gfCommunity);
            //join community
            GFCommunityMember member = new GFCommunityMember();
            member.setUsername(record.getUsername());
            member.setCommunityId(gfCommunity.getCommunityId());
            if (commRepository.checkIfCommunityMemberExistsByIdAndUserName(member) == 0) {
                communityCount = commRepository.addCommunityMember(member);
            }
            if (farmCount > 0 && communityCount > 0) {
                return communityCount;
            }
        }
        return 0;
    }

    @Override
    public GFFarmWithBLOBs queryByFarmId(Integer farmId) {
        return repository.queryByFarmId(farmId);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByFarmWithBLOBs(GFFarmWithBLOBs record) {
        return repository.queryByFarmWithBLOBs(record);
    }

    @Override
    public int updateByFarmWithBLOBsSelective(GFFarmWithBLOBs record) {
        return repository.updateByFarmWithBLOBsSelective(record);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByMulConditions(String searchText) {
        return repository.queryByMulConditions(searchText);
    }

    @Override
    public GFFarmWithBLOBs queryFarmByFarmId(Integer farmId) {
        return repository.queryByFarmId(farmId);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByTotalArea(String totalArea) {
        return repository.queryByTotalArea(totalArea);
    }

    @Override
    public int deleteFarmRlnInfoByFarmId(Integer farmId) {
        return repository.deleteFarmRlnInfoByFarmId(farmId);
    }

}
