package com.csxx.service.webOrg.impl;

import com.csxx.Application;
import com.csxx.i18nTest;
import com.csxx.service.webOrg.WebMemberService;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebMemberServiceImplTest.class)
@Import(Application.class)
public class WebMemberServiceImplTest {

    @Autowired
    private WebMemberService webMemberService;

    @Test
    public void memberList() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setMemberId(1);
        userInfo.setOrgId(2);
        ResponseEntity responseEntity = webMemberService.memberList("", 1, 10, null, userInfo, 1);
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void memberListDetail() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setMemberId(1);
        ResponseEntity responseEntity = webMemberService.memberListDetail(userInfo, 1);
        Assert.assertNotNull(responseEntity);
    }

}