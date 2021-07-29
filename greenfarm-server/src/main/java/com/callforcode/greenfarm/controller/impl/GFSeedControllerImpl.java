package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFSeedController;
import com.callforcode.greenfarm.entity.GFSeed;
import com.callforcode.greenfarm.service.api.GFSeedService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFSeedVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class GFSeedControllerImpl implements GFSeedController {

    private GFSeedService service;

    @Override
    public ResultVo<List<GFSeedVo>> query() {
        List<GFSeed> seedList = service.querySeeds();
        return BeanUtils.createResultVo(BeanUtils.copyListBean(seedList, GFSeedVo.class));
    }

    @Override
    public ResultVo<GFSeedVo> queryById(String seedId) {
        GFSeed seed = service.querySeedBySeedId(Integer.valueOf(seedId));
        return BeanUtils.createResultVo(BeanUtils.copyBean(seed, GFSeedVo.class));
    }
}
