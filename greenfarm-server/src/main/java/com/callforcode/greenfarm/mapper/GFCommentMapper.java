package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(GFComment record);

    int insertSelective(GFComment record);

    GFComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(GFComment record);

    int updateByPrimaryKeyWithBLOBs(GFComment record);

    int updateByPrimaryKey(GFComment record);

    List<GFComment> queryCommentByTopicId(Integer topicId);

    int deleteByTopicId(Integer topicId);

}
