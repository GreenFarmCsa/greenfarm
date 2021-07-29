package com.callforcode.greenfarm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.callforcode.greenfarm.entity.GFRent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GFRentMapper {
    int deleteByPrimaryKey(Integer rentId);

    int insert(GFRent record);

    int insertSelective(GFRent record);

    GFRent selectByPrimaryKey(Integer rentId);

    int updateByPrimaryKeySelective(GFRent record);

    int updateByPrimaryKey(GFRent record);

    List<GFRent> queryByUserName(String userName);

    List<GFRent> queryRentLandsByUsernameAndFarmId(@Param("userName") String userName, @Param("farmId") Integer farmId);

    List<GFRent> queryAllRentLands();

    GFRent querySubscribeByLandId(Integer landId);

}
