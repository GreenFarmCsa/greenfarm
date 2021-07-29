package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFComment;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.vo.GFTopicVo;
import com.callforcode.greenfarm.vo.ResultVo;

import java.util.List;

public interface GFTopicService {

    GFTopic addTopic(GFTopic gfTopic);

    int updateTopic(GFTopic gfTopic);

    int addComment(GFComment copyBean);

    List<GFComment> queryCommentByTopicId(Integer topicId);

    ResultVo<GFTopicVo> queryTopicInfoById(String userName, Integer topicId);

    void topicLike(String userName, Integer topicId, String isLike);

    List<GFTopicVo> queryTopicWithLikeByUserName(String userName);

    List<GFTopicVo> queryTopicWithLikeByCommunityIdAndUserName(String userName, Integer communityId);

}
