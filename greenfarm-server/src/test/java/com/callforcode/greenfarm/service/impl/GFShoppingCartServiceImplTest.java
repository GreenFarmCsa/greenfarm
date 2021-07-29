package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.vo.ResultVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
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
public class GFShoppingCartServiceImplTest {

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
    @Transactional
    public void testInsertUserFinance001() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/shopping-cart/add").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).param("productId", "1234")
                        .param("username", "test").param("count", "1").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(StringUtils.isNotBlank(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql",
            "classpath:db/GFShoppingCartServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql" })
    public void testInsertUserFinance002() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/shopping-cart/add").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).param("productId", "1000")
                        .param("username", "MVCTEST").param("count", "1").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(StringUtils.isNotBlank(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql",
            "classpath:db/GFShoppingCartServiceImpl-test-insert.sql" })
    public void testRemoveShoppingCart() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/shopping-cart/remove").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).param("productId", "1000")
                        .param("username", "MVCTEST").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(StringUtils.isNotBlank(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql",
            "classpath:db/GFShoppingCartServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql" })
    public void testUpdateShoppingCart() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/shopping-cart//update").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).param("productId", "1000")
                        .param("username", "MVCTEST").param("count", "1").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(StringUtils.isNotBlank(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql",
            "classpath:db/GFShoppingCartServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFShoppingCartServiceImpl-test-delete.sql" })
    public void testQueryListByUserName() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/shopping-cart/queryByUserName")
                        .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON).param("username", "MVCTEST")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }
}
