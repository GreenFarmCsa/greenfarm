package com.callforcode.greenfarm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.callforcode.greenfarm.entity.GFUserFinance;
import com.callforcode.greenfarm.mapper.GFUserFinanceMapper;
import com.callforcode.greenfarm.repository.api.GFUserFinanceRepository;
import com.callforcode.greenfarm.util.DateToolUtils;

import java.util.List;

public class GFUserFinanceRepositoryImpl implements GFUserFinanceRepository {
    @Autowired
    private GFUserFinanceMapper mapper;

    @Override
    public int insertUserFinance(GFUserFinance record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return mapper.insert(record);
    }

    @Override
    public List<GFUserFinance> selectByWhere(GFUserFinance record) {
        return mapper.selectByWhere(record);
    }

    @Override
    public int updateByPidAndUserName(GFUserFinance record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        record.setCreateTime(null);
        return mapper.updateByPidAndUserName(record);
    }

    @Override
    public int deleteByPidAndUserName(GFUserFinance record) {
        return mapper.deleteByPidAndUserName(record);
    }

}
