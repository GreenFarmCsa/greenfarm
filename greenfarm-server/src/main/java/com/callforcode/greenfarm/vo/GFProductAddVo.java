package com.callforcode.greenfarm.vo;

import com.callforcode.greenfarm.entity.GFProductWithBLOBs;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GFProductAddVo {

    private Integer taskId;
    
    private Integer stepId;

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

    public static GFProductWithBLOBs convertProductVo2ProductWithBLOBS(GFProductAddVo gfProductAddVo) {
        GFProductWithBLOBs product = new GFProductWithBLOBs();
        if (gfProductAddVo != null) {
            product.setFarmId(gfProductAddVo.getFarmId());
            // product.setProductId(gfProductAddVo.getProductId());
            product.setLandId(gfProductAddVo.getLandId());
            product.setUsername(gfProductAddVo.getUsername());
            product.setProductName(gfProductAddVo.getProductName());
            product.setCategory(gfProductAddVo.getCategory());
            product.setIntroduction(gfProductAddVo.getIntroduction());
            product.setWeight(gfProductAddVo.getWeight());
            product.setPrice(gfProductAddVo.getPrice());
            product.setCarbonCredit(gfProductAddVo.getCarbonCredit());
            product.setNumber(gfProductAddVo.getNumber());
            product.setSaleNumber(gfProductAddVo.getSaleNumber());
            product.setLikeNumber(gfProductAddVo.getLikeNumber());
            product.setIdentifications(gfProductAddVo.getIdentifications());
            product.setCarbonEmission(gfProductAddVo.getCarbonEmission());
            product.setDonateAmount(gfProductAddVo.getDonateAmount());
            product.setCreateTime(gfProductAddVo.getCreateTime());
            product.setModifyTime(gfProductAddVo.getModifyTime());
            product.setRemark(gfProductAddVo.getRemark());
            // product.setTaskId(gfProductAddVo.getTaskId());
            String imageUrlTmp = "";
            for (String tmpImageUrl : gfProductAddVo.getImageUrl()) {
                imageUrlTmp += tmpImageUrl + ",";
            }
            if (!"".equals(imageUrlTmp)) {
                imageUrlTmp = imageUrlTmp.substring(0, imageUrlTmp.length() - 1);
                product.setImageUrl(imageUrlTmp);
            }
            String vedioUrlTmp = "";
            for (String tmpVedioUrlUrl : gfProductAddVo.getVedioUrl()) {
                vedioUrlTmp += tmpVedioUrlUrl + ",";
            }
            if (!"".equals("vedioUrlTmp")) {
                vedioUrlTmp = vedioUrlTmp.substring(0, vedioUrlTmp.length() - 1);
                product.setVedioUrl(vedioUrlTmp);
            }
        }
        return product;
    }

}
