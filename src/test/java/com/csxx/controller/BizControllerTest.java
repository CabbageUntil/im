package com.csxx.controller;

import com.csxx.mapper.contact.TelDetailListMapperTest;
import com.csxx.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelDetailListMapperTest.class)
@Import(Application.class)
public class BizControllerTest {

    private static final String URL_PREFIX = "/im/webOrg";

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult result = mockMvc.perform(post(URL_PREFIX.concat("/login"))
                .param("token", "6099GNyNEwlop1Aznsaev6pV3dF46Rnsx1Up8DG4FtPMMIaZjA%2FDh2MZ7no5oewCfH%2FXbdOONcGyYdlQwvVD6e%2FAg2tQZcPKZB3Rf98Wl4CzpZCN%2BPph6o4"))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
