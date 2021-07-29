package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.vo.GFCommunityVo;
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
public class GFCommunityServiceImplTest {

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
            scripts = {"classpath:db/GFCommunityServiceImpl-test-delete.sql", "classpath:db/GFCommunityServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFCommunityServiceImpl-test-delete.sql"})
    public void queryByFarmId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/community/queryByFarmId")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo<GFCommunityVo> data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getData() != null);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:db/GFCommunityMemberServiceImpl-test-delete.sql", "classpath:db/GFCommunityMemberServiceImpl-test-insert.sql"})
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFCommunityMemberServiceImpl-test-delete.sql"})
    public void queryByUserName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/community/queryByUserName")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "jz").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo<List<GFCommunityVo>> data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getData() != null && data.getData().size() > 0);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:db/GFCommunityMemberServiceImpl-test-delete.sql"})
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"classpath:db/GFCommunityMemberServiceImpl-test-delete.sql"})
    public void join() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/community/join")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new JSONObject().fluentPut("communityId", 1).fluentPut("username", "jz").toJSONString())
                .contentType(MediaType.APPLICATION_JSON).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue("OK".equals(data.getResultCode().name()));
    }

}
