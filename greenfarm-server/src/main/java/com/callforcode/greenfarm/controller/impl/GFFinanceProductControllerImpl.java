package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFFinanceProductController;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFFinanceProductTree;
import com.callforcode.greenfarm.entity.GFUserFinance;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.service.api.GFFinanceProductService;
import com.callforcode.greenfarm.service.api.GFUserFinanceService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.RegularUtils;
import com.callforcode.greenfarm.vo.GFFinanceProductVo;
import com.callforcode.greenfarm.vo.GFUserFinanceVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class GFFinanceProductControllerImpl implements GFFinanceProductController {

    private GFFinanceProductService service;

    private GFUserFinanceService uService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResultVo<List<GFFinanceProductVo>> queryFinanceProducts(String username) {
        GFFinanceProductTree entity = new GFFinanceProductTree();
        entity.setUserName(username);
        List<GFFinanceProduct> list = service.querryGFFinanceProductByTree(entity);
        if (null != list && list.size() > 0) {
            return BeanUtils.createResultVo(BeanUtils.copyListBean(list, GFFinanceProductVo.class));
        }
        return new ResultVo();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResultVo<GFFinanceProductVo> queryFinanceProduct(String financeProductId) {
        boolean flag = RegularUtils.getPatternFlag("^\\d+$", financeProductId);
        if (StringUtils.isNotBlank(financeProductId) && flag) {
            GFFinanceProduct entity = new GFFinanceProduct();
            int fpId = Integer.parseInt(financeProductId);
            entity.setFinanceProductId(fpId);
            List<GFFinanceProduct> list = service.querryGFFinanceProductByConditions(entity);
            if (null != list && list.size() > 0) {
                return BeanUtils.createResultVo(BeanUtils.copyBean(list.get(0), GFFinanceProductVo.class));
            }
        }
        return new ResultVo();
    }

    @Override
    public ResultVo<GFUserFinanceVo> insertUserFinance(GFUserFinanceVo gfUserFinanceVo) {
        GFFinanceProduct entity = new GFFinanceProduct();
        entity.setFinanceProductId(gfUserFinanceVo.getFinanceProductId());
        List<GFFinanceProduct> rList = service.querryGFFinanceProductByConditions(entity);
        if (rList.size() == 0) {
            throw new GFException("add this finance product not exist ");
        }
        GFUserFinanceVo newVo = new GFUserFinanceVo();
        GFUserFinance uEntity = null;
        newVo.setUsername(gfUserFinanceVo.getUsername());
        newVo.setFinanceProductId(gfUserFinanceVo.getFinanceProductId());
        List<GFUserFinance> list = uService.queryUserFinanceByConditions(newVo);
        if (null != list && list.size() > 0) {
            throw new GFException("you have already signed this finance product");
        } else {
            uEntity = uService.addUserFinance(gfUserFinanceVo);
        }
        if (null != uEntity) {
            GFUserFinanceVo vo = new GFUserFinanceVo();
            return BeanUtils.createResultVo(vo.entityToVo(uEntity));
        } else {
            throw new GFException("add this user finance  error ");
        }
    }

    @Override
    public ResultVo<GFUserFinanceVo> breakUserFinance(GFUserFinanceVo gfUserFinanceVo) {
        int resultCount = 0;
        GFUserFinanceVo newVo = new GFUserFinanceVo();
        newVo.setUsername(gfUserFinanceVo.getUsername());
        newVo.setFinanceProductId(gfUserFinanceVo.getFinanceProductId());
        List<GFUserFinance> list = uService.queryUserFinanceByConditions(newVo);
        if (null != list && list.size() > 0) {
            resultCount = uService.deleteUserFinanceByPidAndUserName(newVo);
        } else {
            throw new GFException("you have not signed this finance product");
        }
        if (resultCount > 0) {
            return new ResultVo<>();
        } else {
            throw new GFException("cancle this user finance product  error ");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResultVo<List<GFFinanceProductVo>> queryAllWithSignStatus(String username) {
        List<GFFinanceProductVo> list = service.querryAllWithSignStatus(username);
        if (null != list && list.size() > 0) {
            return BeanUtils.createResultVo(list);
        }
        return new ResultVo();
    }

}
