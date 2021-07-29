package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.List;

@Data
public class RevisePlantTaskVo {

    private Integer stepId;

    private List<FileInfo> operRecord;

    @Data
    public static class FileInfo {
        private String type;

        private String url;
    }
}
