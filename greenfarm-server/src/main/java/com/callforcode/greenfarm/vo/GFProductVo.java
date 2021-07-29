package com.callforcode.greenfarm.vo;

import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import com.callforcode.greenfarm.util.BeanUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class GFProductVo {

    private Integer productId;

    private Integer farmId;

    private Integer landId;

    private String username;

    private String productName;

    private String category;

    private String introduction;

    private String weight;

    private BigDecimal price;

    private Integer carbonCredit;

    private Integer number;

    private Integer saleNumber;

    private Integer likeNumber;

    private String identifications;

    private BigDecimal carbonEmission;

    private BigDecimal donateAmount;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private List<String> imageUrl;

    private List<String> vedioUrl;

    // private boolean islike;

    public static GFProductWithBLOBs convertProductVo2ProductWithBLOBS(GFProductVo gfProductVo) {
        GFProductWithBLOBs product = new GFProductWithBLOBs();
        if (gfProductVo != null) {
            product.setFarmId(gfProductVo.getFarmId());
            product.setProductId(gfProductVo.getProductId());
            product.setLandId(gfProductVo.getLandId());
            product.setUsername(gfProductVo.getUsername());
            product.setProductName(gfProductVo.getProductName());
            product.setCategory(gfProductVo.getCategory());
            product.setIntroduction(gfProductVo.getIntroduction());
            product.setWeight(gfProductVo.getWeight());
            product.setPrice(gfProductVo.getPrice());
            product.setCarbonCredit(gfProductVo.getCarbonCredit());
            product.setNumber(gfProductVo.getNumber());
            product.setSaleNumber(gfProductVo.getSaleNumber());
            product.setLikeNumber(gfProductVo.getLikeNumber());
            product.setIdentifications(gfProductVo.getIdentifications());
            product.setCarbonEmission(gfProductVo.getCarbonEmission());
            product.setDonateAmount(gfProductVo.getDonateAmount());
            product.setCreateTime(gfProductVo.getCreateTime());
            product.setModifyTime(gfProductVo.getModifyTime());
            product.setRemark(gfProductVo.getRemark());
            // product.setProductId(gfProductVo.getProductId());
            String imageUrlTmp = "";
            for (String tmpImageUrl : gfProductVo.getImageUrl()) {
                imageUrlTmp += tmpImageUrl + ",";
            }
            if (!"".equals(imageUrlTmp)) {
                imageUrlTmp = imageUrlTmp.substring(0, imageUrlTmp.length() - 1);
                product.setImageUrl(imageUrlTmp);
            }
            String vedioUrlTmp = "";
            for (String tmpVedioUrlUrl : gfProductVo.getVedioUrl()) {
                vedioUrlTmp += tmpVedioUrlUrl + ",";
            }
            if (!"".equals("vedioUrlTmp")) {
                vedioUrlTmp = vedioUrlTmp.substring(0, vedioUrlTmp.length() - 1);
                product.setVedioUrl(vedioUrlTmp);
            }

        }
        return product;
    }

    public static GFProductVo convertProductWithBLOBS2ProductVo(GFProductWithBLOBs productBLOBs) {
        GFProductVo productVo = BeanUtils.copyBean(productBLOBs, GFProductVo.class);
        List<String> imageUrls = new ArrayList<String>();
        if (productBLOBs.getImageUrl() != null) {
            for (String url : productBLOBs.getImageUrl().split(",")) {
                imageUrls.add(url);
            }
        }
        productVo.setImageUrl(imageUrls);

        List<String> vedioUrls = new ArrayList<String>();
        if (productBLOBs.getVedioUrl() != null) {
            for (String url : productBLOBs.getVedioUrl().split(",")) {
                vedioUrls.add(url);
            }
        }
        productVo.setVedioUrl(vedioUrls);
        return productVo;
    }
}
