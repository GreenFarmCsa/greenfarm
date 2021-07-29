package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFSeedVo;
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
public class GFSeedServiceImplTest {
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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFSeedServiceImpl-test-delete.sql", "classpath:db/GFSeedServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFSeedServiceImpl-test-delete.sql"})
    public void querySeeds() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/seed/query")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo<List<GFSeedVo>> resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertEquals(1, resultVo.getData().size());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFSeedServiceImpl-test-delete.sql", "classpath:db/GFSeedServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = {"classpath:db/GFSeedServiceImpl-test-delete.sql"})
    public void querySeedBySeedId() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/seed/query")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON).param("id", "100").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo<List<GFSeedVo>> resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
        Assert.assertEquals(1, resultVo.getData().size());
    }
}


