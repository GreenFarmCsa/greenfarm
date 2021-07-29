package com.callforcode.greenfarm.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;
import com.callforcode.greenfarm.mapper.GFProductMapper;
import com.callforcode.greenfarm.mapper.GFShoppingCartMapper;
import com.callforcode.greenfarm.repository.api.GFShoppingCartRepository;
import com.callforcode.greenfarm.util.DateToolUtils;

public class GFShoppingCartRepositoryImpl implements GFShoppingCartRepository {
    @Autowired
    private GFShoppingCartMapper mapper;

    @Autowired
    private GFProductMapper productMapper;

    @Override
    public List<GFShoppingCart> queryGFShoppingCartByConditions(GFShoppingCart record) {
        return mapper.selectByWhere(record);
    }

    @Override
    public int insertShoppingCart(GFShoppingCart record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.insert(record);
    }

    @Override
    public List<GFProduct> selectCartRlnProductInfo(String userName) {
        return productMapper.selectCartRlnProductInfo(userName);
    }

    @Override
    public int updateByPidAndUserName(GFShoppingCart record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        record.setCreateTime(null);
        return mapper.updateByPidAndUserName(record);
    }

    @Override
    public int deleteByPidAndUserName(GFShoppingCart record) {
        return mapper.deleteByPidAndUserName(record);
    }

    @Override
    public List<GFShoppingCartProductTree> selectByUserName(GFShoppingCart record) {
        return mapper.selectByUserName(record);
    }
}
