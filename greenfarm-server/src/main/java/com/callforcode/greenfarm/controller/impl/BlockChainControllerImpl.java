package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.BlockChainController;
import com.callforcode.greenfarm.dto.BlockChainProductModel;
import com.callforcode.greenfarm.service.api.BlockChainService;
import com.callforcode.greenfarm.vo.GFBlockChainProductVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BlockChainControllerImpl implements BlockChainController {

    private BlockChainService service;

    @Override
    public List<BlockChainProductModel> generate(Integer productId) {
        return service.generate(productId);
    }

    @Override
    public List<BlockChainProductModel> record(Integer productId) {
        return service.record(productId);
    }

    @Override
    public List<GFBlockChainProductVo> query(Integer productId) {
        return service.query(productId);
    }

}
