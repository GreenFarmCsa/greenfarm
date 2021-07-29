package com.callforcode.greenfarm.repository.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.callforcode.greenfarm.entity.GFLive;
import com.callforcode.greenfarm.mapper.GFLiveMapper;
import com.callforcode.greenfarm.repository.api.GFLiveRepository;
import com.callforcode.greenfarm.util.DateToolUtils;

public class GFLiveRepositoryImpl implements GFLiveRepository {
    @Autowired
    private GFLiveMapper mapper;

    @Override
    public GFLive add(GFLive record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        record.setTickTime(DateToolUtils.getCurrDate());
        int insert = mapper.insert(record);
        if (insert == 1) {
            return record;
        }
        return null;
    }

    @Override
    public List<GFLive> queryByLive(GFLive record) {
        return mapper.selectByWhere(record);
    }

    @Override
    public int updateByLiveSelective(GFLive record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<GFLive> getLivesByMinutes(Date customDate) {
        return mapper.getLivesByMinutes(customDate);
    }

    @Override
    public List<Integer> getLiveIdListBySeconds(Date customDate) {
        return mapper.getLiveIdListBySeconds(customDate);
    }

    @Override
    public int updateStatusByIdLiveList(List<Integer> idLiveList) {
        return mapper.updateStatusByIdLiveList(idLiveList);
    }

}
