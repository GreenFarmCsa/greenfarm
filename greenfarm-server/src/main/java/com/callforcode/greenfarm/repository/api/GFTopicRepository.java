package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFComment;
import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFTopic;

import com.callforcode.greenfarm.vo.GFTopicVo;
import java.util.List;

public interface GFTopicRepository {
    int addTopic(GFTopic gfTopic);

    int updateTopic(GFTopic gfTopic);

    List<GFTopic> queryTopicsByUsername(String userName);

    List<GFTopic> queryTopicsByCommunityId(Integer communityId);

    List<GFTopic> selectByWhere(GFTopic gfTopic);

    int addComment(GFComment gfComment);

    List<GFComment> queryCommentByTopicId(Integer topicId);

    int deleteCommentsByTopicId(Integer topicId);

    GFTopic queryTopicInfoById(Integer topicId);

    Integer getCommentsCountByTopicId(Integer topicId);

    GFLike queryGFLikeByUserNameAndTopicId(String userName, Integer topicId);

    int insertLikeInfo(GFLike gfLike);

    int deleteLikeByLikeId(Integer likeId);

    List<GFTopicVo> queryTopicsWithLikeByUsername(String userName);

    List<GFTopicVo> selectWithLikeByCommunityIdAndUserName(String userName, Integer communityId);

}
