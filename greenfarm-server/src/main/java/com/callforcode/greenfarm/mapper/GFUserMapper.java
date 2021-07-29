package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFUser;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GFUserMapper {
    int deleteByPrimaryKey(String username);

    int insert(GFUser record);

    int insertSelective(GFUser record);

    GFUser selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(GFUser record);

    int updateByPrimaryKey(GFUser record);
    
    List<GFUser> selectByWhere(GFUser record);
}
