package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.vo.GFTopicVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GFTopicMapper {
    int deleteByPrimaryKey(Integer topicId);

    int insert(GFTopic record);

    int insertSelective(GFTopic record);

    GFTopic selectByPrimaryKey(Integer topicId);

    int updateByPrimaryKeySelective(GFTopic record);

    int updateByPrimaryKey(GFTopic record);

    List<GFTopic> selectByUsername(String userName);

    List<GFTopic> selectByCommunityId(Integer communityId);

    List<GFTopic> selectByWhere(GFTopic gfTopic);

    Integer getCommentsCountByTopicId(Integer topicId);

    List<GFTopicVo> queryTopicsWithLikeByUsername(String userName);

    List<GFTopicVo> selectWithLikeByCommunityIdAndUserName(@Param("userName") String userName, @Param("communityId") Integer communityId);

}
