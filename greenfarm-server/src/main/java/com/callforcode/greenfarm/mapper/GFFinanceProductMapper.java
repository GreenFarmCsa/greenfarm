package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.callforcode.greenfarm.dto.GFFinanceProdDTO;
import com.callforcode.greenfarm.entity.GFFinanceProdAdd;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;

@Mapper
public interface GFFinanceProductMapper {
    int deleteByPrimaryKey(Integer financeProductId);

    int insert(GFFinanceProduct record);

    int insertSelective(GFFinanceProduct record);

    GFFinanceProduct selectByPrimaryKey(Integer financeProductId);

    int updateByPrimaryKeySelective(GFFinanceProduct record);

    int updateByPrimaryKey(GFFinanceProduct record);

    List<GFFinanceProduct> selectByWhere(GFFinanceProduct record);

    List<GFFinanceProduct> findTreeByWhere(@Param("tree") GFFinanceProductTree tree);

    List<GFFinanceProdDTO> selectAll();
    
    List<GFFinanceProdAdd> querryAllWithSignStatus(@Param("username") String username);
    
}
