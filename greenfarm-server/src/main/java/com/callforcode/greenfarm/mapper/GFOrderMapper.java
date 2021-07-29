package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFOrder;
import com.callforcode.greenfarm.entity.OrderDetailProductTree;
import com.callforcode.greenfarm.entity.OrderUserTree;

@Mapper
public interface GFOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(GFOrder record);

    int insertSelective(GFOrder record);

    GFOrder selectByPrimaryKey(Integer orderId);

    List<GFOrder> selectByWhere(GFOrder record);

    int updateByPrimaryKeySelective(GFOrder record);

    int updateByPrimaryKey(GFOrder record);

    List<OrderDetailProductTree> findTreeByWhere(GFOrder record);

    List<OrderUserTree> selectByOrderIdList(List<Integer> orderIdList);

    List<OrderUserTree> selectByUserName(String username);
}
