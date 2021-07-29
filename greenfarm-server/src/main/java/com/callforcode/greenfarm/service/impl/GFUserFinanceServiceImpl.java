package com.callforcode.greenfarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFUserFinance;
import com.callforcode.greenfarm.repository.api.GFUserFinanceRepository;
import com.callforcode.greenfarm.service.api.GFUserFinanceService;
import com.callforcode.greenfarm.vo.GFUserFinanceVo;

public class GFUserFinanceServiceImpl implements GFUserFinanceService {
    @Autowired
    private GFUserFinanceRepository repository;

    @Override
    public GFUserFinance addUserFinance(GFUserFinanceVo record) {
        int count = repository.insertUserFinance(record.voToEntity(record));
        if (count > 0) {
            return record.voToEntity(record);
        }
        return null;
    }

    @Override
    public List<GFUserFinance> queryUserFinanceByConditions(GFUserFinanceVo record) {
        return repository.selectByWhere(record.voToEntity(record));
    }

    @Override
    public int deleteUserFinanceByPidAndUserName(GFUserFinanceVo record) {
        return repository.deleteByPidAndUserName(record.voToEntity(record));
    }

}
