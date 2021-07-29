package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFLandAddVo;
import com.callforcode.greenfarm.vo.GFLandReviseVo;
import com.callforcode.greenfarm.vo.GFLandVo;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFLandServiceImplTest {
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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql", "classpath:db/GFFarmControllerImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql"})
    public void addLand() throws Exception {
        GFLandAddVo addVo = new GFLandAddVo();
        addVo.setFarmId(101);
        addVo.setArea(10);
        String res = BeanUtils.convertJson(addVo);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/land/add")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(res).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql", "classpath:db/GFLandServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql"})
    public void updateLand() throws Exception {
        GFLandReviseVo reviseVo = new GFLandReviseVo();
        reviseVo.setFarmId(5);
        reviseVo.setLandId(888);
        reviseVo.setArea(222);
        String res = BeanUtils.convertJson(reviseVo);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/land/revise")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(res).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql", "classpath:db/GFLandServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFLandServiceImpl-test-delete.sql"})
    public void queryLandByLandId() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/land/queryByFarmId")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "888").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        List<GFLandVo> gfLandVos = ((JSONArray) resultVo.getData()).toJavaList(GFLandVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

}

