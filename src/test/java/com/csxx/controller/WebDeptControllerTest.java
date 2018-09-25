package com.csxx.controller;

import com.csxx.controller.webOrg.WebDeptController;
import com.csxx.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebDeptController.class)
@Import(Application.class)
public class WebDeptControllerTest {
    @Test
    public void allDeptMent() throws Exception {
    }

    @Test
    public void test1() throws Exception {
    }

}