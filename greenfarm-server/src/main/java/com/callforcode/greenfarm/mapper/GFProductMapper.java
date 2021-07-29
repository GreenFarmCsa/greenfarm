package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.dto.GFOrderCartRlnProductDTO;
import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GFProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(GFProductWithBLOBs record);

    int insertSelective(GFProductWithBLOBs record);

    GFProductWithBLOBs selectByPrimaryKey(Integer productId);

    List<GFProductWithBLOBs> selectByWhere(GFProductWithBLOBs record);

    int updateByPrimaryKeySelective(GFProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GFProductWithBLOBs record);

    int updateByPrimaryKey(GFProduct record);

    List<GFOrderCartRlnProductDTO> selectOrderRlnProductInfo();

    List<GFProduct> selectCartRlnProductInfo(String userName);

    List<GFProductWithBLOBs> selectBySearchText(@Param("searchText") String searchText);

    List<GFProductWithBLOBs> queryProductById(Integer productId);

    List<GFProductWithBLOBs> queryProductByFarmId(Integer farmId);

    List<GFProduct> queryProductByCommentator(String userName);
}
