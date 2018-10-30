package com.csxx.controller.webOrg;

import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.service.webOrg.WebCompanyService;
import com.csxx.service.webOrg.WebUserService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/org")
public class WebUserController {

    private final WebUserService webUserService;
    private final WebCompanyService webCompanyService;

    @Autowired
    public WebUserController(WebUserService webUserService, WebCompanyService webCompanyService) {
        this.webUserService = webUserService;
        this.webCompanyService = webCompanyService;
    }

    @PostMapping("/login")
    public ResponseEntity login(String jwtToken,
                                HttpSession session) throws Exception {
        return webUserService.login(URLEncoder.encode(jwtToken, "utf-8"), session);
    }

    /**
     * 进入公司的方法
     * @param session
     * @param orgId
     * @return
     */
    @PostMapping("/loginCom")
    public ResponseEntity loginCom(HttpSession session,
                                   Integer orgId) {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            return webUserService.loginCom(session, userInfo, orgId);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }

    /**
     * 查询群成员以及登录功能
     * @param session
     * @param groupId
     * @return
     */
    @PostMapping("/loginGroup")
    public ResponseEntity loginGroup(HttpSession session, String groupId, HttpServletResponse response) throws IOException {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            return webUserService.loginGroup(session, userInfo, groupId);
        } else {
            //response.sendRedirect("https://passport.dianchat.net/pass/service_login?callback=http://110.165.41.27:8092/login");

            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }
    @GetMapping("getUserInfo")
    public ResponseEntity getUserInfo(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            return ResponseEntityUtil.success(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }
    @PostMapping("/findPhoneList")
    public ResponseEntity getPhoneList(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
           //return null;
            return ResponseEntityUtil.success(webUserService.findTellList(userInfo));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute(UserInfoEnum.USERINFO);
        return ResponseEntityUtil.success();
    }

    @PostMapping("/logoutCom")
    public ResponseEntity logoutCom(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            webUserService.logoutCom(session, userInfo);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }

    @GetMapping("/validateRepeat")
    public ResponseEntity validRepeat(HttpSession session,
                                      String type,
                                      String value) {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            if (webCompanyService.validRepeat(value, type)) {
                return ResponseEntityUtil.success();
            } else {
                return ResponseEntityUtil.error(ResultEnum.ORG_DUP_ERR.getCode(), ResultEnum.ORG_DUP_ERR.getTransMsg());
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    @PostMapping("/getServer")
    public ResponseEntity getServer(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(UserInfoEnum.USERINFO);
        String token = userInfo.getToken();
        return ResponseEntityUtil.success();
    }
}
