package com.csxx.repository;

import com.csxx.bo.contact.WebContactLine;
import com.csxx.dao.contact.TelOwnerList;
import com.csxx.dao.contact.TelUserList;
import com.csxx.mapper.contact.TelUserListMapper;
import com.csxx.Application;
import com.csxx.repository.contact.TelOwnerListRepository;
import com.csxx.repository.contact.TelUserListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelUserListRepositoryTest.class)
@Import(Application.class)
public class TelUserListRepositoryTest {

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;
    @Autowired
    private TelUserListRepository telUserListRepository;

    @Autowired
    private TelUserListMapper telUserListMapper;

    @Test
    @Transactional
    public void findByTelOwnerList() throws Exception {

        int size;

        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName("13147593333");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(2, 15, sort);
        Page<TelUserList> telUserListPage = telUserListRepository.findByTelOwnerList(telOwnerList, pageRequest);
        telUserListPage.getTotalElements();
        telUserListPage.getTotalPages();
        telUserListPage.getNumber();
        telUserListPage.getNumberOfElements();
        telUserListPage.getSize();
        telUserListPage.getPageable().getPageSize();
        telUserListPage.getPageable().getPageNumber();
        telUserListPage.getPageable().getOffset();
        List<WebContactLine> webContactLineList = new LinkedList<>();

        /*for (TelUserList telUserList : telOwnerList.getTelUserLists()) {
            WebContactLine webContactLineDTO = new WebContactLine();

            // 获取通讯人的基本信息
            webContactLineDTO.setId(telUserList.getId());
            webContactLineDTO.setName(telUserList.getFirstName() + telUserList.getLastName());
            webContactLineDTO.setBirthday(telUserList.getBirthday());
            webContactLineDTO.setCompany_department(
                    StrUtil.CovertNullToEmptyStr(telUserList.getOrganizationName()) + " " +
                            StrUtil.CovertNullToEmptyStr(telUserList.getDepartmentName()));
            webContactLineDTO.setJob(telUserList.getJobTitle());
            webContactLineDTO.setNote(telUserList.getNote());

            List<TelDetailList> telDetailLists = new ArrayList<>(telUserList.getTelDetailLists());

            // 获取通讯人邮箱
            Set<TelDetailList> emailSet = telDetailLists.stream().filter(t -> t.getCategory().equals("email")).collect(Collectors.toSet());
            size = emailSet.size();
            if (size > 0) {
                String mail = emailSet.iterator().next().getContent();
                if (size > 1) {
                    mail = mail + " ...";
                }
                webContactLineDTO.setMail(mail);
            }

            // 获取通讯人手机
            Set<TelDetailList> mobileSet = telDetailLists.stream().filter(t -> t.getCategory().equals("mobile")).collect(Collectors.toSet());
            size = mobileSet.size();
            if (size > 0) {
                String phone = mobileSet.iterator().next().getContent();
                if (size > 1) {
                    phone = phone + " ...";
                }
                webContactLineDTO.setPhone(phone);
            }

            // 获取通讯人网址
            Set<TelDetailList> urlAddressSet = telDetailLists.stream().filter(t -> t.getCategory().equals("urlAddress")).collect(Collectors.toSet());
            size = urlAddressSet.size();
            if (size > 0) {
                String url = mobileSet.iterator().next().getContent();
                if (size > 1) {
                    url = url + " ...";
                }
                webContactLineDTO.setUrl(url);
            }

            // 获取通讯人地址
            Set<TelDetailList> addressSet = telDetailLists.stream().filter(t -> t.getCategory().equals("address")).collect(Collectors.toSet());
            size = addressSet.size();
            if (size > 0) {
                TelAddressListMapper telAddressList = addressSet.iterator().next().getTelAddressList();
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
                webContactLineDTO.setAddress(address);
            }

            webContactLineList.add(webContactLineDTO);
        }*/

        Assert.assertNotEquals(0, webContactLineList.size());

    }

    @Test
    @Transactional
    public void findByIdIn() throws Throwable {
        String owner = "13147593333";
        String filter = "%1%";
        Integer ownerId = telUserListMapper.findByUserName(owner);
        List<Integer> userList = telUserListMapper.findUserIdByName(ownerId, filter);
        List<Integer> detailList = telUserListMapper.findUserIdByEmailOrMobile(ownerId, filter);
        userList.addAll(detailList);
        List<Integer> userIdList = userList.stream().distinct().collect(Collectors.toList());

        Sort sort = new Sort(Sort.Direction.ASC, "first_name + last_name");
        PageRequest pageRequest = PageRequest.of(2, 15, sort);

        Page<TelUserList> result = telUserListRepository.findByIdIn(userIdList, pageRequest);
        result.getSort().iterator().next().getDirection().name();
        result.getSort().iterator().next().getProperty();
        Assert.assertNotEquals(0, result.getSize());
    }

    @Test
    @Transactional
    public void updateStatusByIdIn() {
    }

    @Test
    @Transactional
    public void deleteByUserIdIn() {
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName("xxx");
        List<TelUserList> telUserLists = telUserListRepository.findAllById(Arrays.asList(1148, 1150));
        telOwnerList.getTelUserLists().remove(telUserLists);
        telUserListRepository.deleteAll(telUserLists);
    }

    @Test
    @Transactional
    public void findRelatedIdByUserIdIn() {
        List<Object[]> objects = telUserListRepository.findRelatedIdByUserIdIn(Arrays.asList(1148, 1150));
        for (Object[] object : objects) {
            System.out.println(object[0]);
            System.out.println(object[1]);
            System.out.println(object[2]);
        }
        Assert.assertEquals(2, objects.size());
    }

    @Test
    public void findByTelOwnerListAndStatus() throws Exception {
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName("xxx");
        List<TelUserList> telUserList = telUserListRepository.findByTelOwnerListAndStatus(telOwnerList, false);
        Assert.assertNotEquals(0, telUserList.size());
    }

    @Test
//    @Transactional
    public void deleteById() {
        telUserListRepository.deleteById(3511);
    }

}