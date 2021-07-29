package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFLike;
import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;

public interface GFProductService {

    int deleteByProductId(Integer productId);

    GFProductWithBLOBs add(GFProductWithBLOBs record);

    List<GFProductWithBLOBs> queryBySearchText(String searchText);

    List<GFProductWithBLOBs> queryByProductWithBLOBs(GFProductWithBLOBs record);

    GFProductWithBLOBs updateByProductWithBLOBsWithBLOBs(GFProductWithBLOBs record);

    void productLike(Integer productId, String isLike, String userName);

    List<GFProductWithBLOBs> queryProductById(Integer productId);

    Integer updateGFPlantTask(GFPlantTask record);

    List<GFProductWithBLOBs> queryProductByFarmId(Integer farmId);

    List<GFLike> queryByUsernameAndProductId(String username, Integer productId);
}
