package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.mapper.GFLikeMapper;
import com.callforcode.greenfarm.mapper.GFPlantTaskMapper;
import com.callforcode.greenfarm.mapper.GFProductMapper;
import com.callforcode.greenfarm.repository.api.GFProductRepository;
import com.callforcode.greenfarm.util.DateToolUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class GFProductRepositoryImpl implements GFProductRepository {

    @Autowired
    private GFProductMapper gfproductMapper;

    @Autowired
    private GFPlantTaskMapper gfPlantTaskMapper;

    @Autowired
    private GFLikeMapper gfLikeMapper;

    @Override
    public int deleteByProductId(Integer productId) {
        return gfproductMapper.deleteByPrimaryKey(productId);
    }

    @Override
    public int add(GFProductWithBLOBs record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return gfproductMapper.insert(record);
    }

    @Override
    public GFProductWithBLOBs queryByProductId(Integer productId) {
        return gfproductMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<GFProductWithBLOBs> queryByProductWithBLOBs(GFProductWithBLOBs record) {
        return gfproductMapper.selectByWhere(record);
    }

    @Override
    public int updateByProductWithBLOBsSelective(GFProductWithBLOBs record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return gfproductMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByProductWithBLOBsWithBLOBs(GFProductWithBLOBs record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return gfproductMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public List<GFProductWithBLOBs> queryBySearchText(String searchText) {
        return gfproductMapper.selectBySearchText(searchText);
    }

    @Override
    public List<GFProductWithBLOBs> queryProductById(Integer productId) {
        return gfproductMapper.queryProductById(productId);
    }

    @Override
    public Integer updateGFPlantTask(GFPlantTask record) {
        return gfPlantTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public GFPlantTask queryPlantById(Integer taskId) {
        return gfPlantTaskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public List<GFProductWithBLOBs> queryProductByFarmId(Integer farmId) {
        return gfproductMapper.queryProductByFarmId(farmId);
    }

    @Override
    public int addByProduct(GFLike record) {

        return gfLikeMapper.insertByProduct(record);
    }

    @Override
    public int deleteByUsernameAndProductId(String userName, Integer productId) {
        GFLike gfLike = new GFLike();
        gfLike.setUsername(userName);
        gfLike.setProductId(productId);

        return gfLikeMapper.deleteByUsernameAndProductId(userName, productId);
    }

    @Override
    public List<GFLike> queryByUsernameAndProductId(String userName, Integer productId) {
        GFLike gfLike = new GFLike();
        gfLike.setUsername(userName);
        gfLike.setProductId(productId);

        return gfLikeMapper.selectByUsernameAndProductId(userName, productId);
    }

    @Override
    public List<GFProduct> queryProductByCommentator(String userName) {
        return gfproductMapper.queryProductByCommentator(userName);
    }
}
