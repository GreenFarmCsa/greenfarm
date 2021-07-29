package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.dto.BlockChainProductModel;
import com.callforcode.greenfarm.vo.GFBlockChainProductVo;

import java.util.List;

public interface BlockChainService {

    List<BlockChainProductModel> record(Integer productId);

    List<BlockChainProductModel> generate(Integer productId);

    List<GFBlockChainProductVo> query(Integer productId);

}
