package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFProductComment;
import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;

@Mapper
public interface GFProductCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(GFProductCommentWithBLOBs record);

    int insertSelective(GFProductCommentWithBLOBs record);

    GFProductCommentWithBLOBs selectByPrimaryKey(Integer commentId);

    List<GFProductCommentWithBLOBs> selectByWhere(GFProductCommentWithBLOBs record);

    int updateByPrimaryKeySelective(GFProductCommentWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GFProductCommentWithBLOBs record);

    int updateByPrimaryKey(GFProductComment record);
}
