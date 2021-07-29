package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFCommentAddVo;
import com.callforcode.greenfarm.vo.GFCommentVo;
import com.callforcode.greenfarm.vo.GFTopicAddVo;
import com.callforcode.greenfarm.vo.GFTopicVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Topic API")
@RequestMapping("/topic")
public interface GFTopicController {
    @ApiOperation("query topics by community id")
    @GetMapping("/queryByCommunityId")
    ResultVo<List<GFTopicVo>> queryByCommunityId(@RequestParam(value = "username") String userName, @RequestParam(value = "id") String communityId);

    @ApiOperation("query topics by username")
    @GetMapping("/queryByUserName")
    ResultVo<List<GFTopicVo>> queryByUserName(@RequestParam(value = "username") String userName);

    @ApiOperation("create topic")
    @Transactional
    @PostMapping("/add")
    ResultVo insert(@RequestBody GFTopicAddVo vo);

    @ApiOperation("revise topic")
    @Transactional
    @PutMapping("/revise")
    ResultVo update(@RequestBody GFTopicVo vo);

    @ApiOperation("add comment on topic")
    @Transactional
    @PostMapping("/addComment")
    ResultVo addComment(@RequestBody GFCommentAddVo gfCommentVo);

    @ApiOperation("query comments on topic")
    @GetMapping("/queryComment")
    ResultVo<List<GFCommentVo>> queryComment(@RequestParam(value = "topicId") String topicId);

    @ApiOperation("query topic by topic id and username")
    @GetMapping("/queryByTopicId")
    ResultVo<GFTopicVo> queryByTopicId(@RequestParam(value = "username") String userName, @RequestParam(value = "topicId") String topicId);

    @ApiOperation("like topic")
    @Transactional
    @PutMapping("/like")
    ResultVo like(@RequestParam(value = "topicId") String topicId, @RequestParam(value = "isLike") String isLike, @RequestParam(value = "username") String userName);

}
