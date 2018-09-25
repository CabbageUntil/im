package com.csxx.mapper.app;

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
@SpringBootTest(classes = DianAppListMapperTest.class)
@ComponentScan(basePackages = {"com.csxx.*"})
@EnableJpaRepositories(basePackages = {"com.csxx.repository"})
@EntityScan("com.csxx.dao")
@MapperScan(basePackages = "com.csxx.mapper")
public class DianAppListMapperTest {

    @Autowired
    private DianAppListMapper mapper;

    @Test
    public void findAppByAttr() throws Exception {

        AppQueryForm appQueryForm = new AppQueryForm();
        appQueryForm.setAppName("%one%");
        List<AppDTO> result =  mapper.findAppByAttr(appQueryForm);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAppByUserName() throws Exception {

        AppQueryForm appQueryForm = new AppQueryForm();
        appQueryForm.setUserName("zhangsan");
        List<AppDTO> result =  mapper.findAppByUserName(appQueryForm);
        Assert.assertNotEquals(0, result.size());

    }

    @Test
    public void findAll() throws Exception {
        List<AppDTO> result = mapper.findAll();
        Assert.assertNotEquals(0, result.size());
    }

}