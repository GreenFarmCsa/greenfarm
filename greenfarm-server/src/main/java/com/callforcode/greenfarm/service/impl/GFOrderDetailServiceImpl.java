package com.callforcode.greenfarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFOrderDetail;
import com.callforcode.greenfarm.repository.api.GFOrderDetailRepository;
import com.callforcode.greenfarm.service.api.GFOrderDetailService;

public class GFOrderDetailServiceImpl implements GFOrderDetailService {
    @Autowired
    private GFOrderDetailRepository repository;

    @Override
    public int insertList(List<GFOrderDetail> list) {
        return repository.insertList(list);
    }

}
