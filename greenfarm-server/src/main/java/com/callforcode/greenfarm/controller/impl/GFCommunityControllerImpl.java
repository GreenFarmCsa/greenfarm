package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFCommunityController;
import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFCommunityMember;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.service.api.GFCommunityService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFCommunityMemberVo;
import com.callforcode.greenfarm.vo.GFCommunityTopicVo;
import com.callforcode.greenfarm.vo.GFCommunityVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class GFCommunityControllerImpl implements GFCommunityController {
    private GFCommunityService service;

    @Override
    public ResultVo<GFCommunityVo> queryByFarmId(String farmId) {

        if (Objects.isNull(farmId) || "".equals(farmId)) {
            throw new GFIllegalArgumentException("query community failed, farmId can not be null!");
        }

        GFCommunity community = service.queryCommunityInfoByFarmId(Integer.valueOf(farmId));
        return BeanUtils.createResultVo(BeanUtils.copyBean(community, GFCommunityVo.class));

    }

    @Override
    public ResultVo<List<GFCommunityVo>> queryByUserName(String userName) {

        if (Objects.isNull(userName) || "".equals(userName)) {
            throw new GFIllegalArgumentException("query community failed ,username can not be null!");
        }
        List<GFCommunityVo> gfCommunityGFCommunityVos = service.queryCommunityInfoByUsername(userName);

        return BeanUtils.createResultVo(gfCommunityGFCommunityVos);
    }

    @Override
    public ResultVo<?> join(GFCommunityMemberVo gfCommunityMemberVo) {

        if (Objects.isNull(gfCommunityMemberVo.getCommunityId()) || "".equals(gfCommunityMemberVo.getCommunityId())
            || Objects.isNull(gfCommunityMemberVo.getUsername()) || "".equals(gfCommunityMemberVo.getCommunityId())) {
            throw new GFIllegalArgumentException("join community failed ,communityId/username can not be null!");
        }
        GFCommunityMember member = BeanUtils.copyBean(gfCommunityMemberVo, GFCommunityMember.class);
        service.addCommunityMember(member);
        return new ResultVo<Object>();
    }

    @Override
    public ResultVo<GFCommunityTopicVo> queryTopicCommunityByFarmId(@RequestParam(value = "id") String farmId) {
        if (Objects.isNull(farmId) || "".equals(farmId)) {
            throw new GFIllegalArgumentException("query TopicCommunity failed ,id can not be null !");
        }
        ResultVo<GFCommunityTopicVo> resultVo = service.queryTopicCommunityByFarmId(Integer.valueOf(farmId));
        return resultVo;
    }

}
