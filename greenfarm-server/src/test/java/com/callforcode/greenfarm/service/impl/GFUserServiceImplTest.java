package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFUserVo;
import com.callforcode.greenfarm.vo.ResultVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import javax.servlet.http.HttpSession;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GFUserServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @Mock
    private HttpSession mockSession;

    @Before
    public void setup() {
        this.session = new MockHttpSession();
        GFUser gfUser = new GFUser();
        gfUser.setUsername("jack");
        session.setAttribute(GreenFarmConst.GRF_LOGIN_SESSION, gfUser);
        Mockito.when(mockSession.getAttribute(Mockito.anyString())).thenReturn(gfUser);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql", "classpath:db/GFUserServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql"})
    public void login() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "jack")
                .param("password", "123456").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getData() != null);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql", "classpath:db/GFUserServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql"})
    public void logout() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/logout")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "jack")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql", "classpath:db/GFUserServiceImpl-test-insert.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql"})
    public void queryUserInfoByUserName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/queryInfo")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "jack")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        GFUserVo gfUserVo = ((JSONObject) data.getData()).toJavaObject(GFUserVo.class);
        Assert.assertTrue(data.getResultCode() == ResultCode.OK);
        Assert.assertEquals("jack", gfUserVo.getUsername());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:db/GFUserServiceImpl-test-delete.sql"})
    public void addUserInfo() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        +
                        "  \"carbonCredit\": 0,"
                        +
                        "  \"carbonMedals\": ["
                        +
                        "  \"123\",\"222\""
                        +
                        "  ],"
                        +
                        "  \"description\": \"test-description\","
                        +
                        "  \"email\": \"1223445@sina.cn\","
                        +
                        "  \"fullname\": \"jack Ma\","
                        +
                        "  \"iconUrl\": \"/images/jack.png\","
                        +
                        "  \"location\": \"test-location\","
                        +
                        "  \"password\": \"123456\","
                        +
                        "  \"phone\": \"string\","
                        +
                        "  \"remark\": \"test-remark\","
                        +
                        "  \"rolename\": \"admin\","
                        +
                        "  \"sex\": \"1\","
                        +
                        "  \"username\": \"jack\""
                        +
                        "}").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo data = JSON.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(ResultCode.OK == data.getResultCode());
    }

}

