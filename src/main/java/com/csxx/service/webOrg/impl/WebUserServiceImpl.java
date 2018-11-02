package com.csxx.service.webOrg.impl;

import com.alibaba.fastjson.JSONObject;
import com.csxx.bo.phoneList.ValidPhoneListData;
import com.csxx.bo.phoneList.ValidPhoneListEntity;
import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.MemberStatusEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.mapper.webOrg.AbGroupMapper;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.service.webOrg.WebUserService;
import com.csxx.utils.*;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.GroupMemberInfo;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    private RestTemplate restTemplate;
    private final UnifiedLoginService unifiedLoginService;
    private final AbMemberMapper abMemberMapper;
    @Autowired
    private AbGroupMapper abGroupMapper;
    @Autowired
    public WebUserServiceImpl(UnifiedLoginService unifiedLoginService, AbMemberMapper abMemberMapper) {
        this.unifiedLoginService = unifiedLoginService;
        this.abMemberMapper = abMemberMapper;
    }

    public ResponseEntity login(String token, HttpSession session) {
        ValidResponseEntity responseEntity = unifiedLoginService.unifiedLogin(token);
        if (responseEntity.getIsOk().equals(0)) {
            return ResponseEntityUtil.error(ResultEnum.LOGIN_FAIL.getCode(), responseEntity.getMessage());
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(responseEntity.getData().getMobile());
            String s = responseEntity.getData().getMobile();
            userInfo.setSecret(MD5Util.getMD5(s+"D8FIs5mz9spSTF7R"));
            userInfo.setRole("visitor");
            userInfo.setToken(responseEntity.getData().getToken());
            userInfo.setName(responseEntity.getData().getNickname());
            userInfo.setAvatar(responseEntity.getData().getAvatar());
            session.setAttribute(UserInfoEnum.USERINFO, userInfo);
            return ResponseEntityUtil.success(userInfo);
        }
       /* UserInfo userInfo = new UserInfo();
        userInfo.setUsername("13246554606");
        userInfo.setRole("visitor");
        userInfo.setName("13246554606");
        userInfo.setAvatar("");
        session.setAttribute(UserInfoEnum.USERINFO, userInfo);
        return ResponseEntityUtil.success();*/
    }

    /**
     * 根据公司编号，登录账号，员工的状态查询是否存在这样的员工
     * 员工状态为 1
     * @param session 会话
     * @param userInfo 用户信息
     * @param orgId 公司ID
     * @return
     */
    @Override
    public ResponseEntity loginCom(HttpSession session, UserInfo userInfo, Integer orgId) {
        UserInfo orgUserInfo = abMemberMapper.selectByOrgIdAndUsernameAndStatus(orgId, userInfo.getUsername(), MemberStatusEnum.FORMAL.getCode());
        if (orgUserInfo != null) {

            userInfo.setOrgId(orgUserInfo.getOrgId());
            userInfo.setMemberId(orgUserInfo.getMemberId());
            userInfo.setOrgName(orgUserInfo.getOrgName());
            userInfo.setMemberName(orgUserInfo.getMemberName());
            userInfo.setRole(orgUserInfo.getRole());


            session.setAttribute(UserInfoEnum.USERINFO, userInfo);
            return ResponseEntityUtil.success();
        } else {
            log.error("登录公司失败，userInfo={}，orgId={}", userInfo, orgId);
            return ResponseEntityUtil.error(ResultEnum.ORG_LOGIN_FAIL.getCode(), "登录公司失败");
        }
    }

    /**
     * 登录分组
     * 查询该成员拥有的角色、群组名称、姓名
     *
     * @param session
     * @param userInfo
     * @param groupId
     * @return
     */
    @Override
    public ResponseEntity loginGroup(HttpSession session, UserInfo userInfo, String groupId) {
        GroupMemberInfo groupMemberInfo = abGroupMapper.selectGroupMemberByMebileAndGroupId(groupId,userInfo.getUsername());
        if (groupMemberInfo != null) {
            userInfo.setGroupMemberId(groupMemberInfo.getGroupMemberId());
            userInfo.setOrgName(groupMemberInfo.getGroupName());
            userInfo.setMemberName(groupMemberInfo.getMemberName());
            String role = groupMemberInfo.getMemberRole().equals("2")? "groupLeader":"groupMember";
            userInfo.setRole(role);
            userInfo.setGroupId(groupMemberInfo.getGroupId());
            session.setAttribute(UserInfoEnum.USERINFO, userInfo);
            return ResponseEntityUtil.success();
        } else {
            log.error("登录群组失败，userInfo={}，orgId={}", userInfo, groupId);
            return ResponseEntityUtil.error(ResultEnum.ORG_LOGIN_FAIL.getCode(), "登录群组失败");
        }
    }

    @Override
    public void logoutCom(HttpSession session, UserInfo userInfo) {
        userInfo.setOrgName(null);
        userInfo.setOrgId(null);
        userInfo.setMemberId(null);
        userInfo.setMemberName(null);
        userInfo.setRole("visitor");
        session.setAttribute(UserInfoEnum.USERINFO, userInfo);
    }
    /**
     * 调用统一接口访问电话本数据
     *
     */
    @Override
    public List<PhoneParentList> findTellList(UserInfo userInfo){
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        //获取用户注册账号
        String owner = userInfo.getUsername();
        postParameters.add("owner",owner);
        postParameters.add("secret", MD5Util.getMD5(owner+"D8FIs5mz9spSTF7R"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);

        String data = restTemplate.postForObject("http://onenet.com.tw:8088/im/contacts/api/get_user",
                request, String.class);
        //json对象转成
        ValidPhoneListEntity aa = JSONObject.parseObject(data,ValidPhoneListEntity.class);
        if(aa!=null&&!"".equals(aa)){
            List<ValidPhoneListData> d = aa.getData();
            if(d!=null&&!"".equals(d)){
                List<PhoneParentList> pLists = new ArrayList<>();
                for (ValidPhoneListData val : d){
                    PhoneParentList phoneParentList =  new PhoneParentList();
                    String name = val.getPrettyName();
                    String[] phone = val.getPhoneList();
                    List<PhoneChildList> cList = new ArrayList<>();
                    for(int i = 0; i <phone.length; i++){
                        PhoneChildList phoneChildList = new PhoneChildList();
                        phoneChildList.setLabel(phone[i]);
                        phoneChildList.setValue(phone[i]);
                        cList.add(phoneChildList);
                    }
                    phoneParentList.setChildren(cList);
                    phoneParentList.setLabel(name);
                    phoneParentList.setValue(name);
                    pLists.add(phoneParentList);
                }
                return  pLists;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 根据号码查询通讯录
     * @param userInfo
     * @return
     */
    @Override
    public List<TelList> getTelList(UserInfo userInfo) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        //获取用户注册账号
        String owner = userInfo.getUsername();
        System.out.println("手机号-------------------------------------------------------->"+owner);
        postParameters.add("owner",owner);
        postParameters.add("secret", MD5Util.getMD5(owner+"D8FIs5mz9spSTF7R"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);

        String data = restTemplate.postForObject("http://onenet.com.tw:8088/im/contacts/api/get_user",
                request, String.class);
        //json对象转成
        ValidPhoneListEntity aa = JSONObject.parseObject(data,ValidPhoneListEntity.class);
        if(aa!=null&&!"".equals(aa)){
            List<ValidPhoneListData> d = aa.getData();
            //接收对象
            if(d!=null&&!"".equals(d)){
                List<TelList> telLists = new ArrayList<>();
                for (ValidPhoneListData val : d){
                    String name = val.getPrettyName();
                    String[] name1 = name.split(" ");
                    StringBuffer sb = new StringBuffer();
                    sb.append(name1[1]).append(name1[0]);
                    String userName = sb.toString();
                    String[] phone = val.getPhoneList();
                    //遍历电话号码
                    for(int i = 0;i<phone.length;i++){
                        TelList telList  =  new TelList();
                        telList.setValue(phone[i]);
                        telList.setName(userName);
                        telLists.add(telList);
                    }
                }
                return telLists;
            } else {
                return null;
            }
        }
        return null;
    }
    /**
     * 根据姓名查询通讯录
     * @param userInfo
     * @return
     */
    @Override
    public List<NameList> getNameList(UserInfo userInfo) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        //获取用户注册账号
        String owner = userInfo.getUsername();
        System.out.println("手机号-------------------------------------------------------->"+owner);
        postParameters.add("owner",owner);
        postParameters.add("secret", MD5Util.getMD5(owner+"D8FIs5mz9spSTF7R"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);

        String data = restTemplate.postForObject("http://onenet.com.tw:8088/im/contacts/api/get_user",
                request, String.class);
        //json对象转成
        ValidPhoneListEntity aa = JSONObject.parseObject(data,ValidPhoneListEntity.class);
        if(aa!=null&&!"".equals(aa)){
            List<ValidPhoneListData> d = aa.getData();
            //接收对象
            if (d != null && !"".equals(d)) {
                List<NameList> nameLists = new ArrayList<>();
                for (ValidPhoneListData val : d){
                    NameList nameList = new NameList();
                    String name = val.getPrettyName();
                    String[] name1 = name.split(" ");
                    StringBuffer sb = new StringBuffer();
                    sb.append(name1[1]).append(name1[0]);
                    String userName = sb.toString();
                    nameList.setPhone(val.getPhoneList());
                    nameList.setValue(userName);
                    nameLists.add(nameList);
                }
                return nameLists;
            }else {
                return null;
            }

        }
        return null;
    }
    /**
     * 远程调用接口添加电话星系
     * @param userInfo
     * @return
     */
    @Override
    public ValidPhoneListEntity addPhoneList(UserInfo userInfo,String name,String mobile) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        //获取用户注册账号
        String owner = userInfo.getUsername();
        System.out.println("手机号-------------------------------------------------------->"+owner);
        postParameters.add("owner",owner);
        postParameters.add("name",name);
        postParameters.add("mobile",mobile);
        postParameters.add("secret", MD5Util.getMD5(owner+"D8FIs5mz9spSTF7R"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);

        String data = restTemplate.postForObject("http://onenet.com.tw:8088/im/contacts/api/add_mobile",
                request, String.class);
        //json对象转成
        ValidPhoneListEntity aa = JSONObject.parseObject(data,ValidPhoneListEntity.class);
        return aa;
    }
}
