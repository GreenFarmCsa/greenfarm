package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.dto.GFOrderCartRlnProductDTO;
import com.callforcode.greenfarm.entity.GFOrder;
import com.callforcode.greenfarm.entity.OrderDetailProductTree;
import com.callforcode.greenfarm.entity.OrderUserTree;
import com.callforcode.greenfarm.mapper.GFOrderMapper;
import com.callforcode.greenfarm.mapper.GFProductMapper;
import com.callforcode.greenfarm.repository.api.GFOrderRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFOrderRepositoryImpl implements GFOrderRepository {

    @Autowired
    private GFOrderMapper gforderMapper;

    @Autowired
    private GFProductMapper productMapper;

    @Override
    public int insertOrder(GFOrder record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(DateToolUtils.getCurrDate());
        return gforderMapper.insert(record);
    }

    @Override
    public List<OrderDetailProductTree> queryTreeByConditions(GFOrder record) {
        return gforderMapper.findTreeByWhere(record);
    }

    @Override
    public List<OrderUserTree> selectByOrderIdList(List<Integer> orderIdList) {
        return gforderMapper.selectByOrderIdList(orderIdList);
    }

    @Override
    public List<GFOrderCartRlnProductDTO> selectOrderRlnProductInfo(String userName) {
        return productMapper.selectOrderRlnProductInfo();
    }

    @Override
    public List<OrderUserTree> selectByUserName(String username) {
        return gforderMapper.selectByUserName(username);
    }

}
