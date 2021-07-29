package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFCarbonCreditDaily;
import com.callforcode.greenfarm.vo.CarbonCredit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFCarbonCreditDailyMapper {

    int deleteByPrimaryKey(Integer creditDailyId);

    int insert(GFCarbonCreditDaily record);

    int insertSelective(GFCarbonCreditDaily record);

    GFCarbonCreditDaily selectByPrimaryKey(Integer creditDailyId);

    int updateByPrimaryKeySelective(GFCarbonCreditDaily record);

    int updateByPrimaryKey(GFCarbonCreditDaily record);

    GFCarbonCreditDaily selectByUserNameAndDate(GFCarbonCreditDaily record);

    List<CarbonCredit> selectByUserNameAndType(GFCarbonCreditDaily record);

}
