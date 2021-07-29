package com.callforcode.greenfarm.vo;

import com.callforcode.greenfarm.entity.GFUserFinance;
import com.callforcode.greenfarm.util.DateToolUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public class GFUserFinanceVo {
    private Integer userFinanceId;
    
    private Integer financeProductId;

    private String username;

    private String financeLimit;

    private String startDate;

    private String endDate;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public GFUserFinance voToEntity(GFUserFinanceVo vo) {
        GFUserFinance entity = new GFUserFinance();
        if (null != vo) {

            entity.setCreateTime(DateToolUtils.getCurrDate());
            if (StringUtils.isNotBlank(vo.getEndDate())) {
                entity.setEndDate(DateToolUtils.getCustomDate("yyyy-MM-dd", vo.getEndDate()));
            }
            if (StringUtils.isNotBlank(vo.getStartDate())) {
                entity.setStartDate(DateToolUtils.getCustomDate("yyyy-MM-dd", vo.getStartDate()));
            }
            entity.setUserFinanceId(vo.getUserFinanceId());
            entity.setFinanceLimit(vo.getFinanceLimit());
            entity.setFinanceProductId(vo.getFinanceProductId());
            entity.setRemark(vo.getRemark());
            entity.setUsername(vo.getUsername());
            return entity;
        }
        return null;
    }

    public GFUserFinanceVo entityToVo(GFUserFinance entity) {
        GFUserFinanceVo vo = new GFUserFinanceVo();
        if (null != entity) {
            vo.setCreateTime(DateToolUtils.getCurrDate());
            if (null != entity.getEndDate()) {
                vo.setEndDate(DateToolUtils.getDateStrByDate("yyyy-MM-dd", entity.getEndDate()));
            }
            if (null != entity.getStartDate()) {
                vo.setStartDate(DateToolUtils.getDateStrByDate("yyyy-MM-dd", entity.getStartDate()));
            }
            vo.setUserFinanceId(entity.getUserFinanceId());
            vo.setFinanceLimit(entity.getFinanceLimit());
            vo.setFinanceProductId(entity.getFinanceProductId());
            vo.setModifyTime(entity.getModifyTime());
            vo.setRemark(entity.getRemark());
            vo.setUsername(entity.getUsername());
            return vo;
        }
        return null;
    }
}
