package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.vo.GFOrderDetailAddVo;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFOrderServiceImplTest {

    @Autowired
    private MockMvc mvc;

    private List<GFOrderDetailAddVo> detailList;

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
    public void testCreateOrder() throws Exception {
        detailList = new ArrayList<GFOrderDetailAddVo>();
        GFOrderDetailAddVo vo = new GFOrderDetailAddVo();
        vo.setCount(1);
        vo.setImageUrl("a.png,b.png");
        vo.setProductId(1000);
        detailList.add(vo);
        JSONObject josnObject = new JSONObject();
        josnObject.put("address", "test");
        josnObject.put("money", 1111);
        josnObject.put("username", "TEST-TESTA");
        josnObject.put("carbonCredit", 50);
        josnObject.put("remark", "test");
        josnObject.put("detail", detailList);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/order/add").accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).content(josnObject.toJSONString())
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(StringUtils.isNotBlank(data.getResultCode().name()));

    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFOrderServiceImpl-test-delete.sql", "classpath:db/GFUserServiceImpl-ex-test-delete.sql",
            "classpath:db/GFOrderServiceImpl-test-insert.sql", "classpath:db/GFUserServiceImpl-ex-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFOrderServiceImpl-test-delete.sql", "classpath:db/GFUserServiceImpl-ex-test-delete.sql"})
    public void testQueryPurchased() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/order/queryPurchased").accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON)
                .param("username", "TEST-TESTA").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFOrderServiceImpl-test-delete.sql", "classpath:db/GFProductServiceImpl-test-delete.sql",
            "classpath:db/GFOrderServiceImpl-test-insert.sql", "classpath:db/GFProductServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFOrderServiceImpl-test-delete.sql", "classpath:db/GFProductServiceImpl-test-delete.sql"})
    public void testQuerySold() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/order/querySold").accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).param("username", "MVCTEST")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

}
