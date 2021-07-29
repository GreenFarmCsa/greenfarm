package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFRentFarmVo;
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
public class GFRentServiceImplTest {
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
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFRentServiceImpl-test-delete.sql", "classpath:db/GFRentServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFRentServiceImpl-test-delete.sql"})
    public void queryRentLands() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rent/queryRentLands")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "jack").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        List<GFRentFarmVo> rentFarmVos = ((JSONArray) resultVo.getData()).toJavaList(GFRentFarmVo.class);
        Assert.assertTrue(ResultCode.OK == resultVo.getResultCode());
        Assert.assertTrue(rentFarmVos.size() == 1);

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFRentServiceImpl-test-delete.sql", "classpath:db/GFRentServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFRentServiceImpl-test-delete.sql"})
    public void createRent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rent/add")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                        +
                        "  \"area\": 456,\n"
                        +
                        "  \"confirmCrops\": \"test\",\n"
                        +
                        "  \"farmId\": 666,\n"
                        +
                        "  \"landId\": 555,\n"
                        +
                        "  \"remark\": \"string\",\n"
                        +
                        "  \"rentPrice\": 999999,\n"
                        +
                        "  \"seedId\": 1,\n"
                        +
                        "  \"username\": \"jack\"\n"
                        +
                        "}").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(ResultCode.OK == resultVo.getResultCode());
    }

}


