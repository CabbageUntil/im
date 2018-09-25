package com.csxx.utils;

import com.csxx.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JWTUtilTest.class)
@Import(Application.class)
public class JWTUtilTest {
}