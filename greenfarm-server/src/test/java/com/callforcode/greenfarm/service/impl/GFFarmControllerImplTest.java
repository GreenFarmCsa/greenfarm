package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
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
public class GFFarmControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @Before
    public void setup() {
        this.session = new MockHttpSession();
        GFUser user = new GFUser();
        user.setUsername("test");
        user.setPassword("123456");
        session.setAttribute(GreenFarmConst.GRF_LOGIN_SESSION, user);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryAll() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryAll").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryByLocation() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryByLocation").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("location", "test").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryByProduct() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryByProductName").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("productName", "test").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQuery() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/query").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("searchText", "test").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryFarmById() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryById").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("id", "101").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryByUserName() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryByUserName").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("username", "test").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Transactional
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFFarmControllerImpl-test-insert.sql" })
    public void testQueryByTotalArea() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/farm/queryByTotalArea").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("totalArea", "12").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

}
