package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFCommentVo;
import com.callforcode.greenfarm.vo.GFTopicVo;
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
public class GFTopicServiceImplTest {
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
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql", "classpath:db/GFTopicServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void updateTopic() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/topic/revise")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"commentsCount\": 0,"
                        +
                        "  \"communityId\": 1,"
                        +
                        "  \"hasLiked\": true,"
                        +
                        "  \"iconUrl\": \"test\","
                        +
                        "  \"likeSum\": 0,"
                        +
                        "  \"remark\": \"string\","
                        +
                        "  \"topicContent\": \"string\","
                        +
                        "  \"topicId\": 777,"
                        +
                        "  \"topicImageUrl\": \"test\","
                        +
                        "  \"topicName\": \"test-name\","
                        +
                        "  \"username\": \"jz\"}").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql", "classpath:db/GFTopicServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void addTopic() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topic/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{"
                        +
                        "  \"communityId\": 1,"
                        +
                        "  \"likeSum\": 0,"
                        +
                        "  \"remark\": \"string\","
                        +
                        "  \"topicContent\": \"adsfsa\","
                        +
                        "  \"topicImageUrl\": \"sdf\","
                        +
                        "  \"topicName\": \"test-topic\","
                        +
                        "  \"username\": \"jz\""
                        +
                        "}").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void addComment() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topic/addComment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{"
                        +
                        "  \"commentContent\": \"test-content\","
                        +
                        "  \"remark\": \"test-remark\","
                        +
                        "  \"topicId\": 777,"
                        +
                        "  \"username\": \"jack\""
                        +
                        "}").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql", "classpath:db/GFTopicServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void queryCommentByTopicId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topic/queryComment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("topicId", "777").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        List<GFCommentVo> gfCommentVos = ((JSONArray) data.getData()).toJavaList(GFCommentVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
        Assert.assertTrue(gfCommentVos.size() == 1);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql", "classpath:db/GFTopicServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void queryTopicInfoById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topic/queryByTopicId")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("topicId", "777").param("username", "jz").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        GFTopicVo gfTopicVo = ((JSONObject) data.getData()).toJavaObject(GFTopicVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
        Assert.assertTrue(777 == gfTopicVo.getTopicId());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql", "classpath:db/GFTopicServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFTopicServiceImpl-test-delete.sql"})
    public void topicLike() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/topic/like")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("topicId", "777")
                .param("username", "jz")
                .param("isLike", "false").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
    }

}

