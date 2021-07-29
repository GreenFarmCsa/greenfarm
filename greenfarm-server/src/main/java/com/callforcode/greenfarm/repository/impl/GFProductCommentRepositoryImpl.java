package com.callforcode.greenfarm.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.callforcode.greenfarm.entity.GFProductComment;
import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;
import com.callforcode.greenfarm.mapper.GFProductCommentMapper;
import com.callforcode.greenfarm.repository.api.GFProductCommentRepository;
import com.callforcode.greenfarm.util.DateToolUtils;

public class GFProductCommentRepositoryImpl implements GFProductCommentRepository {
    @Autowired
    private GFProductCommentMapper mapper;

    @Override
    public int deleteByCommentId(Integer commentId) {
        return mapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public int add(GFProductCommentWithBLOBs record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return mapper.insert(record);
    }

    @Override
    public int addProductCommentWithBLOBs(GFProductCommentWithBLOBs record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return mapper.insertSelective(record);
    }

    @Override
    public GFProductCommentWithBLOBs queryByCommentId(Integer commentId) {
        return mapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int updateByProductCommentWithBLOBsSelective(GFProductCommentWithBLOBs record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByProductCommentWithBLOBsWithBLOBs(GFProductCommentWithBLOBs record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByProductComment(GFProductComment record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<GFProductCommentWithBLOBs> queryByProductCommentWithBLOBs(GFProductCommentWithBLOBs record) {
        return mapper.selectByWhere(record);
    }

}
