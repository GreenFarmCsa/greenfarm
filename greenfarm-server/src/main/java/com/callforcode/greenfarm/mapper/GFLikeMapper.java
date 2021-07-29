package com.callforcode.greenfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.callforcode.greenfarm.entity.GFLike;

@Mapper
public interface GFLikeMapper {
    int deleteByPrimaryKey(Integer likeId);

    int insert(GFLike record);

    int insertSelective(GFLike record);

    GFLike selectByPrimaryKey(Integer likeId);

    int updateByPrimaryKeySelective(GFLike record);

    int updateByPrimaryKey(GFLike record);

    GFLike queryGFLikeByUserNameAndTopicId(@Param("userName") String userName, @Param("topicId") Integer topicId);

    int insertByProduct(GFLike record);
    
    int deleteByUsernameAndProductId(@Param("username") String username, @Param("productId") Integer productId);
    
    List<GFLike> selectByUsernameAndProductId(@Param("username") String username, @Param("productId") Integer productId);

}
