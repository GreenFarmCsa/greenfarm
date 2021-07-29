package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.api.GFProductController;
import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFProductCommentWithBLOBs;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.GFIllegalArgumentException;
import com.callforcode.greenfarm.exception.GFPlantTaskNotFoundException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.service.api.*;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class GFProductControllerImpl implements GFProductController {

    private GFProductService service;

    private GFProductCommentService serviceProductComment;

    private BlockChainService blockChainService;

    private GFUserService userService;

    private GFPlantTaskService plantTaskService;

    @Override
    public ResultVo<List<GFProductVo>> queryTopProducts() {
        ResultVo<List<GFProductVo>> result = new ResultVo<List<GFProductVo>>();
        List<GFProductVo> data = new ArrayList<GFProductVo>();

        GFProductWithBLOBs productEntity = new GFProductWithBLOBs();
        List<GFProductWithBLOBs> productList = service.queryByProductWithBLOBs(productEntity);
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                GFProductVo productVo = BeanUtils.copyBean(productList.get(i), GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getVedioUrl()));
                data.add(productVo);
            }
            result.setData(data);
        }
        result.setResultCode(ResultCode.OK);
        result.setMessage("query product info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFProductVo>> queryProductsByCategory(String category) {
        ResultVo<List<GFProductVo>> result = new ResultVo<List<GFProductVo>>();
        List<GFProductVo> data = new ArrayList<GFProductVo>();
        if (category == null || "".equals(category)) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("query product info failed, category can not be null!");
            return result;
        }

        GFProductWithBLOBs productEntity = new GFProductWithBLOBs();
        productEntity.setCategory(category);
        List<GFProductWithBLOBs> productList = service.queryByProductWithBLOBs(productEntity);
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                GFProductVo productVo = BeanUtils.copyBean(productList.get(i), GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getVedioUrl()));
                data.add(productVo);
            }
            result.setData(data);

        }
        result.setResultCode(ResultCode.OK);
        result.setMessage("query product info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFProductVo>> queryPlantProducts(String userName) {
        ResultVo<List<GFProductVo>> result = new ResultVo<List<GFProductVo>>();
        List<GFProductVo> data = new ArrayList<GFProductVo>();
        if (userName == null || "".equals(userName)) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("query product info failed, userName can not be null!");
            return result;
        }
        GFProductWithBLOBs productEntity = new GFProductWithBLOBs();
        productEntity.setUsername(userName);
        List<GFProductWithBLOBs> productList = service.queryByProductWithBLOBs(productEntity);
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                GFProductVo productVo = BeanUtils.copyBean(productList.get(i), GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(productList.get(i).getVedioUrl()));
                data.add(productVo);
            }
            result.setData(data);

        }
        result.setResultCode(ResultCode.OK);
        result.setMessage("query product info successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFProductCommentVo>> queryComment(String productId) {

        ResultVo<List<GFProductCommentVo>> result = new ResultVo<List<GFProductCommentVo>>();
        List<GFProductCommentVo> data = new ArrayList<GFProductCommentVo>();
        if (productId == null || "".equals(productId)) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("query product comment failed, productId can not be null!");
            return result;
        }
        GFProductCommentWithBLOBs gfProductComment = new GFProductCommentWithBLOBs();
        gfProductComment.setProductId(Integer.parseInt(productId));

        List<GFProductCommentWithBLOBs> gfProductCommentList = serviceProductComment.queryByProductCommentWithBLOBs(gfProductComment);
        if (gfProductCommentList != null && gfProductCommentList.size() > 0) {
            for (int i = 0; i < gfProductCommentList.size(); i++) {
                GFProductCommentWithBLOBs gfProductCommentWithBLOBs = gfProductCommentList.get(i);
                GFProductCommentVo productCommentVo = new GFProductCommentVo();
                BeanUtils.copyBean(gfProductCommentWithBLOBs, productCommentVo);
                GFUser gfUser = userService.queryUserInfoByUserName(gfProductCommentWithBLOBs.getUsername());
                productCommentVo.setIconUrl(gfUser.getIconUrl());
                data.add(productCommentVo);
            }
            result.setData(data);
        }
        result.setResultCode(ResultCode.OK);
        result.setMessage("query product comment successfully!");
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo<GFProductVo> addProduct(GFProductAddVo gfProductAddVo) {
        ResultVo<GFProductVo> result = new ResultVo<>();
        if (gfProductAddVo.getProductName() == null || "".equals(gfProductAddVo.getProductName())
                || gfProductAddVo.getFarmId() == null || gfProductAddVo.getLandId() == null) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("add product info failed, productName/farmId/landId can not be null!");
            return result;
        }
        GFProductWithBLOBs product = new GFProductWithBLOBs();
        product.setProductName(gfProductAddVo.getProductName());
        product.setFarmId(gfProductAddVo.getFarmId());
        product.setLandId(gfProductAddVo.getLandId());
        List<GFProductWithBLOBs> productList = service.queryByProductWithBLOBs(product);
        if (productList != null && productList.size() > 0) {
            GFProductWithBLOBs productExist = BeanUtils.copyBean(gfProductAddVo, GFProductWithBLOBs.class);
            productExist.setImageUrl(BeanUtils.convertArrayToStringWithComma(gfProductAddVo.getImageUrl()));
            productExist.setVedioUrl(BeanUtils.convertArrayToStringWithComma(gfProductAddVo.getVedioUrl()));
            productExist.setProductId(productList.get(0).getProductId());
            GFProductWithBLOBs updateByPrimaryKeyWithBLOBs = service.updateByProductWithBLOBsWithBLOBs(productExist);
            if (updateByPrimaryKeyWithBLOBs != null) {
                GFProductVo productVo = GFProductVo.convertProductWithBLOBS2ProductVo(updateByPrimaryKeyWithBLOBs);
                result.setData(productVo);
            }
            result.setResultCode(ResultCode.OK);
            result.setMessage("update product info successfully!");
        } else {
            GFProductWithBLOBs productNew = BeanUtils.copyBean(gfProductAddVo, GFProductWithBLOBs.class);
            productNew.setImageUrl(BeanUtils.convertArrayToStringWithComma(gfProductAddVo.getImageUrl()));
            productNew.setVedioUrl(BeanUtils.convertArrayToStringWithComma(gfProductAddVo.getVedioUrl()));
            GFProductWithBLOBs productInfo = service.add(productNew);
            if (productInfo != null) {
                GFPlantTask record = new GFPlantTask();
                record.setTaskId(gfProductAddVo.getTaskId());
                record.setProductId(productInfo.getProductId());
                record.setStatus(GreenFarmConst.GRF_TASK_STATUS_COMPLETE);
                Integer updateGFPlantTask = service.updateGFPlantTask(record);
                if (updateGFPlantTask != 1) {
                    throw new GFPlantTaskNotFoundException("taskId not find");
                }

                blockChainService.record(productInfo.getProductId());
                GFProductVo productVo = GFProductVo.convertProductWithBLOBS2ProductVo(productInfo);
                result.setData(productVo);
            }
            result.setResultCode(ResultCode.OK);
            result.setMessage("add product info successfully!");
        }
        //update task-step status to complete
        if (Objects.nonNull(gfProductAddVo.getStepId())) {
            plantTaskService.updateTaskStepStatus(gfProductAddVo.getStepId(), GreenFarmConst.GRF_STEP_STATUS_END);
        }
        return result;

    }

    @Override
    public ResultVo packProduct(GFProductBoxVo gfProductBoxVo) {
        GFPlantTask record = new GFPlantTask();
        record.setTaskId(gfProductBoxVo.getTaskId());
        record.setStatus(GreenFarmConst.GRF_TASK_STATUS_COMPLETE);
        Integer res = service.updateGFPlantTask(record);
        if (res != 1) {
            throw new GFPlantTaskNotFoundException("taskId not find");
        }
        //update task-step status to complete
        if (Objects.nonNull(gfProductBoxVo.getStepId())) {
            plantTaskService.updateTaskStepStatus(gfProductBoxVo.getStepId(), GreenFarmConst.GRF_STEP_STATUS_END);
        }
        ResultVo<GFProductVo> result = new ResultVo<>();
        result.setResultCode(ResultCode.OK);
        result.setMessage("pack product successfully!");
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo updateProduct(GFProductVo gfProductVo) {
        gfProductVo.setCreateTime(null);
        ResultVo result = new ResultVo();
        if (gfProductVo.getProductName() == null || "".equals(gfProductVo.getProductName())
                || gfProductVo.getFarmId() == null || gfProductVo.getLandId() == null) {

            result.setResultCode(ResultCode.ERROR);
            result.setMessage("update product info failed, productName/farmId/landId can not be null!");
            return result;
        }
        GFProductWithBLOBs product = new GFProductWithBLOBs();
        product.setProductName(gfProductVo.getProductName());
        product.setFarmId(gfProductVo.getFarmId());
        product.setLandId(gfProductVo.getLandId());
        List<GFProductWithBLOBs> productList = service.queryByProductWithBLOBs(product);
        if (productList != null && productList.size() > 0) {
            GFProductWithBLOBs productExist = BeanUtils.copyBean(gfProductVo, GFProductWithBLOBs.class);
            productExist.setImageUrl(BeanUtils.convertArrayToStringWithComma(gfProductVo.getImageUrl()));
            productExist.setVedioUrl(BeanUtils.convertArrayToStringWithComma(gfProductVo.getVedioUrl()));
            service.updateByProductWithBLOBsWithBLOBs(productExist);
            result.setResultCode(ResultCode.OK);
            result.setMessage("update product info successfully!");
            return result;
        } else {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("update product info failed,this product does not exist!");
            return result;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo deleteProduct(String productId) {

        ResultVo result = new ResultVo();
        if (productId == null || "".equals(productId)) {

            result.setResultCode(ResultCode.ERROR);
            result.setMessage("delete product info failed, producId can not be null!");
            return result;
        }

        service.deleteByProductId(Integer.parseInt(productId));
        result.setResultCode(ResultCode.OK);
        result.setMessage("delete product info successfully!");
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo addComment(GFProductCommentAddVo gfProductCommentVo) {
        ResultVo result = new ResultVo();
        if (gfProductCommentVo.getProductId() == null || "".equals(gfProductCommentVo.getUsername())
                || gfProductCommentVo.getUsername() == null) {
            throw new GFIllegalArgumentException("add product comment failed, productId or username can not be null!");
        }
        GFProductCommentWithBLOBs gfProductComment = com.callforcode.greenfarm.util.BeanUtils
                .copyBean(gfProductCommentVo, GFProductCommentWithBLOBs.class);
        int res = serviceProductComment.addProductCommentWithBLOBs(gfProductComment);
        if (res == 1) {
            result.setResultCode(ResultCode.OK);
            result.setMessage("add product comment successfully!");
            return result;
        } else {
            throw new UpdateRecordCountNotMatchException("add product comment error", 1, res);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo like(String productId, String isLike, String userName) {
        if (Objects.isNull(productId) || Objects.isNull(userName) || "".equals(userName) || Objects.isNull(isLike)
                || "".equals(isLike)) {
            throw new GFIllegalArgumentException("product_like operate failed,productId/userName can not be null!");
        }
        service.productLike(Integer.valueOf(productId), isLike, userName);
        return new ResultVo();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResultVo deleteComment(String commentId) {
        ResultVo result = new ResultVo();
        if (commentId == null || "".equals(commentId)) {
            result.setResultCode(ResultCode.ERROR);
            result.setMessage("delete product comment failed, commentId can not be null!");
            return result;
        }
        serviceProductComment.deleteByCommentId(Integer.parseInt(commentId));
        result.setResultCode(ResultCode.OK);
        result.setMessage("delete product comment successfully!");
        return result;
    }

    @Override
    public ResultVo<List<GFProductVo>> queryBySearchText(String searchText) {
        List<GFProductWithBLOBs> productList = service.queryBySearchText(searchText);
        ResultVo<List<GFProductVo>> result = new ResultVo<>();
        if (productList != null && productList.size() > 0) {
            List<GFProductVo> data = new ArrayList<>();
            productList.forEach(f -> {
                GFProductVo productVo = BeanUtils.copyBean(f, GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(f.getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(f.getVedioUrl()));
                data.add(productVo);
            });
            result.setData(data);
        }
        result.setResultCode(ResultCode.OK);
        return result;
    }

    @Override
    public ResultVo<List<GFProductVo>> queryProductByProductId(Integer productId) {
        List<GFProductWithBLOBs> productList = service.queryProductById(productId);
        ResultVo<List<GFProductVo>> result = new ResultVo<>();
        if (productList != null && productList.size() > 0) {
            List<GFProductVo> data = new ArrayList<>();
            productList.forEach(f -> {
                GFProductVo productVo = BeanUtils.copyBean(f, GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(f.getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(f.getVedioUrl()));
                data.add(productVo);
            });
            result.setData(data);
        }
        result.setResultCode(ResultCode.OK);
        return result;
    }

    @Override
    public ResultVo<List<GFProductVo>> queryProductByFarmId(Integer farmId) {
        List<GFProductWithBLOBs> productList = service.queryProductByFarmId(farmId);
        ResultVo<List<GFProductVo>> result = new ResultVo<>();
        if (productList != null && productList.size() > 0) {
            List<GFProductVo> data = new ArrayList<>();
            productList.forEach(f -> {
                GFProductVo productVo = BeanUtils.copyBean(f, GFProductVo.class);
                productVo.setImageUrl(BeanUtils.convertStringWithCommaToArray(f.getImageUrl()));
                productVo.setVedioUrl(BeanUtils.convertStringWithCommaToArray(f.getVedioUrl()));
                data.add(productVo);
            });
            result.setData(data);
        }
        result.setResultCode(ResultCode.OK);
        return result;
    }

}
