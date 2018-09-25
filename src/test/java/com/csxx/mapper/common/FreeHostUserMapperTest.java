package com.csxx.mapper.common;

import com.csxx.Application;
import com.csxx.mapper.contact.TelAddressListMapperTest;
import com.csxx.mapper.unifiedLogin.FreeHostUserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelAddressListMapperTest.class)
@Import(Application.class)
public class FreeHostUserMapperTest {

    @Autowired
    private FreeHostUserMapper mapper;

    @Test
    public void getExists() throws Exception {
        Integer result = mapper.getExists("xxx");
        Assert.assertNotNull(result);
    }

}