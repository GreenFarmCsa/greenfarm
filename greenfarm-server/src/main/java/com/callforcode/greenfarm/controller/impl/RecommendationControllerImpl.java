package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.RecommendationController;
import com.callforcode.greenfarm.entity.GFFarm;
import com.callforcode.greenfarm.entity.GFFinanceProduct;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.service.api.RecommendationService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFFarmVo;
import com.callforcode.greenfarm.vo.GFFinanceProductVo;
import com.callforcode.greenfarm.vo.GFProductVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class RecommendationControllerImpl implements RecommendationController {

    private RecommendationService service;

    @Override
    public ResultVo<List<GFFarmVo>> queryFarms(String userName) {
        List<GFFarm> gfFarms = service.queryFarms(userName);
        return BeanUtils.createResultVo(BeanUtils.copyListBean(gfFarms, GFFarmVo.class));
    }

    @Override
    public ResultVo<List<GFProductVo>> queryProducts(String userName) {
        List<GFProductWithBLOBs> gfProductWithBLOBs = service.queryProducts(userName);
        List<GFProductVo> gfProductVos = new ArrayList<>();
        if (Objects.nonNull(gfProductWithBLOBs) && gfProductWithBLOBs.size() > 0) {
            gfProductWithBLOBs.forEach(gfProductWithBLOB -> {
                GFProductVo gfProductVo = BeanUtils.copyBean(gfProductWithBLOB, GFProductVo.class);
                gfProductVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(gfProductWithBLOB.getImageUrl()));
                gfProductVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(gfProductWithBLOB.getVedioUrl()));
                gfProductVos.add(gfProductVo);
            });
        }
        return BeanUtils.createResultVo(gfProductVos);

    }

    @Override
    public ResultVo<List<GFFinanceProductVo>> queryFinanceProducts(String userName) {
        List<GFFinanceProduct> gfFinanceProducts = service.queryFinanceProducts(userName);
        return BeanUtils.createResultVo(BeanUtils.copyListBean(gfFinanceProducts, GFFinanceProductVo.class));
    }
}
