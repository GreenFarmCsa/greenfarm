package com.callforcode.greenfarm.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.callforcode.greenfarm.exception.DownloadFileException;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.repository.api.GFFileRepository;
import com.callforcode.greenfarm.service.api.GFFileService;
import com.callforcode.greenfarm.util.ClientIBM;

public class GFFileServiceImpl implements GFFileService {

    @Autowired
    private GFFileRepository gfFileRepository;

    @Value("${green-farm.file.upload-path}")
    private String uploadPath;

    @Value("${green-farm.file.move-path}")
    private String movePath;

    @Value("${green-farm.ibm.objectstorage.enable}")
    private Boolean enable;

    @Override
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (enable) {
            String uplaodIBM = ClientIBM.uploadIBM(uploadPath, file);
            if (ClientIBM.getCode400().equals(uplaodIBM) || ClientIBM.getCode404().equals(uplaodIBM)) {
                throw new DownloadFileException(uplaodIBM);
            } else {
                return uplaodIBM;
            }
        }
        String name = UUID.randomUUID().toString().replace("-", "");
        String originalFilename = file.getOriginalFilename();
        String concat = FilenameUtils.concat(uploadPath, name + "-" + originalFilename);
        file.transferTo(new File(concat));
        return concat;
    }

    @Override
    public byte[] download(String url) throws Exception {
        String path = url;
        if (enable) {
            path = uploadPath + url;
            ClientIBM.largeObjectDownload(url, path);
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
        byte[] buff = new byte[bufferedInputStream.available()];

        bufferedInputStream.read(buff);
        bufferedInputStream.close();
        return buff;
    }

    @Override
    public void deleteByUrl(String url) {
        File file = new File(url);
        String filename = file.getName();

        File movePathFile = new File(movePath + filename);
        if (file.isFile()) {
            try {
                FileUtils.moveFile(file, movePathFile);
            } catch (IOException e) {
                throw new GFException("move file error");
            }
            // boolean delete = file.delete();
            // return delete;
        } else {
            throw new GFException("url not file");
        }
    }

    @Override
    public void cleanDirtyFile(String path) throws IOException {
        File file = new File(path);
        Set<String> url = gfFileRepository.getUrl();
        if (file.isDirectory()) {
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                if (!url.contains(path + File.separator + list[i])) {
                    File movePathFile = new File(movePath + list[i]);
                    FileUtils.moveFile(new File(path + File.separator + list[i]), movePathFile);
                }
            }
        }
    }

}
