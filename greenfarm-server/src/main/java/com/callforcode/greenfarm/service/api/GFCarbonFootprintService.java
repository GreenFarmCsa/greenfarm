package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.vo.GFDashboardFootprintVo;

import java.util.List;

public interface GFCarbonFootprintService {

    List<GFCarbonFootprint> queryCarbonFootprint(String userName);

    int addCarbonFootprint(GFCarbonFootprint gfCarbonFootprint);

    GFDashboardFootprintVo queryFromDashboard(String userName);
}
