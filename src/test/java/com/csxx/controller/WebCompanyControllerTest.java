package com.csxx.controller;

import com.csxx.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebCompanyControllerTest.class)
@Import(Application.class)
public class WebCompanyControllerTest {

    private static final String URL_PREFIX = "/webOrg";

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
    public void testCreateCom() throws Exception {
        String content = "{\"orgForm\":{\"orgName\":\"TEST\",\"attrB\":2,\"attrC\":3},\"objB\":{\"attrD\":4,\"attrE\":5}}";
        String result = mockMvc.perform(post(URL_PREFIX.concat("/createCom")).contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
