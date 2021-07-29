package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;

@Mapper
public interface GFShoppingCartMapper {

    List<GFShoppingCart> selectByWhere(GFShoppingCart record);

    int deleteByPrimaryKey(GFShoppingCart record);

    int insert(GFShoppingCart record);

    int insertSelective(GFShoppingCart record);

    GFShoppingCart selectByPrimaryKey(Integer cartId);

    int updateByPrimaryKeySelective(GFShoppingCart record);

    int updateByPrimaryKey(GFShoppingCart record);

    int updateByPidAndUserName(GFShoppingCart record);

    int deleteByPidAndUserName(GFShoppingCart record);

    List<GFShoppingCartProductTree> selectByUserName(GFShoppingCart record);
}
