package com.csxx.service.webOrg;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;

import javax.servlet.http.HttpSession;

public interface WebUserService {

    /**
     * 根据统一登录接口返回的令牌进行登录操作
     * @param token 外部令牌
     * @param session 会话
     * @return
     */
    ResponseEntity login(String token, HttpSession session);

    /**
     * 登录公司
     * @param session 会话
     * @param userInfo 用户信息
     * @param orgId 公司ID
     * @return
     */
    ResponseEntity loginCom(HttpSession session, UserInfo userInfo, Integer orgId);

    /**
     * 登录分组
     * 查询该成员拥有的角色、群组名称、姓名
     * @param session
     * @param userInfo
     * @param groupId
     * @return
     */
    ResponseEntity loginGroup(HttpSession session, UserInfo userInfo, String groupId);

    void logoutCom(HttpSession session, UserInfo userInfo);

}
