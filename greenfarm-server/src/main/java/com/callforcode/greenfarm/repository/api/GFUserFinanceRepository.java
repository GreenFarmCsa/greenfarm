package com.callforcode.greenfarm.repository.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFUserFinance;

public interface GFUserFinanceRepository {

    int insertUserFinance(GFUserFinance record);

    List<GFUserFinance> selectByWhere(GFUserFinance record);

    int updateByPidAndUserName(GFUserFinance record);

    int deleteByPidAndUserName(GFUserFinance record);

}
