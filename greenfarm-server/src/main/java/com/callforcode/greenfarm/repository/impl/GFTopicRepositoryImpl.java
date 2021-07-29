package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFComment;
import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.mapper.GFCommentMapper;
import com.callforcode.greenfarm.mapper.GFLikeMapper;
import com.callforcode.greenfarm.mapper.GFTopicMapper;
import com.callforcode.greenfarm.repository.api.GFTopicRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.GFTopicVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFTopicRepositoryImpl implements GFTopicRepository {

    @Autowired
    private GFTopicMapper mapper;

    @Autowired
    private GFLikeMapper gfLikeMapper;

    @Autowired
    private GFCommentMapper gfCommentMapper;

    @Override
    public int addTopic(GFTopic gfTopic) {
        gfTopic.setCreateTime(DateToolUtils.getCurrDate());
        gfTopic.setModifyTime(gfTopic.getCreateTime());
        //init likeSum
        gfTopic.setLikeSum(0);
        return mapper.insertSelective(gfTopic);
    }

    @Override
    public int updateTopic(GFTopic gfTopic) {
        gfTopic.setModifyTime(DateToolUtils.getCurrDate());
        gfTopic.setCreateTime(null);
        return mapper.updateByPrimaryKeySelective(gfTopic);
    }

    @Override
    public List<GFTopic> queryTopicsByUsername(String userName) {
        return mapper.selectByUsername(userName);
    }

    @Override
    public List<GFTopic> queryTopicsByCommunityId(Integer communityId) {
        return mapper.selectByCommunityId(communityId);
    }

    @Override
    public List<GFTopic> selectByWhere(GFTopic gfTopic) {
        return mapper.selectByWhere(gfTopic);
    }

    @Override
    public int addComment(GFComment gfComment) {
        gfComment.setCreateTime(DateToolUtils.getCurrDate());
        gfComment.setModifyTime(gfComment.getCreateTime());
        return gfCommentMapper.insertSelective(gfComment);
    }

    @Override
    public List<GFComment> queryCommentByTopicId(Integer topicId) {
        return gfCommentMapper.queryCommentByTopicId(topicId);
    }

    @Override
    public int deleteCommentsByTopicId(Integer topicId) {
        return gfCommentMapper.deleteByTopicId(topicId);
    }

    @Override
    public GFTopic queryTopicInfoById(Integer topicId) {
        return mapper.selectByPrimaryKey(topicId);
    }

    @Override
    public Integer getCommentsCountByTopicId(Integer topicId) {
        return mapper.getCommentsCountByTopicId(topicId);
    }

    @Override
    public GFLike queryGFLikeByUserNameAndTopicId(String userName, Integer topicId) {
        return gfLikeMapper.queryGFLikeByUserNameAndTopicId(userName, topicId);
    }

    @Override
    public int insertLikeInfo(GFLike gfLike) {
        return gfLikeMapper.insertSelective(gfLike);
    }

    @Override
    public int deleteLikeByLikeId(Integer likeId) {
        return gfLikeMapper.deleteByPrimaryKey(likeId);
    }

    @Override
    public List<GFTopicVo> queryTopicsWithLikeByUsername(String userName) {
        return mapper.queryTopicsWithLikeByUsername(userName);
    }

    @Override
    public List<GFTopicVo> selectWithLikeByCommunityIdAndUserName(String userName,
        Integer communityId) {
        return mapper.selectWithLikeByCommunityIdAndUserName(userName, communityId);
    }

}

