package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFShoppingCartController;
import com.callforcode.greenfarm.entity.GFShoppingCart;
import com.callforcode.greenfarm.entity.GFShoppingCartProductTree;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.service.api.GFShoppingCartService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.RegularUtils;
import com.callforcode.greenfarm.vo.GFGFShoppingCartVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class GFShoppingCartControllerImpl implements GFShoppingCartController {

    private GFShoppingCartService service;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResultVo<GFGFShoppingCartVo> addShoppingCart(String productId, String username, int count) {
        boolean flag = false;
        int resultCount = 0;
        GFShoppingCart entity = null;
        GFShoppingCart record = getGFShoppingCartEntity(productId, username, count, false);
        if (null != record) {
            flag = true;
            List<GFShoppingCart> list = service.queryGFShoppingCartByConditions(record);
            record.setNumber(count);
            if (list.size() > 0) {
                record.setCartId(list.get(0).getCartId());
                resultCount = service.updateByPidAndUserName(record);
            } else {
                entity = service.insertShoppingCart(record);
            }
        }
        if (flag) {
            if (null != entity) {
                return BeanUtils.createResultVo(BeanUtils.copyBean(entity, GFGFShoppingCartVo.class));
            } else if (resultCount > 0) {
                return new ResultVo();
            } else {
                throw new GFException("add shopping cart info error ");
            }
        } else {
            throw new GFException("add shopping cart info error ");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo removeShoppingCart(String productId, String username) {
        boolean flag = false;
        int resultCount = 0;
        GFShoppingCart record = getGFShoppingCartEntity(productId, username, 0, false);
        if (null != record) {
            flag = true;
            resultCount = service.deleteByPidAndUserName(record);
        }
        if (!flag || resultCount == 0) {
            throw new GFException("remove shopping cart info error ");
        }
        return new ResultVo();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo updateShoppingCart(String productId, String username, int count) {
        boolean flag = false;
        int resultCount = 0;
        GFShoppingCart record = getGFShoppingCartEntity(productId, username, count, true);
        if (null != record) {
            flag = true;
            resultCount = service.updateByPidAndUserName(record);
        }
        if (!flag || resultCount == 0) {
            throw new GFException("update shopping cart info error ");
        }
        return new ResultVo();
    }

    public GFShoppingCart getGFShoppingCartEntity(String productId, String username, int count, boolean isFlag) {
        GFShoppingCart record = null;
        boolean flag = isFlagByPidAndCount(productId, count);
        if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(username) && flag) {
            record = new GFShoppingCart();
            record.setProductId(Integer.parseInt(productId));
            record.setUsername(username);
            if (isFlag) {
                record.setNumber(count);
            }
        }
        return record;
    }

    @Override
    public ResultVo<List<GFGFShoppingCartVo>> queryListByUserName(String username) {
        GFShoppingCart record = new GFShoppingCart();
        record.setUsername(username);
        List<GFShoppingCartProductTree> shoppingCartList = service.selectByUserName(record);
        return BeanUtils.createResultVo(BeanUtils.copyListBean(shoppingCartList, GFGFShoppingCartVo.class));
    }

    private boolean isFlagByPidAndCount(String productId, int count) {
        boolean pFlag = RegularUtils.getPatternFlag("^\\d+$", productId);
        boolean countFlag = RegularUtils.getPatternFlag("^\\d+$", String.valueOf(count));
        return pFlag && countFlag;
    }

}
