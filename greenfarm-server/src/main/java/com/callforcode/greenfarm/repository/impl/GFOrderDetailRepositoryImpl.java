package com.callforcode.greenfarm.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFOrderDetail;
import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.mapper.GFOrderDetailMapper;
import com.callforcode.greenfarm.repository.api.GFOrderDetailRepository;

public class GFOrderDetailRepositoryImpl implements GFOrderDetailRepository {
    @Autowired
    private GFOrderDetailMapper mapper;

    @Override
    public int insertList(List<GFOrderDetail> list) {
        return mapper.insertList(list);
    }

    @Override
    public List<GFProduct> queryProductByList(List<GFOrderDetail> list) {
        return mapper.selectProductByIdList(list);
    }

    @Override
    public int updateProductByList(List<GFProduct> list) {
        return mapper.updateProductByList(list);
    }
}
