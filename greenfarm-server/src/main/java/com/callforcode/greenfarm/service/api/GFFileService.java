package com.callforcode.greenfarm.service.api;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface GFFileService {

    String upload(MultipartFile file) throws IllegalStateException, IOException;

    byte[] download(String url) throws Exception;
    
    void deleteByUrl(String url);
    
    void cleanDirtyFile(String path) throws IOException;
}
