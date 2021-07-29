package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;

import com.callforcode.greenfarm.vo.GFOrderDetailVo;

public class OrderDetailProductTree {
    private Integer orderId;

    private Integer productId;

    private Integer amount;
    
    private String productName;
    
    private String imageUrl;
    
    private String remark;
    
    private String userName;
    
    private BigDecimal price;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public GFOrderDetailVo entity2Vo(OrderDetailProductTree entity) {
        GFOrderDetailVo vo = new GFOrderDetailVo();
        if (null != entity) {
            vo.setCount(entity.getAmount());
            if (null != entity.getImageUrl()) {
                String[] url = entity.getImageUrl().split(",");
                entity.setImageUrl(url[0]);
            }
            vo.setImageUrl(entity.getImageUrl());
            vo.setOrderId(entity.getOrderId());
            vo.setProductId(entity.getProductId());
            vo.setRemark(entity.getRemark());
            vo.setProductName(entity.getProductName());
            BigDecimal priceN = (null == entity.getPrice()) ? new BigDecimal(0)
                    : (entity.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            vo.setPrice(priceN);
            return vo;
        }
        return null;
    }

}
