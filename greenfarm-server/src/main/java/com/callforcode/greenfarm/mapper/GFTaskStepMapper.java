package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.vo.CarbonCredit;
import org.apache.ibatis.annotations.Mapper;
import com.callforcode.greenfarm.entity.GFTaskStep;

import java.util.List;

@Mapper
public interface GFTaskStepMapper {

    int deleteByPrimaryKey(Integer stepId);

    int insert(GFTaskStep record);

    int insertSelective(GFTaskStep record);

    GFTaskStep selectByPrimaryKey(Integer stepId);

    int updateByPrimaryKeySelective(GFTaskStep record);

    int updateByPrimaryKey(GFTaskStep record);

    List<CarbonCredit> selectActualFootprint(String userName);
    
    List<GFTaskStep> queryTaskStepByTaskId(Integer taskId);

}
