package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFTopicController;
import com.callforcode.greenfarm.entity.GFComment;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.service.api.GFTopicService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class GFTopicControllerImpl implements GFTopicController {

    private GFTopicService service;

    @Override
    public ResultVo<List<GFTopicVo>> queryByCommunityId(String userName, String communityId) {
        if (Objects.isNull(communityId) || "".equals(communityId) || Objects.isNull(userName) || "".equals(userName)) {
            throw new GFIllegalArgumentException("query topic failed , communityId or username can not be null!");
        }
        List<GFTopicVo> gfTopics = service.queryTopicWithLikeByCommunityIdAndUserName(userName, Integer.valueOf(communityId));
        return BeanUtils.createResultVo(gfTopics);

    }

    @Override
    public ResultVo<List<GFTopicVo>> queryByUserName(String userName) {
        if (Objects.isNull(userName) || "".equals(userName)) {
            throw new GFIllegalArgumentException("query topic failed , username can not be null!");
        }
        return BeanUtils.createResultVo(service.queryTopicWithLikeByUserName(userName));
    }

    @Override
    public ResultVo<GFTopicVo> insert(GFTopicAddVo vo) {
        if (Objects.isNull(vo.getCommunityId()) || Objects.isNull(vo.getUsername())
            || "".equals(vo.getUsername()) || Objects.isNull(vo.getTopicName()) || "".equals(vo.getTopicName())) {
            throw new GFIllegalArgumentException("add topic failed,communityId/username/topicName can not be null !");
        }
        GFTopic gfTopic = service.addTopic(BeanUtils.copyBean(vo, GFTopic.class));
        if (Objects.isNull(gfTopic)) {
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("add topic error");
            resultVo.setResultCode(ResultCode.FAULT);
            return resultVo;
        }
        return BeanUtils.createResultVo(BeanUtils.copyBean(gfTopic, GFTopicVo.class));
    }

    @Override
    public ResultVo update(GFTopicVo vo) {
        if (Objects.isNull(vo.getTopicId())) {
            throw new GFIllegalArgumentException("update topic failed ,topicId can not be null! ");
        }
        service.updateTopic(BeanUtils.copyBean(vo, GFTopic.class));
        return new ResultVo();
    }

    @Override
    public ResultVo addComment(GFCommentAddVo gfCommentVo) {
        if (Objects.isNull(gfCommentVo.getTopicId()) || Objects.isNull(gfCommentVo.getUsername())
            || gfCommentVo.getUsername().equals("") || Objects.isNull(gfCommentVo.getCommentContent())
            || gfCommentVo.getCommentContent().equals("")) {
            throw new GFIllegalArgumentException("add topic_comment failed,topicId/username/commentContent can not be null !");

        }
        service.addComment(BeanUtils.copyBean(gfCommentVo, GFComment.class));
        return new ResultVo();
    }

    @Override
    public ResultVo<List<GFCommentVo>> queryComment(String topicId) {
        if (Objects.isNull(topicId) || "".equals(topicId)) {
            throw new GFIllegalArgumentException("query topic_comment failed , topicId can not be null!");
        }
        List<GFComment> gfComments = service.queryCommentByTopicId(Integer.valueOf(topicId));
        return BeanUtils.createResultVo(BeanUtils.copyListBean(gfComments, GFCommentVo.class));
    }

    @Override
    public ResultVo<GFTopicVo> queryByTopicId(@RequestParam(value = "username") String userName, @RequestParam(value = "topicId") String topicId) {
        if (Objects.isNull(topicId) || "".equals(topicId) || Objects.isNull(userName) || "".equals(userName)) {
            throw new GFIllegalArgumentException("query topic info failed,topicId or username can not be null!");
        }
        ResultVo<GFTopicVo> resultVo = service.queryTopicInfoById(userName, Integer.valueOf(topicId));
        return resultVo;

    }

    @Override
    public ResultVo like(String topicId, String isLike, String userName) {
        if (Objects.isNull(topicId) || Objects.isNull(isLike) || Objects.isNull(userName) || "".equals(userName) || "".equals(isLike)) {
            throw new GFIllegalArgumentException("add topic like  failed,topicId/userName/isLike can not be null!");
        }
        service.topicLike(userName, Integer.valueOf(topicId), isLike);
        return new ResultVo();
    }
}
