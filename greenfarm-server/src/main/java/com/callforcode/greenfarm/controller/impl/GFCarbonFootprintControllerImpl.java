package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFCarbonFootprintController;
import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.exception.UserOrPasswordErrorException;
import com.callforcode.greenfarm.service.api.GFCarbonFootprintService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFCarbonFootprintAddVo;
import com.callforcode.greenfarm.vo.GFCarbonFootprintVo;
import com.callforcode.greenfarm.vo.GFDashboardFootprintVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class GFCarbonFootprintControllerImpl implements GFCarbonFootprintController {

    private GFCarbonFootprintService carbonFootprintService;

    @Override
    public ResultVo<List<GFCarbonFootprintVo>> queryCarbonFootprint(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new UserOrPasswordErrorException("query carbonFootprint info failed, username can not be null!");
        }
        List<GFCarbonFootprint> list = carbonFootprintService.queryCarbonFootprint(userName);
        return BeanUtils.createResultVo(BeanUtils.copyListBean(list, GFCarbonFootprintVo.class));

    }

    @Override
    public ResultVo<?> addCarbonFootprint(GFCarbonFootprintAddVo gfCarbonFootprintVo) {
        GFCarbonFootprint gfCarbonFootprint = BeanUtils.copyBean(gfCarbonFootprintVo, GFCarbonFootprint.class);
        carbonFootprintService.addCarbonFootprint(gfCarbonFootprint);
        return new ResultVo<Object>();
    }

    @Override
    public ResultVo<GFDashboardFootprintVo> queryFromDashboard(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new UserOrPasswordErrorException("query dashboardFootprint info failed, username can not be null!");
        }
        ResultVo<GFDashboardFootprintVo> resultVo = new ResultVo<GFDashboardFootprintVo>();
        GFDashboardFootprintVo gfDashboardFootprintVo = carbonFootprintService.queryFromDashboard(userName);
        resultVo.setData(gfDashboardFootprintVo);
        return resultVo;
    }

}
