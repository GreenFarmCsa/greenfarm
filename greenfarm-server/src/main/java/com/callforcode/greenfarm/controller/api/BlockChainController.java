package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.dto.BlockChainProductModel;
import com.callforcode.greenfarm.vo.GFBlockChainProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(tags = "Block-Chain API")
@RequestMapping("/block")
public interface BlockChainController {

    @ApiOperation("generate product info")
    @GetMapping("/generate")
    List<BlockChainProductModel> generate(Integer productId);

    @ApiOperation("record")
    @PostMapping("/record")
    List<BlockChainProductModel> record(Integer productId);

    @ApiOperation("query")
    @GetMapping("/query")
    List<GFBlockChainProductVo> query(Integer productId);

}
