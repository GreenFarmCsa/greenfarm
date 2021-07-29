package com.callforcode.greenfarm.config;

import com.callforcode.greenfarm.repository.api.GFCarbonFootprintRepository;
import com.callforcode.greenfarm.repository.api.GFCommunityRepository;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.repository.api.GFFileRepository;
import com.callforcode.greenfarm.repository.api.GFFinanceProductRepository;
import com.callforcode.greenfarm.repository.api.GFLandRepository;
import com.callforcode.greenfarm.repository.api.GFLiveRepository;
import com.callforcode.greenfarm.repository.api.GFLiveVideoBatRepository;
import com.callforcode.greenfarm.repository.api.GFOrderDetailRepository;
import com.callforcode.greenfarm.repository.api.GFOrderRepository;
import com.callforcode.greenfarm.repository.api.GFPlantTaskRepository;
import com.callforcode.greenfarm.repository.api.GFProductCommentRepository;
import com.callforcode.greenfarm.repository.api.GFProductRepository;
import com.callforcode.greenfarm.repository.api.GFRentRepository;
import com.callforcode.greenfarm.repository.api.GFSeedRepository;
import com.callforcode.greenfarm.repository.api.GFShoppingCartRepository;
import com.callforcode.greenfarm.repository.api.GFTaskStepRepository;
import com.callforcode.greenfarm.repository.api.GFTopicRepository;
import com.callforcode.greenfarm.repository.api.GFUserFinanceRepository;
import com.callforcode.greenfarm.repository.api.GFUserRepository;
import com.callforcode.greenfarm.repository.impl.GFCarbonFootprintRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFCommunityRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFFarmRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFFileRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFFinanceProductRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFLandRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFLiveRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFLiveVideoBatRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFOrderDetailRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFOrderRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFPlantTaskRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFProductCommentRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFProductRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFRentRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFSeedRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFShoppingCartRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFTaskStepRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFTopicRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFUserFinanceRepositoryImpl;
import com.callforcode.greenfarm.repository.impl.GFUserRepositoryImpl;
import com.callforcode.greenfarm.service.api.*;
import com.callforcode.greenfarm.service.impl.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class ConfigService {

    @Bean("_primaryDataSource")
    @ConfigurationProperties(prefix = "green-farm.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("_jdbcTemplate")
    public JdbcTemplate createJdbcTemplate() {
        return new JdbcTemplate(primaryDataSource());
    }

    @Bean("_restTemplate")
    public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(5L)).setReadTimeout(Duration.ofSeconds(5L)).build();
    }

    @Bean
    public GFFarmRepository createGFFarmRepository() {
        return new GFFarmRepositoryImpl();
    }

    @Bean
    public GFFarmService createGFFarmService() {
        return new GFFarmServiceImpl();
    }

    @Bean
    public GFProductRepository createGFProductRepository() {
        return new GFProductRepositoryImpl();
    }

    @Bean
    public GFProductService createGFProductService() {
        return new GFProductServiceImpl();
    }

    @Bean
    public GFLandService createGFLandService() {
        return new GFLandServiceImpl();
    }

    @Bean
    public GFLandRepository createGFLandRepository() {
        return new GFLandRepositoryImpl();
    }

    @Bean
    public GFRentService createGFRentService() {
        return new GFRentServiceImpl();
    }

    @Bean
    public GFRentRepository createGFRentRepository() {
        return new GFRentRepositoryImpl();
    }

    @Bean
    public GFOrderRepository createGFOrderRepository() {
        return new GFOrderRepositoryImpl();
    }

    @Bean
    public GFOrderService createGFOrderService() {
        return new GFOrderServiceImpl();
    }

    @Bean
    public GFUserService createGFUserService() {
        return new GFUserServiceImpl();
    }

    @Bean
    public GFUserRepository createGFUserRepository() {
        return new GFUserRepositoryImpl();
    }

    @Bean
    public RecommendationService createRecommendationService() {
        return new RecommendationServiceImpl();
    }

    @Bean
    public GFShoppingCartService createGFShoppingCartService() {
        return new GFShoppingCartServiceImpl();
    }

    @Bean
    public GFShoppingCartRepository createGFShoppingCartRepository() {
        return new GFShoppingCartRepositoryImpl();
    }

    @Bean
    public GFFinanceProductService createGFFinanceProductService() {
        return new GFFinanceProductServiceImpl();
    }

    @Bean
    public GFFinanceProductRepository createGFFinanceProductRepository() {
        return new GFFinanceProductRepositoryImpl();
    }

    @Bean
    public GFUserFinanceService createGFUserFinanceService() {
        return new GFUserFinanceServiceImpl();
    }

    @Bean
    public GFUserFinanceRepository createGFUserFinanceRepository() {
        return new GFUserFinanceRepositoryImpl();
    }

    @Bean
    public GFLiveService createGFLiveService() {
        return new GFLiveServiceImpl();
    }

    @Bean
    public GFLiveRepository createGFLiveRepository() {
        return new GFLiveRepositoryImpl();
    }

    @Bean
    public GFSeedService createSeedService() {
        return new GFSeedServiceImpl();
    }

    @Bean
    public GFSeedRepository createSeedRepository() {
        return new GFSeedRepositoryImpl();
    }

    @Bean
    public GFPlantTaskService createPlantTaskService() {
        return new GFPlantTaskServiceImpl();
    }

    @Bean
    public GFPlantTaskRepository createPlantTaskRepository() {
        return new GFPlantTaskRepositoryImpl();
    }

    @Bean
    public GFProductCommentService createGFProductCommentService() {
        return new GFProductCommentServiceImpl();
    }

    @Bean
    public GFProductCommentRepository createProductCommentRepository() {
        return new GFProductCommentRepositoryImpl();
    }

    @Bean
    public GFFileService createGFFileService() {
        return new GFFileServiceImpl();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.ofBytes(10485760 * 1024));
        multipartConfigFactory.setMaxRequestSize(DataSize.ofBytes(10485760 * 1024));
        return multipartConfigFactory.createMultipartConfig();
    }

    @Bean
    public GFFileRepository createGFFileRepository() {
        return new GFFileRepositoryImpl();
    }

    @Bean
    public GFCarbonFootprintService createGFCarbonFootprintService() {
        return new GFCarbonFootprintServiceImpl();
    }

    @Bean
    public GFCarbonFootprintRepository createGFCarbonFootprintRepository() {
        return new GFCarbonFootprintRepositoryImpl();
    }

    @Bean
    public GFTaskStepRepository createGFTaskStepRepository() {
        return new GFTaskStepRepositoryImpl();
    }

    @Bean
    public GFOrderDetailService createGFOrderDetailService() {
        return new GFOrderDetailServiceImpl();
    }

    @Bean
    public GFOrderDetailRepository createGFOrderDetailRepository() {
        return new GFOrderDetailRepositoryImpl();
    }

    @Bean
    public GFCommunityRepository createCommunityRepository() {
        return new GFCommunityRepositoryImpl();
    }

    @Bean
    public GFCommunityService createCommunityService() {
        return new GFCommunityServiceImpl();
    }

    @Bean
    public GFTopicRepository createTopicRepository() {
        return new GFTopicRepositoryImpl();
    }

    @Bean
    public GFTopicService createTopicService() {
        return new GFTopicServiceImpl();
    }

    @Bean
    public BlockChainService createBlockChainService() {
        return new BlockChainServiceImpl();
    }

    @Bean
    public GFLiveVideoBatService createGFLiveVideoBatService() {
        return new GFLiveVideoBatServiceImpl();
    }

    @Bean
    public GFLiveVideoBatRepository createGFLiveVideoBatRepository() {
        return new GFLiveVideoBatRepositoryImpl();
    }

}
