package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.*;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFPlantTaskServiceImplTest {

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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql", "classpath:db/GFPlantTaskServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql"})
    public void queryByUserName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/plant-task/query")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "jack").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        List<GFPlantTaskVo> gfPlantTaskVos = ((JSONArray) resultVo.getData()).toJavaList(GFPlantTaskVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertEquals(1, gfPlantTaskVos.size());
        Assert.assertTrue(888 == gfPlantTaskVos.get(0).getTaskId());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql", "classpath:db/GFPlantTaskServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql"})
    public void queryTaskDetail() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/plant-task/queryTaskDetail")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "777").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        GFPlantTaskDetailVo gfPlantTaskDetailVo = ((JSONObject) resultVo.getData()).toJavaObject(GFPlantTaskDetailVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertTrue(777 == gfPlantTaskDetailVo.getStepId());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql", "classpath:db/GFPlantTaskServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql"})
    public void queryTaskStep() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/plant-task/querySteps")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "888").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        List<GFPlantTaskStepVo> gfPlantTaskStepVos = ((JSONArray) resultVo.getData()).toJavaList(GFPlantTaskStepVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertEquals(1, gfPlantTaskStepVos.size());
        Assert.assertTrue(777 == gfPlantTaskStepVos.get(0).getStepId());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql", "classpath:db/GFPlantTaskServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql"})
    public void queryPlantTaskByProductId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/plant-task/queryPlantTaskByProductId")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("productId", "6").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        GFPlantTaskVo gfPlantTaskVo = ((JSONObject) resultVo.getData()).toJavaObject(GFPlantTaskVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertTrue(6 == gfPlantTaskVo.getProductId());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql", "classpath:db/GFPlantTaskServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFPlantTaskServiceImpl-test-delete.sql"})
    public void updatePlantTaskDetail() throws Exception {
        RevisePlantTaskVo revisePlantTaskVo = new RevisePlantTaskVo();
        revisePlantTaskVo.setStepId(777);
        List<RevisePlantTaskVo.FileInfo> operRecord = new ArrayList<>();
        RevisePlantTaskVo.FileInfo fileInfo = new RevisePlantTaskVo.FileInfo();
        fileInfo.setUrl("/home/wasuser/huangzhiming/files/f2632dc0ee1746b2a29411ac5dd29cf4-irrigation-777.png");
        fileInfo.setType("video");
        operRecord.add(fileInfo);
        revisePlantTaskVo.setOperRecord(operRecord);
        String content = JSON.toJSONString(revisePlantTaskVo);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/plant-task/revise")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

}



