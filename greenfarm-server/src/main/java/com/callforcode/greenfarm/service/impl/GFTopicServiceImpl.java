package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFComment;
import com.callforcode.greenfarm.entity.GFCommunity;
import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.exception.TopicNotFoundException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.repository.api.GFCommunityRepository;
import com.callforcode.greenfarm.repository.api.GFTopicRepository;
import com.callforcode.greenfarm.repository.api.GFUserRepository;
import com.callforcode.greenfarm.service.api.GFTopicService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFTopicVo;
import com.callforcode.greenfarm.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GFTopicServiceImpl implements GFTopicService {

    @Autowired
    private GFTopicRepository repository;

    @Autowired
    private GFUserRepository userRepository;

    @Autowired
    private GFCommunityRepository communityRepository;

    @Override
    public int updateTopic(GFTopic gfTopic) {
        int res = repository.updateTopic(gfTopic);
        if (res == 1) {
            return res;
        } else if (res == 0) {
            throw new TopicNotFoundException("topic not found.id=" + gfTopic.getTopicId());
        } else {
            throw new UpdateRecordCountNotMatchException("update topic error", 1, res);
        }
    }

    @Override
    public GFTopic addTopic(GFTopic gfTopic) {
        //check user is or not a member of the community
        List<GFCommunity> communities = communityRepository.queryCommunityInfoByUsername(gfTopic.getUsername());
        List<Integer> communityIds = new ArrayList<>();
        if (Objects.isNull(communities) || communities.size() == 0) {
            throw new GFException("the user has not joined into any community!.username=" + gfTopic.getUsername());
        } else {
            communities.forEach(community -> {
                communityIds.add(community.getCommunityId());
            });
            if (!communityIds.contains(gfTopic.getCommunityId())) {
                throw new GFException("the user is not a member of the community!.username=" + gfTopic.getUsername() + "&communityId=" + gfTopic.getCommunityId());
            }
        }

        int res = repository.addTopic(gfTopic);
        if (res == 1) {
            return gfTopic;
        }
        return null;
    }

    @Override
    public List<GFTopicVo> queryTopicWithLikeByCommunityIdAndUserName(String userName, Integer communityId) {
        return repository.selectWithLikeByCommunityIdAndUserName(userName, communityId);
    }

    @Override
    public int addComment(GFComment gfComment) {
        int res = repository.addComment(gfComment);
        if (res == 1) {
            return res;
        } else {
            throw new UpdateRecordCountNotMatchException("add topic_comment error", 1, res);
        }
    }

    @Override
    public List<GFComment> queryCommentByTopicId(Integer topicId) {
        return repository.queryCommentByTopicId(topicId);
    }

    @Override
    public ResultVo<GFTopicVo> queryTopicInfoById(String userName, Integer topicId) {
        GFTopic gfTopic = repository.queryTopicInfoById(topicId);
        if (Objects.isNull(gfTopic)) {
            return new ResultVo<>();
        } else {
            //get commentsCount of topic
            Integer commentsCount = repository.getCommentsCountByTopicId(topicId);
            //get user info
            GFUser user = userRepository.queryUserInfo(gfTopic.getUsername());
            // check user if like this topic
            GFLike gfLike = repository.queryGFLikeByUserNameAndTopicId(userName, topicId);
            GFTopicVo vo = BeanUtils.copyBean(gfTopic, GFTopicVo.class);
            if (Objects.nonNull(user)) {
                vo.setIconUrl(user.getIconUrl());
            }
            vo.setCommentsCount(commentsCount);
            vo.setHasLiked(gfLike != null);
            ResultVo<GFTopicVo> resultVo = new ResultVo<>();
            resultVo.setData(vo);
            return resultVo;
        }
    }

    @Override
    public void topicLike(String userName, Integer topicId, String isLike) {

        GFTopic topic = repository.queryTopicInfoById(topicId);
        GFTopic topicForUpdate = new GFTopic();
        if (Objects.isNull(topic)) {
            throw new GFException("add topic_like opt failed, the topic info not found.topicId=" + topicId + " username=" + userName);
        }
        if (!"true".equals(isLike) && !"false".equals(isLike)) {
            throw new GFException(" the value of isLike  can only be true/false!");
        }
        // get the like record
        GFLike gFlike = repository.queryGFLikeByUserNameAndTopicId(userName, topicId);

        if ("true".equals(isLike)) {
            if (gFlike != null) {
                throw new GFException("add topic_like opt failed, you already liked it");
            }
            gFlike = new GFLike();
            gFlike.setTopicId(topicId);
            gFlike.setUsername(userName);
            int optRes = repository.insertLikeInfo(gFlike);
            if (optRes != 1) {
                throw new GFException("like topic failed");
            }
            topicForUpdate.setTopicId(topicId);
            topicForUpdate.setLikeSum((topic.getLikeSum() == null ? 0 : topic.getLikeSum()) + 1);
            int updRes = repository.updateTopic(topicForUpdate);
            if (updRes != 1) {
                throw new GFException("update topic failed,the topic info not found.topicId=" + topicId);
            }
        } else {
            if (gFlike == null) {
                throw new GFException("add topic_like opt failed, you don't have thumb up");
            }
            int optRes = repository.deleteLikeByLikeId(gFlike.getLikeId());
            if (optRes != 1) {
                throw new GFException("cancel the thumb up failed");
            }
            topicForUpdate.setTopicId(topicId);
            topicForUpdate.setLikeSum(((topic.getLikeSum() == null ? 0 : topic.getLikeSum())) == 0 ? 0 : topic.getLikeSum() - 1);
            int updRes = repository.updateTopic(topicForUpdate);
            if (updRes != 1) {
                throw new GFException("update topic failed,the topic info not found.topicId=" + topicId);
            }
        }
    }

    @Override
    public List<GFTopicVo> queryTopicWithLikeByUserName(String userName) {
        return repository.queryTopicsWithLikeByUsername(userName);
    }

}
