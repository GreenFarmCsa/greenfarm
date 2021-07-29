package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFLiveVideoBat {

    private String liveId;

    private Date modifyTime;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId == null ? null : liveId.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
