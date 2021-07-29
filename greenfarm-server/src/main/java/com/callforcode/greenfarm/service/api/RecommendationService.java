package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFFarm;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;

import java.util.List;

public interface RecommendationService {

    List<GFFarm> queryFarms(String userName);

    List<GFProductWithBLOBs> queryProducts(String userName);

    List<GFFinanceProduct> queryFinanceProducts(String userName);

}
