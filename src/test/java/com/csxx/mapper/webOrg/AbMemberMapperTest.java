package com.csxx.mapper.webOrg;

import com.csxx.Application;
import com.csxx.vo.webOrg.Archive;
import com.csxx.vo.webOrg.MemberInfoVO;
import com.csxx.vo.webOrg.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbMemberMapperTest.class)
@Import(Application.class)
public class AbMemberMapperTest {

    @Autowired
    private AbMemberMapper abMemberMapper;

    @Test
    public void findArchiveByUsernameAndOrgId() throws Exception {
        Archive archive = abMemberMapper.findArchiveByUsernameAndOrgId("13246554606", 2);
        Assert.assertNotNull(archive);
    }

    @Test
    public void findAllCompanyByUsername() throws Exception {
    }

    @Test
    public void selectByOrgIdAndUsernameAndStatus() throws Exception {
        UserInfo userInfo = abMemberMapper.selectByOrgIdAndUsernameAndStatus(2, "13246554606", 1);
        Assert.assertNotNull(userInfo);
    }

    @Test
    public void fetchMemberListByFilterAndStatus() throws Exception {
        List<MemberInfoVO> memberInfoVOList = abMemberMapper.fetchMemberListByFilterAndStatus(2, null, 1);
        Assert.assertNotEquals(0, memberInfoVOList.size());
    }

}