package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.impl.GFLiveControllerImpl;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFLiveAddVo;
import com.callforcode.greenfarm.vo.GFLiveVo;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFLiveServiceImplTest {

    @Autowired
    private GFLiveControllerImpl gfLiveControllerImpl;
    
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
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-delete.sql" })
    public void testQueryByUserName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/live/queryByUserName")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("username", "test").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-delete.sql" })
    public void testQueryByFarmId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/live/queryByFarmId")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("farmId", "199").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-delete.sql" })
    public void testInsert() throws Exception {
        GFLiveAddVo gfLiveAddVo = new GFLiveAddVo();
        gfLiveAddVo.setFarmId(188);
        gfLiveAddVo.setRemark("test");
        gfLiveAddVo.setStepId(188);
        gfLiveControllerImpl.insert(gfLiveAddVo);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-delete.sql" })
    public void testUpdateLive() throws Exception {
        GFLiveVo gfLiveVo = new GFLiveVo();
        gfLiveVo.setLiveId(199);
        gfLiveVo.setFarmId(199);
        gfLiveVo.setIconUrl("test1");
        gfLiveVo.setRemark("test1");
        gfLiveVo.setStepId(199);
        gfLiveControllerImpl.updateLive(gfLiveVo);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:db/GFLiveServiceImpl-test-delete.sql" })
    public void testGetTickTime() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/live/tickTime")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("liveId", "199").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

}
