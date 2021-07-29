package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFTaskDetail;

@Mapper
public interface GFTaskDetailMapper {

    int deleteByPrimaryKey(Integer detailId);

    int insert(GFTaskDetail record);

    int insertSelective(GFTaskDetail record);

    GFTaskDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(GFTaskDetail record);

    int updateByPrimaryKeyWithBLOBs(GFTaskDetail record);

    int updateByPrimaryKey(GFTaskDetail record);

    GFTaskDetail queryTaskDetailByStepId(Integer stepId);

    int updateTaskDetailByStepId(GFTaskDetail record);

    List<GFTaskDetail> queryPlantDetailByProductId(Integer productId);
    
    List<GFTaskDetail> selectByWhere(GFTaskDetail record);

}
