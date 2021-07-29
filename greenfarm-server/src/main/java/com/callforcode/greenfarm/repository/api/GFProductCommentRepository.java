package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFProductComment;
import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;

public interface GFProductCommentRepository {

    int deleteByCommentId(Integer commentId);

    int add(GFProductCommentWithBLOBs record);

    int addProductCommentWithBLOBs(GFProductCommentWithBLOBs record);

    GFProductCommentWithBLOBs queryByCommentId(Integer commentId);

    List<GFProductCommentWithBLOBs> queryByProductCommentWithBLOBs(GFProductCommentWithBLOBs record);

    int updateByProductCommentWithBLOBsSelective(GFProductCommentWithBLOBs record);

    int updateByProductCommentWithBLOBsWithBLOBs(GFProductCommentWithBLOBs record);

    int updateByProductComment(GFProductComment record);

}
