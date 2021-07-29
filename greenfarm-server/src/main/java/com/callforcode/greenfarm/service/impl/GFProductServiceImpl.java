package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.repository.api.GFProductRepository;
import com.callforcode.greenfarm.service.api.GFProductService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class GFProductServiceImpl implements GFProductService {

    @Autowired
    private GFProductRepository repository;

    @Override
    public int deleteByProductId(Integer productId) {
        return repository.deleteByProductId(productId);
    }

    @Override
    public GFProductWithBLOBs add(GFProductWithBLOBs record) {
        if (record != null) {
            int insert = repository.add(record);
            if (insert == 1) {
                return record;
            }
        }
        return null;
    }

    @Override
    public List<GFProductWithBLOBs> queryByProductWithBLOBs(GFProductWithBLOBs record) {
        return repository.queryByProductWithBLOBs(record);
    }

    @Override
    public GFProductWithBLOBs updateByProductWithBLOBsWithBLOBs(GFProductWithBLOBs record) {
        int updateByPrimaryKeyWithBLOBs = repository.updateByProductWithBLOBsWithBLOBs(record);
        if (updateByPrimaryKeyWithBLOBs == 1) {
            return record;
        } else {
            return null;
        }
    }

    @Override
    public List<GFProductWithBLOBs> queryBySearchText(String searchText) {
        return repository.queryBySearchText(searchText);
    }

    @Override
    public void productLike(Integer productId, String isLike, String userName) {
        GFProductWithBLOBs product = repository.queryByProductId(productId);
        GFProductWithBLOBs productForUpdate = new GFProductWithBLOBs();
        if (Objects.isNull(product)) {
            throw new GFException("add product like failed, the product info not found.productId=" + productId);
        }
        if ("true".equals(isLike)) {
            productForUpdate.setProductId(productId);
            productForUpdate.setLikeNumber((product.getLikeNumber() == null ? 0 : product.getLikeNumber()) + 1);
            int updRes = repository.updateByProductWithBLOBsSelective(productForUpdate);
            if (updRes != 1) {
                throw new GFException("the product info not found.productId=" + productId);
            }
            List<GFLike> selectByUsernameAndProductId = repository.queryByUsernameAndProductId(userName, productId);
            if (selectByUsernameAndProductId == null || selectByUsernameAndProductId.size() == 0) {
                GFLike record = new GFLike();
                record.setProductId(productId);
                record.setUsername(userName);
                repository.addByProduct(record);
            }
        } else if ("false".equals(isLike)) {
            productForUpdate.setProductId(productId);
            productForUpdate.setLikeNumber(((product.getLikeNumber() == null ? 0 : product.getLikeNumber())) == 0 ? 0
                    : product.getLikeNumber() - 1);
            int updRes = repository.updateByProductWithBLOBsSelective(productForUpdate);
            if (updRes != 1) {
                throw new GFException("the product info not found.productId=" + productId);
            }
            List<GFLike> selectByUsernameAndProductId = repository.queryByUsernameAndProductId(userName, productId);
            if (selectByUsernameAndProductId != null && selectByUsernameAndProductId.size() > 0) {

                repository.deleteByUsernameAndProductId(userName, productId);
            }
        } else {
            throw new GFException(" the value of isLike  can only be true/false!");

        }

    }

    @Override
    public List<GFProductWithBLOBs> queryProductById(Integer productId) {
        return repository.queryProductById(productId);
    }

    @Override
    public Integer updateGFPlantTask(GFPlantTask record) {
        GFPlantTask queryPlantById = repository.queryPlantById(record.getTaskId());
        if (queryPlantById != null) {
            return repository.updateGFPlantTask(record);
        } else {
            return 0;
        }

    }

    @Override
    public List<GFProductWithBLOBs> queryProductByFarmId(Integer farmId) {

        return repository.queryProductByFarmId(farmId);
    }

    @Override
    public List<GFLike> queryByUsernameAndProductId(String userName, Integer productId) {
        return repository.queryByUsernameAndProductId(userName, productId);
    }
}
