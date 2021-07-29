package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.vo.ResultVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFFinanceProductServiceImplTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;

    @Before
    public void setup() {
        this.session = new MockHttpSession();
        GFUser user = new GFUser();
        user.setUsername("test");
        user.setPassword("123456");
        session.setAttribute(GreenFarmConst.GRF_LOGIN_SESSION, user);
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql",
            "classpath:db/GFFinanceProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql" })
    public void testQueryByUserName() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/finance-product/queryByUserName")
                        .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").param("username", "MVCTEST")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql",
            "classpath:db/GFFinanceProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql" })
    public void testQueryFinanceProduct() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/finance-product/queryById").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("id", "1111").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql",
            "classpath:db/GFFinanceProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql" })
    public void testInsertUserFinance() throws Exception {
        JSONObject josnObject = new JSONObject();
        josnObject.put("userFinanceId", 2223);
        josnObject.put("financeProductId", 1111);
        josnObject.put("username", "Test");
        josnObject.put("financeLimit", "tets");
        josnObject.put("startDate", "2020-6-7");
        josnObject.put("endDate", "2020-7-7");
        josnObject.put("remark", "test");
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/finance-product/apply").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON)
                        .content(josnObject.toJSONString()).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql",
            "classpath:db/GFFinanceProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql" })
    public void testBreakUserFinance() throws Exception {
        JSONObject josnObject = new JSONObject();
        josnObject.put("financeProductId", 1111);
        josnObject.put("username", "MVCTEST");
        josnObject.put("financeLimit", "tets");
        josnObject.put("startDate", "2020-6-7");
        josnObject.put("endDate", "2020-7-7");
        josnObject.put("remark", "test");
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/finance-product/break").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON)
                        .content(josnObject.toJSONString()).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql",
            "classpath:db/GFFinanceProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFFinanceProductServiceImpl-test-delete.sql" })
    public void testQueryAllWithSignStatus() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/finance-product/queryAllWithSignStatus")
                        .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").param("username", "MVCTEST")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

}
