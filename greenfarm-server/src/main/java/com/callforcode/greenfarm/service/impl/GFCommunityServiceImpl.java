package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.repository.api.GFCommunityRepository;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.repository.api.GFTopicRepository;
import com.callforcode.greenfarm.service.api.GFCommunityService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFCommunityTopicVo;
import com.callforcode.greenfarm.vo.GFCommunityVo;
import com.callforcode.greenfarm.vo.GFTopicVo;
import com.callforcode.greenfarm.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GFCommunityServiceImpl implements GFCommunityService {

    @Autowired
    private GFCommunityRepository repository;

    @Autowired
    private GFTopicRepository topicRepository;

    @Autowired
    private GFFarmRepository farmRepository;

    @Override
    public GFCommunity queryCommunityInfoByFarmId(Integer farmId) {
        return repository.queryCommunityInfoByFarmId(farmId);
    }

    @Override
    public List<GFCommunityVo> queryCommunityInfoByUsername(String username) {
        List<GFCommunity> communities = repository.queryCommunityInfoByUsername(username);
        List<GFCommunityVo> vos = new ArrayList<>();
        if (Objects.nonNull(communities) && communities.size() > 0) {
            communities.forEach(community -> {
                GFFarmWithBLOBs gfFarm = farmRepository.queryByFarmId(community.getFarmId());
                GFCommunityVo vo = BeanUtils.copyBean(community, GFCommunityVo.class);
                if (Objects.nonNull(vo)) {
                    vo.setFarmName(gfFarm.getFarmName());
                }
                vos.add(vo);
            });
        }
        return vos;
    }

    @Override
    public int addCommunityMember(GFCommunityMember gfCommunityMember) {
        if (repository.checkIfCommunityMemberExistsByIdAndUserName(gfCommunityMember) > 0) {
            return 1;
        }

        int res = repository.addCommunityMember(gfCommunityMember);
        if (res == 1) {
            return res;
        } else {
            throw new UpdateRecordCountNotMatchException("join  community error", 1, res);
        }
    }

    @Override
    public ResultVo<GFCommunityTopicVo> queryTopicCommunityByFarmId(Integer farmId) {
        ResultVo<GFCommunityTopicVo> resultVo = new ResultVo<>();
        GFCommunity community = repository.queryCommunityInfoByFarmId(farmId);
        if (Objects.nonNull(community)) {
            GFCommunityTopicVo communityTopicVo = BeanUtils.copyBean(community, GFCommunityTopicVo.class);
            List<GFTopic> gfTopics = topicRepository.queryTopicsByCommunityId(community.getCommunityId());
            communityTopicVo.setTopics(BeanUtils.copyListBean(gfTopics, GFTopicVo.class));
            resultVo.setData(communityTopicVo);
        }
        return resultVo;
    }

}
