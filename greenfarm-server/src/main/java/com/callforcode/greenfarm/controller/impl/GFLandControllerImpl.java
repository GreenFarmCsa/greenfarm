package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFLandController;
import com.callforcode.greenfarm.entity.GFLand;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.exception.LandNotFoundException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.service.api.GFLandService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFLandAddVo;
import com.callforcode.greenfarm.vo.GFLandReviseVo;
import com.callforcode.greenfarm.vo.GFLandVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class GFLandControllerImpl implements GFLandController {

    private GFLandService service;

    @Override
    public ResultVo<List<GFLandVo>> queryLandsByFarmId(String farmId) {
        //param validate
        if (farmId == null || "".equals(farmId)) {
            throw new LandNotFoundException("query land info failed, farmId can not be null!");
        }
        List<GFLand> gfLand = service.queryLandsByFarmId(Integer.valueOf(farmId));
        return BeanUtils.createResultVo(BeanUtils.copyListBean(gfLand, GFLandVo.class));

    }

    @Override
    public ResultVo addLand(GFLandAddVo gfLandVo) {
        //param validate
        if (gfLandVo.getFarmId() == null) {
            throw new GFIllegalArgumentException("add land info failed, farmId can not be null!");
        }
        int res = service.addLand(BeanUtils.copyBean(gfLandVo, GFLand.class));
        if (res == 1) {
            return new ResultVo();
        } else if (res == -1) {
            throw new GFException("farm idleArea less than add landArea!");
        } else {
            throw new UpdateRecordCountNotMatchException("add land error", 1, res);
        }
    }

    @Override
    public ResultVo updateLand(GFLandReviseVo gfLandVo) {
        //param validate
        if (gfLandVo.getLandId() == null) {
            throw new GFIllegalArgumentException("update land info failed,landId can not be null!");
        }
        GFLand gfLand = BeanUtils.copyBean(gfLandVo, GFLand.class);
        service.updateLand(gfLand);
        return new ResultVo();
    }

}
