package com.callforcode.greenfarm.mapper;

import com.callforcode.greenfarm.entity.GFCommunityMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GFCommunityMemberMapper {
    int insert(GFCommunityMember record);

    int insertSelective(GFCommunityMember record);

    int checkIfCommunityMemberExistsByIdAndUserName(GFCommunityMember record);

}
