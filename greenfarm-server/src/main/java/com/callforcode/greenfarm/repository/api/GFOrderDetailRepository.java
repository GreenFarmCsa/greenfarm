package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFOrderDetail;
import com.callforcode.greenfarm.entity.GFProduct;

public interface GFOrderDetailRepository {

    int insertList(List<GFOrderDetail> list);

    List<GFProduct> queryProductByList(List<GFOrderDetail> list);

    int updateProductByList(List<GFProduct> list);
}
