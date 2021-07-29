package com.callforcode.greenfarm.entity;

public class GFFarmWithBLOBs extends GFFarm {
    private String imageUrl;

    private String vrUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getVrUrl() {
        return vrUrl;
    }

    public void setVrUrl(String vrUrl) {
        this.vrUrl = vrUrl == null ? null : vrUrl.trim();
    }
}
