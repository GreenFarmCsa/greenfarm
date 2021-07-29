package com.callforcode.greenfarm.service.api;

import java.util.List;

import com.callforcode.greenfarm.entity.GFUserFinance;
import com.callforcode.greenfarm.vo.GFUserFinanceVo;

public interface GFUserFinanceService {

    GFUserFinance addUserFinance(GFUserFinanceVo record);

    List<GFUserFinance> queryUserFinanceByConditions(GFUserFinanceVo record);

    int deleteUserFinanceByPidAndUserName(GFUserFinanceVo record);

}
