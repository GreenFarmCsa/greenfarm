package com.callforcode.greenfarm.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.dto.GFFinanceProdDTO;
import com.callforcode.greenfarm.entity.GFFinanceProdAdd;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;
import com.callforcode.greenfarm.mapper.GFFinanceProductMapper;
import com.callforcode.greenfarm.repository.api.GFFinanceProductRepository;

public class GFFinanceProductRepositoryImpl implements GFFinanceProductRepository {
    @Autowired
    private GFFinanceProductMapper mapper;

    @Override
    public GFFinanceProduct selectByPid(Integer farmId) {
        return mapper.selectByPrimaryKey(farmId);
    }

    @Override
    public List<GFFinanceProduct> querryGFFinanceProductByConditions(GFFinanceProduct record) {
        return mapper.selectByWhere(record);
    }

    @Override
    public List<GFFinanceProduct> querryGFFinanceProductByTree(GFFinanceProductTree record) {
        return mapper.findTreeByWhere(record);
    }

    @Override
    public List<GFFinanceProdDTO> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<GFFinanceProdAdd> querryAllWithSignStatus(String username) {
        return mapper.querryAllWithSignStatus(username);
    }

}
