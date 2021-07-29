package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.controller.api.GFFileController;
import com.callforcode.greenfarm.exception.DownloadFileException;
import com.callforcode.greenfarm.exception.UploadFileException;
import com.callforcode.greenfarm.service.api.GFFileService;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@AllArgsConstructor
@RestController
public class GFFileControllerImpl implements GFFileController {

    private GFFileService service;

    @Override
    public ResultVo<String> upload(@RequestParam("file") MultipartFile file) {
        String data = "";
        try {
            data = service.upload(file);
        } catch (IOException e) {
            throw new UploadFileException("upload file error:" + e.getMessage());
        }
        ResultVo<String> result = new ResultVo<>();
        if (!"".equals(data)) {
            result.setData(data);
            result.setMessage("upload file success");
            return result;
        } else {
            throw new UploadFileException("upload file for update database error");
        }

    }

    @Override
    public void download(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        try {
            File file = new File(url);
            String name = file.getName();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            String substring = name.substring(name.lastIndexOf(".") + 1);
            if ("mp4".equals(substring)) {
                response.setContentType("video/mpeg4");
            } else {
                response.setContentType("application/octet-stream");
            }
            byte[] download = service.download(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(download);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            throw new DownloadFileException("file not found");
        }

    }

    @Override
    public void play(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.reset();

        Long rangeStart = 0L;
        Long rangeEnd = 0L;

        String rangeHeader = request.getHeader("Range");
        if (StringUtils.isNotBlank(rangeHeader)) {
            String[] ranges = rangeHeader.substring(rangeHeader.indexOf("=") + 1).split("-");
            rangeStart = Long.valueOf(ranges[0]);
            rangeEnd = Long.valueOf(ranges[1]);
        }

        response.setContentType("video/mp4");
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        File file = new File(url);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(rangeStart);
        byte[] bytes = new byte[(int) (rangeEnd - rangeStart) + 1];
        int len = randomAccessFile.read(bytes);
        long fileLength = file.length();
        response.setContentLength(len);
        response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, len);
        outputStream.close();
        randomAccessFile.close();
    }

    @Override
    public ResultVo deleteByUrl(String url) {
        service.deleteByUrl(url);
        return new ResultVo();
    }

    @Override
    public ResultVo cleanDirtyFile(String path) throws IOException {
        service.cleanDirtyFile(path);
        return new ResultVo();
    }
}
