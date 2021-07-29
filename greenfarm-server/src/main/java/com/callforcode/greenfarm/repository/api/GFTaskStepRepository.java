package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.vo.CarbonCredit;

import java.util.List;

public interface GFTaskStepRepository {

    List<CarbonCredit> selectActualFootprint(String userName);

}
