package com.callforcode.greenfarm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.impl.GFProductControllerImpl;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.GFProductAddVo;
import com.callforcode.greenfarm.vo.GFProductCommentAddVo;
import com.callforcode.greenfarm.vo.GFProductVo;
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
public class GFProductServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GFProductControllerImpl gfProductControllerImpl;

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
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testQueryTopProducts() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/queryTopN").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testQueryProductsByCategory() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/queryByCategory").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("category", "c").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testQueryPlantProducts() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/queryPlant").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("username", "MVCTEST").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testQueryComment() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/queryComment").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("productId", "1000").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testAddProduct() throws Exception {
        GFProductAddVo gfProductAddVo = new GFProductAddVo();
        gfProductAddVo.setLandId(199);
        gfProductAddVo.setIntroduction("test");
        gfProductControllerImpl.addProduct(gfProductAddVo);
    }

    /*
     * @Test
     * 
     * @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
     * "classpath:db/GFProductServiceImpl-test-insert.sql" })
     * 
     * @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
     * "classpath:db/GFProductServiceImpl-test-delete.sql" }) public void
     * testPackProduct() throws Exception { GFProductBoxVo gfProductBoxVo = new
     * GFProductBoxVo(); gfProductBoxVo.setLandId(199);
     * gfProductBoxVo.setFarmId(199); gfProductBoxVo.setCategory("test");
     * gfProductBoxVo.setStepId(199);
     * gfProductControllerImpl.packProduct(gfProductBoxVo); }
     */

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testUpdateProduct() throws Exception {
        GFProductVo gfProductVo = new GFProductVo();
        gfProductVo.setLandId(199);
        gfProductVo.setIntroduction("test");
        gfProductVo.setProductId(1000);
        gfProductControllerImpl.updateProduct(gfProductVo);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testDeleteProduct() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/product/delete").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("id", "1000").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testAddComment() throws Exception {
        GFProductCommentAddVo gfProductCommentAddVo = new GFProductCommentAddVo();
        gfProductCommentAddVo.setCommentContent("test");
        gfProductCommentAddVo.setCommentImage("test");
        gfProductCommentAddVo.setProductId(1000);
        gfProductCommentAddVo.setUsername("MVCTEST");
        gfProductCommentAddVo.setCreateTime(null);
        gfProductCommentAddVo.setModifyTime(null);
        gfProductCommentAddVo.setRemark("test");
        gfProductControllerImpl.addComment(gfProductCommentAddVo);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testLike() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/product/delete").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("id", "1000").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFCommunityServiceImpl-test-insert.sql" })
    public void testDeleteComment() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/product/deleteComment").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("commentId", "1").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testSelectBySearchText() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/query").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("searchText", "1000").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-insert.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
            "classpath:db/GFProductServiceImpl-test-delete.sql" })
    public void testQueryProductByProductId() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/queryById").accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").param("productId", "1000").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVo resultVo = JSONObject.parseObject(result.getResponse().getContentAsString(), ResultVo.class);
        Assert.assertTrue(resultVo.getResultCode() == ResultCode.OK);
    }

}
