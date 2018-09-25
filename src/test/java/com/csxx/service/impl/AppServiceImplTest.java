package com.csxx.service.impl;

import com.csxx.service.app.impl.AppServiceImpl;
import com.csxx.vo.app.AppDTO;
import com.csxx.dto.app.form.AppQueryForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Configuration
@EnableAutoConfiguration()
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServiceImplTest.class)
@ComponentScan(basePackages = {"com.csxx.*"})
@EnableJpaRepositories(basePackages = {"com.csxx.repository"})
@EntityScan("com.csxx.dao")
@MapperScan(basePackages = "com.csxx.mapper")
public class AppServiceImplTest {

    @Autowired
    private AppServiceImpl appService;

    @Test
    public void query() throws Exception {
        AppQueryForm appQueryForm = new AppQueryForm();
        appQueryForm.setUserName("");
        List<AppDTO> appDTOList = appService.query(appQueryForm);
        Assert.assertNotEquals(0, appDTOList.size());
    }

    @Test
    public void operate() throws Exception {
    }

}