package com.csxx.service.contact;

import com.csxx.converter.contact.AutoMergePOList2DataList;
import com.csxx.converter.contact.ThreeAttrToTelDetailSet;
import com.csxx.dao.contact.TelOwnerList;
import com.csxx.dao.contact.TelUserList;
import com.csxx.bo.contact.AutoMergePO;
import com.csxx.dao.contact.mybatisModel.TelDetailList;
import com.csxx.mapper.contact.TelDetailListMapper;
import com.csxx.mapper.contact.TelUserListMapper;
import com.csxx.bo.contact.Data;
import com.csxx.dto.contact.Owner;
import com.csxx.bo.contact.User;
import com.csxx.enums.common.ResultEnum;
import com.csxx.exception.BizException;
import com.csxx.repository.contact.TelOwnerListRepository;
import com.csxx.repository.contact.TelUserListRepository;
import com.csxx.vo.contacts.UserWithPhoneList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactService {

    @Value("${code}")
    private String code;
    @Value("${web.upload-path}")
    private String location;

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;
    @Autowired
    private TelUserListRepository telUserListRepository;

    @Autowired
    private TelUserListMapper telUserListMapper;
    @Autowired
    private TelDetailListMapper telDetailListMapper;

    /**
     * 上传通讯录
     * @param owner
     * @throws Exception
     */
    @Transactional
    public void upload(Owner owner) {
        // 删除不在回收站的用户
        List<Integer> ids = telUserListMapper.findNormalUserIdByOwnerId(owner.getUserName());
        if (ids != null && ids.size() > 0) {
            telUserListRepository.deleteByIdIn(ids);
            telUserListRepository.flush();
        }
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(owner.getUserName());
        if (telOwnerList == null) {
            telOwnerList = new TelOwnerList();
            telOwnerList.setUserName(owner.getUserName());
            telOwnerList.setSyncTime(new Date());
        } else {
            telOwnerList.setSyncTime(new Date());
        }
        for (Data data : owner.getDataList()) {
            User user = data.getUser();
            TelUserList telUserList = new TelUserList(
                    StringUtils.defaultString(user.getFirstName()),
                    StringUtils.defaultString(user.getLastName()),
                    user.getMidName(),
                    user.getBirthday(),
                    user.getDepartmentName(),
                    user.getJobTitle(),
                    user.getOrganizationName(),
                    user.getNote(),
                    false,
                    null);
            if (data.getEmails() != null && data.getEmails().size() > 0) {
                // 转化emails为TelDetailList
                telUserList.addBatchDetail(ThreeAttrToTelDetailSet.convertToSet(data.getEmails()));
            }
            if (data.getMobiles() != null && data.getMobiles().size() > 0) {
                // 转换mobiles为TelDetailList
                telUserList.addBatchDetail(ThreeAttrToTelDetailSet.convertToSet(data.getMobiles()));
            }
            if (data.getUrlAddresses() != null && data.getUrlAddresses().size() > 0) {
                // 转换urlAddresses为TelDetailList
                telUserList.addBatchDetail(ThreeAttrToTelDetailSet.convertToSet(data.getUrlAddresses()));
            }
            if (data.getAddresses() != null && data.getAddresses().size() > 0) {
                // 转换addresses为TelAddressList
                telUserList.addBatchDetail(ThreeAttrToTelDetailSet.convertToAddrSet(data.getAddresses()));
            }
            // 将该TelUserList对象加入TelOwnerList中的telUserLists
            telOwnerList.addTelUser(telUserList);
        }
        // 保存
        telOwnerListRepository.save(telOwnerList);
    }

    /**
     * 下载通讯录
     * @param userName
     * @return
     */
    @Transactional(readOnly = true)
    public List<Data> download(String userName) {
        List<AutoMergePO> resultSet = telUserListMapper.getManualMergeByOwnerAndIds(userName, null);
        List<Data> dataList = AutoMergePOList2DataList.convert(resultSet);

        return dataList;
    }

    /**
     * 返回通讯人并各自带有电话号码
     * @param owner
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserWithPhoneList> getUserWithPhoneList(String owner) {
        List<UserWithPhoneList> userList;
        List<UserWithPhoneList> outList = new ArrayList<>();
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(owner);
        if (telOwnerList != null) {
            Map<String, UserWithPhoneList> userMap = new HashMap<>();
            userList = telUserListMapper.getUser(telOwnerList.getId());
            for (UserWithPhoneList userWithPhoneList : userList) {
                if (!userMap.containsKey(userWithPhoneList.getRawName())) {
                    userWithPhoneList.setPhoneList(userWithPhoneList.getPhoneList().stream().distinct().collect(Collectors.toList()));
                    userMap.put(userWithPhoneList.getRawName(), userWithPhoneList);
                } else {
                    List<String> anotherPhoneList = new ArrayList<>();
                    anotherPhoneList.addAll(userWithPhoneList.getPhoneList());
                    userMap.get(userWithPhoneList.getRawName()).getPhoneList().addAll(anotherPhoneList);
                    userWithPhoneList.setPhoneList(userWithPhoneList.getPhoneList().stream().distinct().collect(Collectors.toList()));
                }
            }

            for (Map.Entry<String, UserWithPhoneList> entry : userMap.entrySet()) {
                outList.add(entry.getValue());
            }
        } else {
            throw new BizException(ResultEnum.USER_NOT_EXIST);
        }

        return outList;
    }

    /**
     * 按通讯人取电话
     * @param owner
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> getPhoneByName(String owner, String name) {
        List<String> phoneList;
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(owner);
        if (telOwnerList == null) {
            throw new BizException(ResultEnum.USER_NOT_EXIST);
        } else {
            phoneList = telUserListRepository.getPhoneByName(telOwnerList.getId(), name);
        }

        return phoneList;
    }

    /**
     * 获取用户同步时间
     * @param userName
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSyncTime(String userName) {
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(userName);
        if (telOwnerList == null) {
            throw new BizException(ResultEnum.NO_RECORD);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("lastSyncTime", telOwnerList.getSyncTime());
        Integer contactNumber = telUserListRepository.sumByOwnerId(telOwnerList.getId());
        map.put("contactNumber", contactNumber);
        return map;
    }

    @Transactional
    public void addMobile(String owner, String user, String mobile) {
        TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(owner);
        if (telOwnerList == null) {
            throw new BizException(ResultEnum.USER_NOT_EXIST);
        }
        // 检查通讯人是否存在
        Integer id = telUserListMapper.findTopOneByOwnerAndName(owner, user);
        // 如果通讯人不存在则新增一个
        if (id == null) {
            com.csxx.dao.contact.mybatisModel.TelUserList telUserList = new com.csxx.dao.contact.mybatisModel.TelUserList();
            telUserList.setOwnerId(telOwnerList.getId());
            telUserList.setFirstName("");
            telUserList.setLastName(StringUtils.defaultString(user));
            telUserList.setStatus(false);
            telUserListMapper.insertOne(telUserList);
            id = telUserList.getId();
        }
        // 新增号码
        TelDetailList telDetailList = new TelDetailList();
        telDetailList.setUser_id(id);
        telDetailList.setCategory("mobile");
        telDetailList.setLabel("");
        try {
            telDetailList.setContent(URLEncoder.encode(mobile, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("【增加手机】编码失败，用户：{}，手机号：{}！", user, mobile);
            throw new BizException(ResultEnum.URLENCODE_FAIL);
        }
        telDetailListMapper.insertOne(telDetailList);
    }

}
