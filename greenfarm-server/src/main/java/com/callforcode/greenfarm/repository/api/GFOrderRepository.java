package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.dto.GFOrderCartRlnProductDTO;
import com.callforcode.greenfarm.entity.GFOrder;
import com.callforcode.greenfarm.entity.OrderDetailProductTree;
import com.callforcode.greenfarm.entity.OrderUserTree;

import java.util.List;

public interface GFOrderRepository {

    int insertOrder(GFOrder record);

    List<OrderDetailProductTree> queryTreeByConditions(GFOrder record);

    List<OrderUserTree> selectByOrderIdList(List<Integer> orderIdList);

    List<GFOrderCartRlnProductDTO> selectOrderRlnProductInfo(String userName);

    List<OrderUserTree> selectByUserName(String username);
}
