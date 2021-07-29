package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFProduct;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;

public interface GFProductRepository {

    int deleteByProductId(Integer productId);

    int add(GFProductWithBLOBs record);

    GFProductWithBLOBs queryByProductId(Integer productId);

    List<GFProductWithBLOBs> queryByProductWithBLOBs(GFProductWithBLOBs record);

    int updateByProductWithBLOBsSelective(GFProductWithBLOBs record);

    int updateByProductWithBLOBsWithBLOBs(GFProductWithBLOBs record);

    List<GFProductWithBLOBs> queryBySearchText(String searchText);

    List<GFProductWithBLOBs> queryProductById(Integer productId);

    Integer updateGFPlantTask(GFPlantTask record);

    GFPlantTask queryPlantById(Integer taskId);

    List<GFProductWithBLOBs> queryProductByFarmId(Integer farmId);

    int addByProduct(GFLike record);

    int deleteByUsernameAndProductId(String userName, Integer productId);

    List<GFLike> queryByUsernameAndProductId(String userName, Integer productId);

    List<GFProduct> queryProductByCommentator(String userName);
}
