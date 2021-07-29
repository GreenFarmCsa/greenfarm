package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;

public interface GFProductCommentService {

    int deleteByCommentId(Integer commentId);

    int addProductCommentWithBLOBs(GFProductCommentWithBLOBs record);

    List<GFProductCommentWithBLOBs> queryByProductCommentWithBLOBs(GFProductCommentWithBLOBs record);

}
