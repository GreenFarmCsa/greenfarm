package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFOrderController;
import com.callforcode.greenfarm.entity.GFOrder;
import com.callforcode.greenfarm.entity.OrderDetailProductTree;
import com.callforcode.greenfarm.entity.OrderUserTree;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.service.api.GFOrderService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class GFOrderControllerImpl implements GFOrderController {

    private GFOrderService service;

    @Override
    public ResultVo<GFOrderVo> createOrder(GFOrderAddVo gfOrderVo) {
        List<GFOrderDetailAddVo> detailList = gfOrderVo.getDetail();
        GFOrder copyBean = BeanUtils.copyBean(gfOrderVo, GFOrder.class);
        GFOrderVo vo = service.operationOrder(copyBean, detailList);
        if (null != vo) {
            return BeanUtils.createResultVo(vo);
        } else {
            throw new GFException("add order and orderDetail info error ");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResultVo<List<GFOrderVo>> queryPurchased(String userName) {
        List<OrderUserTree> orderList = service.selectByUserName(userName);
        if (orderList.size() > 0) {
            List<GFOrderVo> resultList = new ArrayList<GFOrderVo>();
            for (int i = 0; i < orderList.size(); i++) {
                OrderUserTree tree = orderList.get(i);
                GFOrder gfOrder = new GFOrder();
                gfOrder.setOrderId(tree.getOrderId());
                List<OrderDetailProductTree> detailList = service.queryTreeByConditions(gfOrder);
                GFOrderVo orderVo = BeanUtils.copyBean(tree, GFOrderVo.class);
                if (detailList.size() > 0) {
                    List<GFOrderDetailVo> dList = new ArrayList<GFOrderDetailVo>();
                    for (int j = 0; j < detailList.size(); j++) {
                        GFOrderDetailVo detailVo = detailList.get(j).entity2Vo(detailList.get(j));
                        dList.add(detailVo);
                    }
                    orderVo.setDetail(dList);
                } else {
                    orderVo.setDetail(new ArrayList<GFOrderDetailVo>());
                }
                resultList.add(orderVo);
            }
            return BeanUtils.createResultVo(resultList);
        }
        return new ResultVo();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResultVo<List<GFOrderVo>> querySold(String userName) {
        GFOrder order = new GFOrder();
        order.setUsername(userName);
        List<OrderDetailProductTree> detailList = service.queryTreeByConditions(order);
        List<Integer> orderIdList = new ArrayList<Integer>();
        List<GFOrderVo> resultList = new ArrayList<GFOrderVo>();
        Map<Integer, List<GFOrderDetailVo>> map = new HashMap<Integer, List<GFOrderDetailVo>>();
        if (detailList.size() > 0) {
            for (int j = 0; j < detailList.size(); j++) {
                GFOrderDetailVo detailVo = detailList.get(j).entity2Vo(detailList.get(j));
                orderIdList.add(detailVo.getOrderId());
                if (!map.containsKey(detailVo.getOrderId())) {
                    List<GFOrderDetailVo> detailVoList = new ArrayList<GFOrderDetailVo>();
                    detailVoList.add(detailVo);
                    map.put(detailVo.getOrderId(), detailVoList);
                } else {
                    List<GFOrderDetailVo> tempList = map.get(detailVo.getOrderId());
                    tempList.add(detailVo);
                    map.put(detailVo.getOrderId(), tempList);
                }
            }
            orderIdList = orderIdList.stream().distinct().collect(Collectors.toList());
            List<OrderUserTree> orderList = service.selectByOrderIdList(orderIdList);
            for (int i = 0; i < orderList.size(); i++) {
                OrderUserTree tree = orderList.get(i);
                GFOrderVo orderVo = BeanUtils.copyBean(tree, GFOrderVo.class);
                orderVo.setDetail(map.get(orderVo.getOrderId()));
                resultList.add(orderVo);
            }
            return BeanUtils.createResultVo(resultList);
        }
        return new ResultVo();
    }

}
