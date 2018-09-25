package com.csxx.repository;

import com.csxx.repository.contact.TelAddressListRepository;
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

import java.util.Arrays;

@Configuration
@EnableAutoConfiguration()
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelAddressListRepositoryTest.class)
@ComponentScan(basePackages = {"com.csxx.*"})
@EnableJpaRepositories(basePackages = {"com.csxx.repository"})
@EntityScan("com.csxx.dao")
@MapperScan(basePackages = "com.csxx.mapper")
public class TelAddressListRepositoryTest {

    @Autowired
    private TelAddressListRepository repository;

    @Test

    public void deleteByIdIn() throws Exception {

        repository.deleteByIdIn(Arrays.asList(83, 84));

    }

}