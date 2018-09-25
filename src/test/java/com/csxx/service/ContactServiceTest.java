package com.csxx.service;

import com.csxx.bo.contact.Data;
import com.csxx.Application;
import com.csxx.service.contact.ContactService;
import com.csxx.vo.contacts.UserWithPhoneList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactServiceTest.class)
@Import(Application.class)
public class ContactServiceTest {

    @Autowired
    ContactService contactService;

    @Test
    public void upload() throws Exception {

    }

    @Test
    public void download() throws Exception {
        List<Data> dataList = contactService.download("98882289");
        Assert.assertNotEquals(0, dataList.size());
    }

    @Test
    public void getSyncTime() throws Exception {
    }

    @Test
    public void getName() {
        List<UserWithPhoneList> userList = contactService.getUserWithPhoneList("98882289");
        Assert.assertNotNull(userList);
    }

    @Test
    public void getPhoneByName() {
        List<String> phoneList = contactService.getPhoneByName("98882289", "1");
        Assert.assertNotEquals(0, phoneList.size());
    }

    @Test
//    @Transactional
    public void addUser() {
        contactService.addMobile("xxx", "newUser", "123456");
    }

}