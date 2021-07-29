package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.dto.GFFinanceProdDTO;
import com.callforcode.greenfarm.entity.GFFinanceProdAdd;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;

public interface GFFinanceProductRepository {

    GFFinanceProduct selectByPid(Integer farmId);

    List<GFFinanceProduct> querryGFFinanceProductByConditions(GFFinanceProduct record);

    List<GFFinanceProduct> querryGFFinanceProductByTree(GFFinanceProductTree record);

    List<GFFinanceProdDTO> selectAll();

    List<GFFinanceProdAdd> querryAllWithSignStatus(String username);
}
