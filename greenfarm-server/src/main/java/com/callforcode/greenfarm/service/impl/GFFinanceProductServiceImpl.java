package com.callforcode.greenfarm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFFinanceProdAdd;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;
import com.callforcode.greenfarm.repository.api.GFFinanceProductRepository;
import com.callforcode.greenfarm.service.api.GFFinanceProductService;
import com.callforcode.greenfarm.vo.GFFinanceProductVo;

public class GFFinanceProductServiceImpl implements GFFinanceProductService {
    @Autowired
    private GFFinanceProductRepository repository;

    @Override
    public List<GFFinanceProduct> querryGFFinanceProductByConditions(GFFinanceProduct record) {
        return repository.querryGFFinanceProductByConditions(record);
    }

    @Override
    public List<GFFinanceProduct> querryGFFinanceProductByTree(GFFinanceProductTree record) {
        return repository.querryGFFinanceProductByTree(record);
    }

    @Override
    public List<GFFinanceProductVo> querryAllWithSignStatus(String username) {
        List<GFFinanceProductVo> resultList = new ArrayList<GFFinanceProductVo>();
        List<GFFinanceProdAdd> list = repository.querryAllWithSignStatus(username);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GFFinanceProductVo vo = new GFFinanceProductVo();
                GFFinanceProdAdd entity = list.get(i);
                vo.setCreateTime(entity.getCreateTime());
                vo.setFinanceLimit(entity.getFinanceLimit());
                vo.setFinanceProductCategory(entity.getFinanceProductCategory());
                vo.setFinanceProductDesc(entity.getFinanceProductDesc());
                vo.setFinanceProductId(entity.getFinanceProductId());
                vo.setFinanceProductName(entity.getFinanceProductName());
                vo.setFinanceProductNo(entity.getFinanceProductNo());
                vo.setModifyTime(entity.getModifyTime());
                vo.setOrgName(entity.getOrgName());
                vo.setRemark(entity.getRemark());
                vo.setSigned(entity.getIsSigned() == 1 ? true : false);
                vo.setValidPeriod(entity.getValidPeriod());
                resultList.add(vo);
            }
        }
        return resultList;
    }

}
