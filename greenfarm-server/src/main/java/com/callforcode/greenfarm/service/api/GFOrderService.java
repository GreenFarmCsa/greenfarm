package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFOrder;
import com.callforcode.greenfarm.entity.OrderDetailProductTree;
import com.callforcode.greenfarm.entity.OrderUserTree;
import com.callforcode.greenfarm.vo.GFOrderDetailAddVo;
import com.callforcode.greenfarm.vo.GFOrderVo;

public interface GFOrderService {

    List<OrderDetailProductTree> queryTreeByConditions(GFOrder record);

    List<OrderUserTree> selectByOrderIdList(List<Integer> orderIdList);

    GFOrderVo operationOrder(GFOrder record, List<GFOrderDetailAddVo> list);

    List<OrderUserTree> selectByUserName(String username);
}
