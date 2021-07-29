package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFFarmController;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.exception.FarmNotFoundException;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.service.api.GFFarmService;
import com.callforcode.greenfarm.service.api.GFProductService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFFarmAddVo;
import com.callforcode.greenfarm.vo.GFFarmVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
public class GFFarmControllerImpl implements GFFarmController {

    private GFFarmService service;

    private GFProductService serviceProduct;

    @Override
    public ResultVo<List<GFFarmVo>> queryAll() {
        List<GFFarmVo> data = new ArrayList<>();
        List<GFFarmWithBLOBs> farmList = service.queryByFarmWithBLOBs(new GFFarmWithBLOBs());
        if (farmList != null && farmList.size() > 0) {
            for (GFFarmWithBLOBs gfFarm : farmList) {
                GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                farmVo.setImageUrls(imageUrls);
                data.add(farmVo);
            }

        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFFarmVo>> queryByLocation(String location) {
        List<GFFarmVo> data = new ArrayList<>();
        if (StringUtils.isBlank(location)) {
            throw new GFIllegalArgumentException("query farm info failed, location can not be null!");
        }

        GFFarmWithBLOBs farm = new GFFarmWithBLOBs();
        farm.setLocation(location);
        List<GFFarmWithBLOBs> farmList = service.queryByFarmWithBLOBs(farm);
        if (farmList != null && farmList.size() > 0) {
            for (GFFarmWithBLOBs gfFarm : farmList) {
                GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                farmVo.setImageUrls(imageUrls);
                data.add(farmVo);
            }
        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFFarmVo>> queryByProduct(String product) {
        List<GFFarmVo> data = new ArrayList<>();
        if (product == null || "".equals(product)) {
            throw new GFIllegalArgumentException("query farm info failed, product name can not be null!");
        }
        GFProductWithBLOBs productEntity = new GFProductWithBLOBs();
        productEntity.setProductName(product);
        List<GFProductWithBLOBs> productList = serviceProduct.queryByProductWithBLOBs(productEntity);
        Set<Integer> farmIds = new HashSet<>();
        // distinct farmId
        if (productList != null && productList.size() > 0) {
            productList.forEach(e -> farmIds.add(e.getFarmId()));
        }
        GFFarmWithBLOBs farm = new GFFarmWithBLOBs();
        if (farmIds != null && farmIds.size() > 0) {
            farmIds.forEach(e -> {
                farm.setFarmId(e);
                List<GFFarmWithBLOBs> farmList = service.queryByFarmWithBLOBs(farm);
                if (farmList != null && farmList.size() > 0) {
                    for (GFFarmWithBLOBs gfFarm : farmList) {
                        GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                        List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                        farmVo.setImageUrls(imageUrls);
                        data.add(farmVo);
                    }
                }
            });
        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFFarmVo>> query(String searchText) {
        List<GFFarmVo> data = new ArrayList<>();
        List<GFFarmWithBLOBs> farmList = service.queryByMulConditions(searchText);

        if (farmList != null && farmList.size() > 0) {
            for (GFFarmWithBLOBs gfFarm : farmList) {
                GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                farmVo.setImageUrls(imageUrls);
                data.add(farmVo);
            }
        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm by searchText successfully!");
        return result;
    }

    @Override
    public ResultVo<GFFarmVo> queryFarmById(String farmId) {
        if (StringUtils.isBlank(farmId)) {
            throw new FarmNotFoundException("query farm info failed, farmId can not be null!");
        }
        GFFarmWithBLOBs gfFarm = service.queryByFarmId(Integer.parseInt(farmId));

        GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
        List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
        farmVo.setImageUrls(imageUrls);
        ResultVo<GFFarmVo> result = new ResultVo<>();
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm by farmId successfully!");
        result.setData(farmVo);
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo add(GFFarmAddVo gfFarmVo) {
        if (StringUtils.isBlank(gfFarmVo.getFarmName()) || StringUtils.isBlank(gfFarmVo.getUsername())) {
            throw new GFIllegalArgumentException("add farm info failed, farmName or username can not be null!");
        }
        GFFarmWithBLOBs gfFarm = BeanUtils.copyBean(gfFarmVo, GFFarmWithBLOBs.class);
        String imageUrl = BeanUtils.convertArrayToStringWithComma(gfFarmVo.getImageUrls());
        gfFarm.setImageUrl(imageUrl);
        gfFarm.setIdleArea(gfFarm.getTotalArea());
        int res = service.addFarmWithBLOBs(gfFarm);
        if (res == 1) {
            return new ResultVo();
        } else {
            throw new GFException("farm and community name are existed ");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo update(GFFarmVo gfFarmVo) {
        gfFarmVo.setCreateTime(null);
        if (StringUtils.isBlank(gfFarmVo.getFarmName()) || StringUtils.isBlank(gfFarmVo.getUsername())) {
            throw new GFIllegalArgumentException("update farm info failed, farmName or username can not be null!");
        }
        GFFarmWithBLOBs gfFarm = BeanUtils.copyBean(gfFarmVo, GFFarmWithBLOBs.class);
        String imageUrl = BeanUtils.convertArrayToStringWithComma(gfFarmVo.getImageUrls());
        gfFarm.setImageUrl(imageUrl);
        int res = service.updateByFarmWithBLOBsSelective(gfFarm);
        if (res == 1) {
            return new ResultVo();
        } else if (res == 0) {
            throw new FarmNotFoundException("farm info not found!");
        } else {
            throw new UpdateRecordCountNotMatchException("update farm error" + ",update one more rows", 1, res);
        }
    }

    @Override
    public ResultVo<List<GFFarmVo>> queryByUserName(String userName) {
        List<GFFarmVo> data = new ArrayList<>();
        GFFarmWithBLOBs record = new GFFarmWithBLOBs();
        record.setUsername(userName);
        List<GFFarmWithBLOBs> farmList = service.queryByFarmWithBLOBs(record);

        if (farmList != null && farmList.size() > 0) {
            for (GFFarmWithBLOBs gfFarm : farmList) {
                GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                farmVo.setImageUrls(imageUrls);
                data.add(farmVo);
            }
        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm by userName successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFFarmVo>> queryByTotalArea(String totalArea) {
        List<GFFarmWithBLOBs> farmList = service.queryByTotalArea(totalArea);
        List<GFFarmVo> data = new ArrayList<>();
        if (farmList != null && farmList.size() > 0) {
            for (GFFarmWithBLOBs gfFarm : farmList) {
                GFFarmVo farmVo = BeanUtils.copyBean(gfFarm, GFFarmVo.class);
                List<String> imageUrls = BeanUtils.convertStringWithCommaToArray(gfFarm.getImageUrl());
                farmVo.setImageUrls(imageUrls);
                data.add(farmVo);
            }
        }
        ResultVo<List<GFFarmVo>> result = new ResultVo<>();
        result.setData(data);
        result.setResultCode(ResultCode.OK);
        result.setMessage("query farm by totalArea successfully!");
        return result;
    }

    @Override
    public ResultVo deleteFarmRlnInfo(String farmId) {
        if (StringUtils.isBlank(farmId)) {
            throw new FarmNotFoundException("query farm info failed, farmId can not be null!");
        }
        int res = service.deleteFarmRlnInfoByFarmId(Integer.valueOf(farmId));
        if (res == 0) {
            throw new FarmNotFoundException("farm info not exist!.farmId=" + farmId);
        }
        return new ResultVo();
    }

}
