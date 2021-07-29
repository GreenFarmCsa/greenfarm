package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFUserFinance;

@Mapper
public interface GFUserFinanceMapper {
    int deleteByPrimaryKey(Integer farmId);

    int insert(GFUserFinance record);

    int insertSelective(GFUserFinance record);

    List<GFUserFinance> selectByWhere(GFUserFinance record);

    int updateByPrimaryKeySelective(GFUserFinance record);

    int updateByPrimaryKey(GFUserFinance record);

    int updateByPidAndUserName(GFUserFinance record);

    int deleteByPidAndUserName(GFUserFinance record);

    GFUserFinance selectByPrimaryKey(Integer userFinanceId);

}
