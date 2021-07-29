package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFFarm;
import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.mapper.GFFarmMapper;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.util.RegularUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFFarmRepositoryImpl implements GFFarmRepository {

    @Autowired
    private GFFarmMapper gffarmMapper;

    @Override
    public int addFarmWithBLOBs(GFFarmWithBLOBs record) {
        record.setCreateTime(DateToolUtils.getCurrDate());
        record.setModifyTime(record.getCreateTime());
        return gffarmMapper.insertSelective(record);
    }

    @Override
    public GFFarmWithBLOBs queryByFarmId(Integer farmId) {
        return gffarmMapper.selectByPrimaryKey(farmId);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByFarmWithBLOBs(GFFarmWithBLOBs record) {
        return gffarmMapper.selectByWhere(record);
    }

    @Override
    public int updateByFarmWithBLOBsSelective(GFFarmWithBLOBs record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return gffarmMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByFarm(GFFarm record) {
        record.setModifyTime(DateToolUtils.getCurrDate());
        return gffarmMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<GFFarmWithBLOBs> queryFarmInfoByCommentator(String userName) {
        return gffarmMapper.selectFarmInfoByCommentator(userName);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByMulConditions(String searchText) {
        return gffarmMapper.selectByMulConditions(searchText);
    }

    @Override
    public List<GFFarmWithBLOBs> queryByTotalArea(String totalArea) {
        String strTotalAreaBefore = "";
        String strTotalAreaAfter = "";
        if (totalArea.contains("-")) {
            strTotalAreaBefore = totalArea.substring(0, totalArea.indexOf("-"));
            strTotalAreaAfter = totalArea.substring(totalArea.indexOf("-") + 1, totalArea.length());
        }
        strTotalAreaBefore = strTotalAreaBefore.trim();
        strTotalAreaAfter = strTotalAreaAfter.trim();
        List<GFFarmWithBLOBs> farmList = null;
        farmList = gffarmMapper.queryByTotalArea(
                RegularUtils.getPatternFlag("^\\d+$", strTotalAreaBefore) ? Integer.parseInt(strTotalAreaBefore) : null,
                RegularUtils.getPatternFlag("^\\d+$", strTotalAreaAfter) ? Integer.parseInt(strTotalAreaAfter) : null);
        return farmList;
    }

    @Override
    public int deleteFarmRlnInfoByFarmId(Integer farmId) {
        int res;
        //clean farm rln task info
        gffarmMapper.deleteFarmRlnTaskInfoByFarmId(farmId);
        //clean farm and rln  community info
        res = gffarmMapper.deleteFarmRlnCommunityInfoByFarmId(farmId);
        //clean farm and rln land, rent info
        gffarmMapper.deleteFarmRlnRentInfoByFarmId(farmId);
        return res;
    }

    @Override
    public GFFarm queryByLandId(Integer landId) {
        return gffarmMapper.queryByLandId(landId);
    }

}
