package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.dto.*;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.FarmNotFoundException;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.repository.api.*;
import com.callforcode.greenfarm.service.api.RecommendationService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private GFCommunityRepository communityRepository;

    @Autowired
    private GFUserRepository userRepository;

    @Autowired
    private GFProductRepository productRepository;

    @Autowired
    private GFFarmRepository farmRepository;

    @Autowired
    private GFRentRepository rentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GFOrderRepository orderRepository;

    @Autowired
    private GFFinanceProductRepository financeProductRepository;

    @Value("${green-farm.ai.url:http://10.225.186.186:5000}")
    private String aiEngineUrl;

    private Gson g = new GsonBuilder().create();

    @Override
    public List<GFFarm> queryFarms(String userName) {
        //request params
        Map<String, Object> param = new HashMap<>();
        fillInputParam(userName, param);
        //find all rent info
        List<GFRent> rents = rentRepository.queryAllRentLands();
        List<GFRentDTO> rentDTOS = new ArrayList<>();
        if (Objects.nonNull(rents) || rents.size() > 0) {
            rents.forEach(rent -> {
                GFFarm gfFarm = farmRepository.queryByFarmId(rent.getFarmId());
                if (Objects.isNull(gfFarm)) {
                    throw new FarmNotFoundException("farm not found.farm_id is " + rent.getFarmId());
                }
                GFRentDTO dto = new GFRentDTO();
                dto.setArea(rent.getArea() + "");
                dto.setConfirmCrops(rent.getConfirmCrops());
                dto.setCreateTime(RecommendationServiceImpl.dateToString(rent.getCreateTime()));
                dto.setFarmId(rent.getFarmId() + "");
                dto.setLandId(rent.getLandId() + "");
                dto.setFarmLocation(gfFarm.getLocation());
                dto.setModifyTime(RecommendationServiceImpl.dateToString(rent.getModifyTime()));
                dto.setRentEndTime(RecommendationServiceImpl.dateToString(rent.getRentEndTime()));
                dto.setRentStartTime(RecommendationServiceImpl.dateToString(rent.getRentStartTime()));
                dto.setRentPrice(rent.getRentPrice() + "");
                dto.setRentId(rent.getRentId() + "");
                dto.setUsername(rent.getUsername());
                rentDTOS.add(dto);
            });
        }
        param.put("rent_list", rentDTOS);
        //find all farm info
        List<GFFarmWithBLOBs> farms = farmRepository.queryByFarmWithBLOBs(new GFFarmWithBLOBs());
        List<GFFarmInfoDTO> farmInfoDTOs = new ArrayList<>();
        if (Objects.nonNull(farms) || farms.size() > 0) {
            farms.forEach(e -> {
                GFFarmInfoDTO farm = new GFFarmInfoDTO();
                farm.setFarmId(e.getFarmId() + "");
                farm.setIdleArea(e.getIdleArea() + "");
                farm.setLocation(e.getLocation());
                farm.setRentPeriod(e.getRentPeriod());
                farm.setSuitedCrops(e.getSuitedCrops());
                farm.setTotalArea(e.getTotalArea() + "");
                farmInfoDTOs.add(farm);
            });
        }
       
        param.put("farm", farmInfoDTOs);
        //find comment_productnames by username
        List<GFProduct> products = productRepository.queryProductByCommentator(userName);
        List<String> productNames = new ArrayList<>();
        if (Objects.nonNull(products) || products.size() > 0) {
            products.forEach(e -> {
                productNames.add(e.getProductName());
            });
        }
        param.put("comment_products", productNames);
        String paramStr = BeanUtils.convertJson(param);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(aiEngineUrl + GreenFarmConst.GRF_FARM_URL, paramStr, String.class);
        String body = responseEntity.getBody();
        Type type = new TypeToken<AIRecommFarmModel<List<GFFarmDTO>>>() {
        }.getType();
        AIRecommFarmModel<List<GFFarmDTO>> o = g.fromJson(body, type);
        List<GFFarmDTO> gfFarmDTOS = o.getRecommend_farm_list();
        List<GFFarm> gfFarms = new ArrayList<>();
        if (Objects.nonNull(gfFarmDTOS) && gfFarmDTOS.size() > 0) {
            gfFarmDTOS.forEach(f -> {
                GFFarm gfFarm = farmRepository.queryByFarmId(Integer.valueOf(f.getFarm_id()));
                if (Objects.nonNull(gfFarm)) {
                    gfFarms.add(gfFarm);
                }
            });
            return gfFarms;
        } else {
            throw new FarmNotFoundException("farm info not found.userName=" + userName);
        }
    }

    @Override
    public List<GFProductWithBLOBs> queryProducts(String userName) {
        //request params
        Map<String, Object> param = new HashMap<>();
        fillInputParam(userName, param);
        //find order related product
        List<GFOrderCartRlnProductDTO> orderCartRlnProductDTOS = orderRepository.selectOrderRlnProductInfo(null);
        param.put("order_list", orderCartRlnProductDTOS);
        //find comment_products by username
        List<GFProduct> products = productRepository.queryProductByCommentator(userName);
        List<String> productNames = new ArrayList<>();
        if (Objects.nonNull(products) || products.size() > 0) {
            products.forEach(e -> {
                productNames.add(e.getProductName());
            });
        }
        param.put("comment_products", productNames);
        String paramStr = BeanUtils.convertJson(param);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(aiEngineUrl + GreenFarmConst.GRF_PRODUCT_URL, paramStr, String.class);
        Type type = new TypeToken<AIRecommProductModel<List<GFProductDTO>>>() {
        }.getType();
        AIRecommProductModel<List<GFProductDTO>> model = g.fromJson(responseEntity.getBody(), type);
        List<GFProductDTO> gfProductDTOS = model.getRecommend_product_list();
        List<GFProductWithBLOBs> gfProductWithBLOBss = new ArrayList<>();
        if (Objects.nonNull(gfProductDTOS) && gfProductDTOS.size() > 0) {
            gfProductDTOS.forEach(f -> {
                GFProductWithBLOBs gfProductWithBLOBs = productRepository.queryByProductId(Integer.valueOf(f.getProduct_id()));
                if (Objects.nonNull(gfProductWithBLOBs)) {
                    gfProductWithBLOBss.add(gfProductWithBLOBs);
                }
            });
            return gfProductWithBLOBss;
        } else {
            throw new GFException("product info not found.userName=" + userName);
        }
    }

    @Override
    public List<GFFinanceProduct> queryFinanceProducts(String userName) {
        //request params
        Map<String, Object> param = new HashMap<>();
        param.put("username", userName);
        //find all user
        GFUser user = userRepository.queryUserInfo(userName);
        if (Objects.nonNull(user)) {
            param.put("location", user.getLocation());
            param.put("carbon_credit", user.getCarbonCredit());
            param.put("carbon_medals", user.getCarbonMedals());
            param.put("rolename", user.getRolename());
            param.put("sex", user.getSex());
            param.put("create_time", dateToString(user.getCreateTime()));
            param.put("modify_time", dateToString(user.getModifyTime()));
        }
        //find all finance product
        List<GFFinanceProdDTO> financeProducts = financeProductRepository.selectAll();
        param.put("finance_products", financeProducts);
        String paramStr = BeanUtils.convertJson(param);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(aiEngineUrl + GreenFarmConst.GRF_FINANCIAL_PRODUCT_URL, paramStr, String.class);
        Type type = new TypeToken<AIRecommFinanceProductModel<List<GFFinanceProductDTO>>>() {
        }.getType();
        AIRecommFinanceProductModel<List<GFFinanceProductDTO>> model = g.fromJson(responseEntity.getBody(), type);
        List<GFFinanceProductDTO> gfFinanceProductDTOS = model.getFinance_product_list();
        List<GFFinanceProduct> gfFinanceProducts = new ArrayList<>();
        if (Objects.nonNull(gfFinanceProductDTOS) && gfFinanceProductDTOS.size() > 0) {
            gfFinanceProductDTOS.forEach(f -> {
                GFFinanceProduct gfFinanceProduct = financeProductRepository.selectByPid(Integer.valueOf(f.getFinance_product_id()));
                if (Objects.nonNull(gfFinanceProduct)) {
                    gfFinanceProducts.add(gfFinanceProduct);
                }
            });
            return gfFinanceProducts;
        } else {
            throw new GFException("finance_product info not found.userName=" + userName);
        }
    }

    private static String dateToString(Date date) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    private void fillInputParam(String userName, Map<String, Object> param) {
        param.put("username", userName);
        List<GFCommunity> gfCommunities = communityRepository.queryCommunityInfoByUsername(userName);
        GFCommunity community = new GFCommunity();
        if (Objects.nonNull(gfCommunities) && gfCommunities.size() > 0) {
            community = gfCommunities.get(0);
        }
        param.put("community_id", community.getCommunityId());
        GFUser user = userRepository.queryUserInfo(userName);
        if (Objects.nonNull(user)) {
            param.put("location", user.getLocation());
            param.put("carbon_credit", user.getCarbonCredit());
            param.put("carbon_medals", user.getCarbonMedals());
        }

    }

}
