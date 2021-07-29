package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFGFShoppingCartVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Shopping Cart API")
@RequestMapping("/shopping-cart")
public interface GFShoppingCartController {

    @ApiOperation("add product to cart")
    @Transactional
    @PostMapping("/add")
    ResultVo<GFGFShoppingCartVo> addShoppingCart(@RequestParam(value = "productId") String productId,
                                                 @RequestParam(value = "username") String username, @RequestParam(value = "count") int count);

    @SuppressWarnings("rawtypes")
    @ApiOperation("remove product from shopping cart")
    @Transactional
    @PostMapping("/remove")
    ResultVo removeShoppingCart(@RequestParam(value = "productId") String productId,
                                @RequestParam(value = "username") String username);

    @SuppressWarnings("rawtypes")
    @ApiOperation("revise shopping cart")
    @Transactional
    @PostMapping("/update")
    ResultVo updateShoppingCart(@RequestParam(value = "productId") String productId,
                                @RequestParam(value = "username") String username, @RequestParam(value = "count") int count);

    @ApiOperation("query shopping cart by username")
    @PostMapping("/queryByUserName")
    ResultVo<List<GFGFShoppingCartVo>> queryListByUserName(@RequestParam(value = "username") String username);
}
