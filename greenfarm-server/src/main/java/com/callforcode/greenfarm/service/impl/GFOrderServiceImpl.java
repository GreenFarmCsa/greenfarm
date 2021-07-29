package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.exception.UserNotFoundException;
import com.callforcode.greenfarm.repository.api.*;
import com.callforcode.greenfarm.service.api.GFOrderService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.GFOrderDetailAddVo;
import com.callforcode.greenfarm.vo.GFOrderDetailVo;
import com.callforcode.greenfarm.vo.GFOrderVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GFOrderServiceImpl implements GFOrderService {

    @Autowired
    private GFOrderRepository repository;

    @Autowired
    private GFOrderDetailRepository detailRepository;

    @Autowired
    private GFCarbonFootprintRepository carbonFootprintRepository;

    @Autowired
    private GFProductRepository productRepository;

    @Autowired
    private GFUserRepository userRepository;

    @Override
    public List<OrderDetailProductTree> queryTreeByConditions(GFOrder record) {
        return repository.queryTreeByConditions(record);
    }

    @Override
    public List<OrderUserTree> selectByOrderIdList(List<Integer> orderIdList) {
        return repository.selectByOrderIdList(orderIdList);
    }

    @Override
    public GFOrderVo operationOrder(GFOrder record, List<GFOrderDetailAddVo> list) {
        GFOrderVo vo = null;
        if (list.size() > 0) {
            int count = repository.insertOrder(record);
            if (count <= 0) {
                throw new GFException("create order fail");
            }
            int orderId = record.getOrderId();
            List<GFOrderDetail> lst = new ArrayList<GFOrderDetail>();
            Map<String, Integer> productMap = new HashMap<String, Integer>();
            for (int i = 0; i < list.size(); i++) {
                GFOrderDetailAddVo gfOrderDetailVo = list.get(i);
                GFOrderDetail detailOrder = new GFOrderDetail();
                detailOrder.setAmount(gfOrderDetailVo.getCount());
                detailOrder.setOrderId(orderId);
                detailOrder.setProductId(gfOrderDetailVo.getProductId());
                detailOrder.setRemark(gfOrderDetailVo.getRemark());
                detailOrder.setCreateTime(DateToolUtils.getCurrDate());
                detailOrder.setModifyTime(detailOrder.getCreateTime());
                lst.add(detailOrder);
                productMap.put(String.valueOf(gfOrderDetailVo.getProductId()), gfOrderDetailVo.getCount());
            }
            List<GFProduct> productList = detailRepository.queryProductByList(lst);
            List<GFProduct> updateProductList = new ArrayList<GFProduct>();
            for (int i = 0; i < productList.size(); i++) {
                GFProduct gfProductN = new GFProduct();
                GFProduct gfProduct = productList.get(i);
                int num = gfProduct.getNumber();
                int pCount = productMap.get(String.valueOf(gfProduct.getProductId()));
                int number = num - pCount;
                if (number < 0) {
                    throw new GFException("create order fail,num is not enough");
                } else {
                    gfProductN.setProductId(gfProduct.getProductId());
                    gfProductN.setNumber(number);
                    gfProductN.setSaleNumber(pCount + gfProduct.getSaleNumber());
                    gfProductN.setModifyTime(DateToolUtils.getCurrDate());
                    gfProductN.setCreateTime(null);
                    updateProductList.add(gfProductN);
                }
            }
            if (updateProductList.size() > 0) {
                int updateCount = detailRepository.updateProductByList(updateProductList);
                if (updateCount <= 0) {
                    throw new GFException("create order fail,update product num and salenumer error");
                }
            }
            int detailCount = detailRepository.insertList(lst);
            if (detailCount > 0) {
                vo = BeanUtils.copyBean(record, GFOrderVo.class);
                vo.setDetail(BeanUtils.copyListBean(lst, GFOrderDetailVo.class));
            }

            if (record.getCarbonCredit() <= 0) {
                return vo;
            }

            int res = carbonFootprintRepository.addCarbonFootprint(createCarbonFootprint(record, lst.get(0)));
            if (res != 1) {
                throw new UpdateRecordCountNotMatchException("carbonFootprint add error", 1, res);
            }
            GFUser gfUser = userRepository.queryUserInfo(record.getUsername());
            if (Objects.isNull(gfUser)) {
                throw new UserNotFoundException("user info not found.username=" + record.getUsername());
            }
            int carbonCredit = gfUser.getCarbonCredit() == null ? 0
                    : gfUser.getCarbonCredit() - record.getCarbonCredit();
            if (carbonCredit < 0) {
                throw new GFException("carbonCredit of user is not enough!.username=" + record);
            }
            gfUser.setCarbonCredit(carbonCredit);
            res = userRepository.updateUserInfo(gfUser);
            if (res != 1) {
                throw new UserNotFoundException("user info not found.username=" + record.getUsername());
            }
            Date currDate = DateToolUtils.getCurrDateWithoutTime();
            GFCarbonCreditDaily gfCarbonCreditDaily = carbonFootprintRepository
                    .queryCarbonCreditDaily(record.getUsername(), currDate, GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_ACTUAL);
            if (Objects.isNull(gfCarbonCreditDaily)) {
                gfCarbonCreditDaily = new GFCarbonCreditDaily();
                gfCarbonCreditDaily.setUsername(record.getUsername());
                gfCarbonCreditDaily.setCarbonCredit(carbonCredit);
                gfCarbonCreditDaily.setCarbonDate(currDate);
                res = carbonFootprintRepository.addCarbonCreditDaily(gfCarbonCreditDaily);
            } else {
                gfCarbonCreditDaily.setCarbonCredit(carbonCredit);
                res = carbonFootprintRepository.updateCarbonCreditDaily(gfCarbonCreditDaily);
            }
            if (res != 1) {
                throw new UpdateRecordCountNotMatchException("carbon-Credit-daily update error", 1, res);
            }
        }
        return vo;
    }

    @Override
    public List<OrderUserTree> selectByUserName(String username) {
        return repository.selectByUserName(username);
    }

    private GFCarbonFootprint createCarbonFootprint(GFOrder order, GFOrderDetail detail) {
        GFCarbonFootprint gfCarbonFootprint = new GFCarbonFootprint();
        gfCarbonFootprint.setFootprintCategory(GreenFarmConst.GRF_FOOT_PRINT_CATEGORY_CONSUME);
        GFProductWithBLOBs product = productRepository.queryByProductId(detail.getProductId());
        if (Objects.isNull(product)) {
            throw new GFException("product info not found.productId=" + detail.getProductId());
        }
        gfCarbonFootprint.setUsername(order.getUsername());
        gfCarbonFootprint.setFootprintName("Buy " + product.getProductName());
        gfCarbonFootprint.setCarbonCredit(order.getCarbonCredit());
        gfCarbonFootprint.setCreateTime(DateToolUtils.getCurrDate());
        gfCarbonFootprint.setRemark(order.getRemark());
        gfCarbonFootprint.setFarmId(1);
        return gfCarbonFootprint;
    }
}
