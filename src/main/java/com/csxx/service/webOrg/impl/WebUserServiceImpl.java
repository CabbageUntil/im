package com.csxx.service.webOrg.impl;

import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.MemberStatusEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.service.webOrg.WebUserService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class WebUserServiceImpl implements WebUserService {

    private final UnifiedLoginService unifiedLoginService;
    private final AbMemberMapper abMemberMapper;

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
            userInfo.setRole("visitor");
            userInfo.setToken(responseEntity.getData().getToken());
            System.out.println("登录用户token-------------------------------->>>>>>>>>>>>>>>>>>"+ responseEntity.getData().getToken());
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

    @Override
    public void logoutCom(HttpSession session, UserInfo userInfo) {
        userInfo.setOrgName(null);
        userInfo.setOrgId(null);
        userInfo.setMemberId(null);
        userInfo.setMemberName(null);
        userInfo.setRole("visitor");
        session.setAttribute(UserInfoEnum.USERINFO, userInfo);
    }
}
