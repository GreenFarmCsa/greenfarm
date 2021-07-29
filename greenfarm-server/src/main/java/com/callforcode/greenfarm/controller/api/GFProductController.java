package com.callforcode.greenfarm.controller.api;

import java.util.List;

import com.callforcode.greenfarm.vo.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produce API")
@RequestMapping("/product")
public interface GFProductController {

    @ApiOperation("query TopN produce")
    @GetMapping("/queryTopN")
    ResultVo<List<GFProductVo>> queryTopProducts();

    @ApiOperation("query produce by category")
    @GetMapping("/queryByCategory")
    ResultVo<List<GFProductVo>> queryProductsByCategory(@RequestParam(value = "category") String category);

    @ApiOperation("query produce by multiple conditions")
    @GetMapping("/query")
    ResultVo<List<GFProductVo>> queryBySearchText(
            @RequestParam(value = "searchText", required = false) String searchText);

    @ApiOperation("query planted produce")
    @GetMapping("/queryPlant")
    ResultVo<List<GFProductVo>> queryPlantProducts(@RequestParam(value = "username") String userName);

    @SuppressWarnings("rawtypes")
    @ApiOperation("add comment on produce")
    @PostMapping("/addComment")
    ResultVo addComment(@RequestBody GFProductCommentAddVo gfProductCommentVo);

    @ApiOperation("query comments on produce")
    @GetMapping("/queryComment")
    ResultVo<List<GFProductCommentVo>> queryComment(@RequestParam(value = "productId") String productId);

    @SuppressWarnings("rawtypes")
    @ApiOperation("delete comments on produce")
    @Transactional
    @DeleteMapping("/deleteComment")
    ResultVo deleteComment(@RequestParam(value = "commentId") String commentId);

    @SuppressWarnings("rawtypes")
    @ApiOperation("put produce online")
    @Transactional
    @PostMapping("/add")
    ResultVo addProduct(@RequestBody GFProductAddVo gfProductAddVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("put produce online")
    @Transactional
    @PostMapping("/box")
    ResultVo packProduct(@RequestBody GFProductBoxVo gfProductBoxVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("revise produce")
    @Transactional
    @PutMapping("/revise")
    ResultVo updateProduct(@RequestBody GFProductVo gfProductVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("remove produce")
    @Transactional
    @DeleteMapping("/delete")
    ResultVo deleteProduct(@RequestParam(value = "id") String productId);

    @SuppressWarnings("rawtypes")
    @ApiOperation("like produce")
    @Transactional
    @PutMapping("/like")
    ResultVo like(@RequestParam(value = "productId") String productId, @RequestParam(value = "isLike") String isLike,
                  @RequestParam(value = "username") String userName);

    @ApiOperation("query produce by product id")
    @GetMapping("/queryById")
    ResultVo<List<GFProductVo>> queryProductByProductId(@RequestParam(value = "productId") Integer productId);

    @ApiOperation("query produce by farm id")
    @GetMapping("/queryByFarmId")
    ResultVo<List<GFProductVo>> queryProductByFarmId(@RequestParam(value = "farmId") Integer farmId);
}
