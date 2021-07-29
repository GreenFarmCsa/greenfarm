package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;

public interface GFShoppingCartService {

    List<GFShoppingCart> queryGFShoppingCartByConditions(GFShoppingCart record);

    GFShoppingCart insertShoppingCart(GFShoppingCart record);

    int updateByPidAndUserName(GFShoppingCart record);

    int deleteByPidAndUserName(GFShoppingCart record);

    List<GFShoppingCartProductTree> selectByUserName(GFShoppingCart record);

}
