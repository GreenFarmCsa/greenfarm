package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;

public interface GFShoppingCartRepository {

    List<GFShoppingCart> queryGFShoppingCartByConditions(GFShoppingCart record);

    int insertShoppingCart(GFShoppingCart record);

    List<GFProduct> selectCartRlnProductInfo(String userName);

    int updateByPidAndUserName(GFShoppingCart record);

    int deleteByPidAndUserName(GFShoppingCart record);

    List<GFShoppingCartProductTree> selectByUserName(GFShoppingCart record);
}
