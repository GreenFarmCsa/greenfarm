package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.vo.GFDashboardFootprintVo;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFCarbonFootprintServiceImplTest {

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
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql", "classpath:db/GFCarbonFootprintServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql"})
    public void queryCarbonFootprint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carbon-footprint/query")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("username", "jz").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo<List<GFCarbonFootprint>> data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getData() != null && data.getData().size() > 0);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql"})
    public void addCarbonFootprint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carbon-footprint/add")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new JSONObject().fluentPut("farmId", 1).fluentPut("username", "jz").toJSONString())
                .contentType(MediaType.APPLICATION_JSON).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql", "classpath:db/GFCarbonCreditDailyServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFCarbonFootprintServiceImpl-test-delete.sql"})
    public void queryFromDashboard() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carbon-footprint/queryFromDashboard")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("username", "jz").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo<GFDashboardFootprintVo> data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getData() != null);
    }

}
