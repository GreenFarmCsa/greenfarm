package com.callforcode.greenfarm.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.callforcode.greenfarm.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "File API")
@RequestMapping("/file")
public interface GFFileController {

    @ApiOperation("upload file")
    @PostMapping("/upload")
    ResultVo<String> upload(MultipartFile file);

    @ApiOperation("download file")
    @GetMapping("/download")
    void download(String url, HttpServletResponse response) throws Exception;

    @ApiOperation("play video")
    @GetMapping("/play")
    void play(String url, HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    @ApiOperation("delete file")
    @GetMapping("/deleteByUrl")
    ResultVo deleteByUrl(String url);
    
    @ApiOperation("cleanDirtyFile ")
    @GetMapping("/cleanDirtyFile")
    ResultVo cleanDirtyFile(String path) throws IOException;

}
