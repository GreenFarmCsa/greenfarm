package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFStepTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GFStepTemplateMapper {

    int deleteByPrimaryKey(Integer templateId);

    int insert(GFStepTemplate record);

    int insertSelective(GFStepTemplate record);

    GFStepTemplate selectByPrimaryKey(Integer templateId);

    int updateByPrimaryKeySelective(GFStepTemplate record);

    int updateByPrimaryKey(GFStepTemplate record);
    
    List<GFStepTemplate> queryStepTemplateBySeedId(Integer seedId);

    GFStepTemplate queryStepTemplateByStepId(Integer stepId);

}
