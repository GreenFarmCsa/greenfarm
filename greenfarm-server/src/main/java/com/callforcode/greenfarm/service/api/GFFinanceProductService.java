package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;
import com.callforcode.greenfarm.vo.GFFinanceProductVo;

public interface GFFinanceProductService {

    List<GFFinanceProduct> querryGFFinanceProductByConditions(GFFinanceProduct record);

    List<GFFinanceProduct> querryGFFinanceProductByTree(GFFinanceProductTree record);
    
    List<GFFinanceProductVo> querryAllWithSignStatus(String username);
}
