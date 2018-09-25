package com.csxx.repository;

import com.csxx.bo.contact.WebContactLine;
import com.csxx.dao.contact.TelAddressList;
import com.csxx.dao.contact.TelDetailList;
import com.csxx.dao.contact.TelOwnerList;
import com.csxx.dao.contact.TelUserList;
import com.csxx.repository.contact.TelOwnerListRepository;
import com.csxx.service.contact.ContactService;
import com.csxx.utils.StrUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableAutoConfiguration()
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelOwnerListRepositoryTest.class)
@ComponentScan(basePackages = {"com.csxx.*"})
@EnableJpaRepositories(basePackages = {"com.csxx.repository"})
@EntityScan("com.csxx.dao")
public class TelOwnerListRepositoryTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;

    @Test
    @Transactional
    public void findByUserName() throws Exception {

        int size;

        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName("13147593333");
        List<WebContactLine> webContactLineList = new LinkedList<>();

        for (TelUserList telUserList : telOwnerList.getTelUserLists()) {
            WebContactLine webContactLine = new WebContactLine();

            // 获取通讯人的基本信息
            webContactLine.setId(telUserList.getId());
            webContactLine.setName(telUserList.getFirstName() + telUserList.getLastName());
            webContactLine.setBirthday(telUserList.getBirthday());
            webContactLine.setCompany_department(
                StrUtil.CovertNullToEmptyStr(telUserList.getOrganizationName()) + " " +
                StrUtil.CovertNullToEmptyStr(telUserList.getDepartmentName()));
            webContactLine.setJob(telUserList.getJobTitle());
            webContactLine.setNote(telUserList.getNote());

            List<TelDetailList> telDetailLists = new ArrayList<>(telUserList.getTelDetailLists());

            // 获取通讯人邮箱
            Set<TelDetailList> emailSet = telDetailLists.stream().filter(t -> t.getCategory().equals("email")).collect(Collectors.toSet());
            size = emailSet.size();
            if (size > 0) {
                String mail = emailSet.iterator().next().getContent();
                if (size > 1) {
                    mail = mail + " ...";
                }
                webContactLine.setMail(mail);
            }

            // 获取通讯人手机
            Set<TelDetailList> mobileSet = telDetailLists.stream().filter(t -> t.getCategory().equals("mobile")).collect(Collectors.toSet());
            size = mobileSet.size();
            if (size > 0) {
                String phone = mobileSet.iterator().next().getContent();
                if (size > 1) {
                    phone = phone + " ...";
                }
                webContactLine.setPhone(phone);
            }

            // 获取通讯人网址
            Set<TelDetailList> urlAddressSet = telDetailLists.stream().filter(t -> t.getCategory().equals("urlAddress")).collect(Collectors.toSet());
            size = urlAddressSet.size();
            if (size > 0) {
                String url = mobileSet.iterator().next().getContent();
                if (size > 1) {
                    url = url + " ...";
                }
                webContactLine.setUrl(url);
            }

            // 获取通讯人地址
            Set<TelDetailList> addressSet = telDetailLists.stream().filter(t -> t.getCategory().equals("address")).collect(Collectors.toSet());
            size = addressSet.size();
            if (size > 0) {
                TelAddressList telAddressList = addressSet.iterator().next().getTelAddressList();
                String address = StrUtil.CovertNullToEmptyStr(telAddressList.getCountry()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getState()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getCity()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getSubAdministrativeArea()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getSubLocality()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getStreet()) +
                    StrUtil.CovertNullToEmptyStr(telAddressList.getPostalCode());
                if (size > 1) {
                    address += "...";
                }
                webContactLine.setAddress(address);
            }

            webContactLineList.add(webContactLine);
        }

        Assert.assertNotEquals(0, webContactLineList.size());

    }

}