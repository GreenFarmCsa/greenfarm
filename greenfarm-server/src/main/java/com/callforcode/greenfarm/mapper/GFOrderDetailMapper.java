package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.callforcode.greenfarm.entity.GFOrderDetail;
import com.callforcode.greenfarm.entity.GFProduct;

@Mapper
public interface GFOrderDetailMapper {
    int deleteByPrimaryKey(Integer detailId);

    int insert(GFOrderDetail record);

    int insertSelective(GFOrderDetail record);

    GFOrderDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(GFOrderDetail record);

    int updateByPrimaryKey(GFOrderDetail record);

    List<GFOrderDetail> selectByWhere(GFOrderDetail record);

    int insertList(List<GFOrderDetail> list);
    
    List<GFProduct> selectProductByIdList(@Param("idProductList")List<GFOrderDetail> list);
    
    int updateProductByList(@Param("list")List<GFProduct> list);
}
