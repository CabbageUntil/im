package com.csxx.mapper.contact;

import com.csxx.bo.contact.AutoMergePO;
import com.csxx.bo.contact.WebContactLine;
import com.csxx.Application;
import com.csxx.dao.contact.mybatisModel.TelUserList;
import com.csxx.vo.webContact.DeleteList;
import com.csxx.vo.webContact.SearchTreeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.csxx.vo.contacts.UserWithPhoneList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelUserListMapperTest.class)
@Import(Application.class)
public class TelUserListMapperTest {

    private final String OWNER = "98882289";

    @Autowired
    private TelUserListMapper telUserListMapper;

    @Test
    public void findByUserName() throws Exception {
        Integer ownerId = telUserListMapper.findByUserName("13147593333");
        Assert.assertNotNull(ownerId);
    }

    @Test
    public void findUserIdByName() throws Exception {
        Integer ownerId = telUserListMapper.findByUserName("13147593333");
        List<Integer> result = telUserListMapper.findUserIdByName(ownerId, "%水%");
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findUserIdByEmailOrMobile() throws Exception {
        Integer ownerId = telUserListMapper.findByUserName("13147593333");
        List<Integer> result = telUserListMapper.findUserIdByEmailOrMobile(ownerId, "%1%");
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllByOwner() throws Throwable {
        List<SearchTreeVO> result = telUserListMapper.findAllByOwner("xxx");
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllByOwnerAndFilter() {
        List<SearchTreeVO> result = telUserListMapper.findAllByOwnerAndFilter("xxx", "%1%");
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findNextIdByOwner() {
        Integer id = telUserListMapper.findNextIdByOwnerAndId("xxx", 1150);
        Assert.assertNotNull(id);
    }

    @Test
    public void findDelListByOwner() {
        PageHelper.startPage(1, 10, "id asc");
        List<DeleteList> result = telUserListMapper.findDelListByOwner("13147593333", null);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAllByPage() {
        PageHelper.startPage(1, 10, "id asc");
        List<TelUserList> result = telUserListMapper.selectAll();
        Assert.assertEquals(10, result.size());
    }

    @Test
    public void findUserIdByOwnerAndFilter() {
        String owner = "xxx";
        String filter = "x";
        List<Integer> result = telUserListMapper.findUserIdByOwnerAndFilterAndStatus(owner, filter, null)
                .stream().map(e -> e.getId()).collect(Collectors.toList());
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findContactLineByOwner() {
        List<WebContactLine> result = telUserListMapper.findContactLineByOwner("xxx", null);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void getUserStatus() {
        Integer result = telUserListMapper.getUserStatus("1154");
        Assert.assertNotNull(result);
    }

    @Test
    public void getAutoMergeByOwner() {
        List<AutoMergePO> result = telUserListMapper.getAutoMergeByOwner("98882289");
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    @Transactional
    public void getManualMergeByOwner() {
        List<AutoMergePO> manualMergeList = telUserListMapper.getManualMergeByOwnerAndIds(OWNER, null);
        List<List<AutoMergePO>> finalMergeList = new ArrayList<>();

        Iterator<AutoMergePO> iterator;

        Assert.assertNotEquals(0, finalMergeList.size());
    }

    @Test
    public void getName() throws Exception {
        List<UserWithPhoneList> nameAndPhone = telUserListMapper.getUser(60);
        Assert.assertNotNull(nameAndPhone);
    }

    @Test
    public void getPhone() throws Exception {
    }

    @Test
    @Transactional
    public void testSort() {
        PageHelper.startPage(1, 10, "ltrim(first_name+last_name) asc");
        List<TelUserList> telUserLists = telUserListMapper.selectAll();
        PageInfo pageInfo = new PageInfo(telUserLists);
        Assert.assertNotNull(pageInfo);
    }

    @Test
    @Transactional
    public void getUserTest() {
        List<UserWithPhoneList> result = telUserListMapper.getUser(60);
        Assert.assertEquals(0, result.size());
    }

}