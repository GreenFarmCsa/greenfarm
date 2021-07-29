package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFLiveController;
import com.callforcode.greenfarm.entity.GFLive;
import com.callforcode.greenfarm.entity.GFLiveVideoBat;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.LiveNotFoundException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.service.api.GFLiveService;
import com.callforcode.greenfarm.service.api.GFLiveVideoBatService;
import com.callforcode.greenfarm.service.api.GFUserService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFLiveAddVo;
import com.callforcode.greenfarm.vo.GFLiveVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class GFLiveControllerImpl implements GFLiveController {

    private GFLiveService service;

    private GFUserService userService;

    private GFLiveVideoBatService liveVideoBatService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResultVo<List<GFLiveVo>> queryByUserName(String username) {
        GFLive liveEntity = new GFLive();
        liveEntity.setUsername(username);
        List<GFLive> gfLives = service.queryByLive(liveEntity);
        if (gfLives.size() > 0) {
            return BeanUtils.createResultVo(BeanUtils.copyListBean(gfLives, GFLiveVo.class));
        }
        return new ResultVo();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResultVo<List<GFLiveVo>> queryByFarmId(String farmId) {
        GFLive liveEntity = new GFLive();
        liveEntity.setFarmId(Integer.parseInt(farmId));
        List<GFLive> gfLives = service.queryByLive(liveEntity);
        if (gfLives.size() > 0) {
            List<GFLiveVo> copyListBean = BeanUtils.copyListBean(gfLives, GFLiveVo.class);
            for (int i = 0; i < copyListBean.size(); i++) {
                GFUser queryUserInfo = userService.queryUserInfoByUserName(copyListBean.get(i).getUsername());
                if (queryUserInfo != null) {
                    copyListBean.get(i).setIconUrl(queryUserInfo.getIconUrl());
                }
            }
            return BeanUtils.createResultVo(copyListBean);
        }
        return new ResultVo();
    }

    @Override
    public ResultVo<GFLiveVo> insert(GFLiveAddVo gfLiveVo) {
        GFLive gfLive = BeanUtils.copyBean(gfLiveVo, GFLive.class);
        GFLive res = service.add(gfLive);
        ResultVo<GFLiveVo> resultVo = new ResultVo<>();
        if (res != null) {
            GFLiveVo copyBean = BeanUtils.copyBean(res, GFLiveVo.class);
            resultVo.setData(copyBean);
            return resultVo;
        } else {
            resultVo.setMessage("add live error");
            resultVo.setResultCode(ResultCode.FAULT);
            return resultVo;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo updateLive(GFLiveVo gfLiveVo) {
        gfLiveVo.setCreateTime(null);
        GFLive gfLive = BeanUtils.copyBean(gfLiveVo, GFLive.class);
        int res = service.updateByLiveSelective(gfLive);
        if (res == 1) {
            GFLiveVideoBat liveVideoBat = new GFLiveVideoBat();
            liveVideoBat.setLiveId(gfLive.getLiveId().toString());
            liveVideoBat.setModifyTime(new Date());
            int insert = liveVideoBatService.add(liveVideoBat);
            if (insert == 1) {
                return new ResultVo();
            } else {
                throw new UpdateRecordCountNotMatchException("insert live_vodeo_bat error", 1, insert);
            }
        } else if (res == 0) {
            throw new LiveNotFoundException("Live not found.id=" + gfLive.getLiveId());
        } else {
            throw new UpdateRecordCountNotMatchException("update live error", 1, res);
        }

    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo getTickTime(String liveId) {
        int liveIdInt = Integer.parseInt(liveId);
        GFLiveVo gfLiveVo = new GFLiveVo();
        gfLiveVo.setLiveId(liveIdInt);
        gfLiveVo.setTickTime(DateToolUtils.getCurrDate());
        GFLive gfLive = BeanUtils.copyBean(gfLiveVo, GFLive.class);
        int res = service.updateByLiveSelective(gfLive);
        if (res == 1) {
            return new ResultVo();
        } else if (res == 0) {
            throw new LiveNotFoundException("Live not found.id=" + gfLive.getLiveId());
        } else {
            throw new UpdateRecordCountNotMatchException("update live error", 1, res);
        }
    }

}
