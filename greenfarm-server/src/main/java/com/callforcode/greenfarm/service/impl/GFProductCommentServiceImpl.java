package com.callforcode.greenfarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;
import com.callforcode.greenfarm.repository.api.GFProductCommentRepository;
import com.callforcode.greenfarm.service.api.GFProductCommentService;

public class GFProductCommentServiceImpl implements GFProductCommentService {
    @Autowired
    private GFProductCommentRepository repository;

    @Override
    public int deleteByCommentId(Integer commentId) {
        return repository.deleteByCommentId(commentId);
    }

    @Override
    public int addProductCommentWithBLOBs(GFProductCommentWithBLOBs record) {
        return repository.addProductCommentWithBLOBs(record);
    }

    @Override
    public List<GFProductCommentWithBLOBs> queryByProductCommentWithBLOBs(GFProductCommentWithBLOBs record) {
        return repository.queryByProductCommentWithBLOBs(record);
    }

}
