package com.callforcode.greenfarm.repository.impl;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.entity.GFTaskDetail;
import com.callforcode.greenfarm.entity.GFTopic;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.mapper.GFFarmMapper;
import com.callforcode.greenfarm.mapper.GFProductMapper;
import com.callforcode.greenfarm.mapper.GFTaskDetailMapper;
import com.callforcode.greenfarm.mapper.GFTopicMapper;
import com.callforcode.greenfarm.mapper.GFUserMapper;
import com.callforcode.greenfarm.repository.api.GFFileRepository;

public class GFFileRepositoryImpl implements GFFileRepository {

    @Autowired
    private GFFarmMapper gfFarmMapper;

    @Autowired
    private GFProductMapper gfProductMapper;

    @Autowired
    private GFTopicMapper gfTopicMapper;

    @Autowired
    private GFUserMapper gfUserMapper;

    @Autowired
    private GFTaskDetailMapper gfTaskDetailMapper;

    @Override
    public int upload(String url) {
        return 1;
    }

    @Override
    public File download(String url) {
        return null;
    }

    @Override
    public Set<String> getUrl() {

        GFFarmWithBLOBs gfFarmWithBLOBs = new GFFarmWithBLOBs();
        List<GFFarmWithBLOBs> farmSelectByWhere = gfFarmMapper.selectByWhere(gfFarmWithBLOBs);
        Set<String> urlSet = new HashSet<>();
        for (int i = 0; i < farmSelectByWhere.size(); i++) {
            String[] iconUrlSplit = farmSelectByWhere.get(i).getIconUrl().split(",");
            for (int j = 0; j < iconUrlSplit.length; j++) {
                urlSet.add(iconUrlSplit[j]);
            }
            String[] imageUrlSplit = farmSelectByWhere.get(i).getImageUrl().split(",");
            for (int j = 0; j < imageUrlSplit.length; j++) {
                urlSet.add(imageUrlSplit[j]);
            }
            String[] vrUrlSplit = farmSelectByWhere.get(i).getVrUrl().split(",");
            for (int j = 0; j < vrUrlSplit.length; j++) {
                urlSet.add(vrUrlSplit[j]);
            }
        }
        GFProductWithBLOBs gfProductWithBLOBs = new GFProductWithBLOBs();
        List<GFProductWithBLOBs> productSelectByWhere = gfProductMapper.selectByWhere(gfProductWithBLOBs);
        for (int i = 0; i < productSelectByWhere.size(); i++) {
            String[] imageUrlSplit = productSelectByWhere.get(i).getImageUrl().split(",");
            for (int j = 0; j < imageUrlSplit.length; j++) {
                urlSet.add(imageUrlSplit[j]);
            }
            String[] vedioUrlSplit = productSelectByWhere.get(i).getVedioUrl().split(",");
            for (int j = 0; j < vedioUrlSplit.length; j++) {
                urlSet.add(vedioUrlSplit[j]);
            }
        }
        GFTaskDetail gfTaskDetail = new GFTaskDetail();
        List<GFTaskDetail> taskDetailSelectByWhere = gfTaskDetailMapper.selectByWhere(gfTaskDetail);
        for (int i = 0; i < taskDetailSelectByWhere.size(); i++) {
            String operRecord = taskDetailSelectByWhere.get(i).getOperRecord();
            String[] split = toJsonArray(operRecord);
            for (int k = 0; k < split.length; k++) {
                urlSet.add(split[k]);
            }
        }
        GFTopic gfTopic = new GFTopic();
        List<GFTopic> topicSelectByWhere = gfTopicMapper.selectByWhere(gfTopic);
        for (int i = 0; i < topicSelectByWhere.size(); i++) {
            String[] topicImageUrlSplit = topicSelectByWhere.get(i).getTopicImageUrl().split(",");
            for (int j = 0; j < topicImageUrlSplit.length; j++) {
                urlSet.add(topicImageUrlSplit[j]);
            }
        }
        GFUser gfUser = new GFUser();
        List<GFUser> userSelectByWhere = gfUserMapper.selectByWhere(gfUser);
        for (int i = 0; i < userSelectByWhere.size(); i++) {
            String[] iconUrlSplit = userSelectByWhere.get(i).getIconUrl().split(",");
            for (int j = 0; j < iconUrlSplit.length; j++) {
                urlSet.add(iconUrlSplit[j]);
            }
        }
        return urlSet;
    }
    
    public String[] toJsonArray(String operRecord) {
        if (operRecord != null) {
            JSONArray parseArray = JSONObject.parseArray(operRecord);
            if (parseArray.size() > 0) {
                for (int j = 0; j < parseArray.size(); j++) {
                    JSONObject parseObject = JSONObject.parseObject(parseArray.get(j).toString());
                    String[] split = parseObject.get("url").toString().split(",");
                    return split;
                }
            }
        }
        return null;
    }

}
