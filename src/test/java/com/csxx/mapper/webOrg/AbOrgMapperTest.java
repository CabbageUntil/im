package com.csxx.mapper.webOrg;

import com.csxx.Application;
import com.csxx.i18nTest;
import com.csxx.repository.webOrg.MapRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbOrgMapperTest.class)
@Import(Application.class)
public class AbOrgMapperTest {

    @Autowired
    private MapRepository mapRepository;

    @Test
    public void existsByOrgName() throws Exception {
        Map<Integer, String> resultMap = mapRepository.findAllCompanyByUsername("13246554606");
        Assert.assertNotNull(resultMap);
    }

}