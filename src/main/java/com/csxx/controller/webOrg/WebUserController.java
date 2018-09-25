package com.csxx.controller.webOrg;

import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebCompanyService;
import com.csxx.service.webOrg.WebUserService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/loginCom")
    public ResponseEntity loginCom(HttpSession session,
                                   Integer orgId) {
        UserInfo userInfo = (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            return webUserService.loginCom(session, userInfo, orgId);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN.getCode(), "需要登录");
        }
    }

    @GetMapping("getUserInfo")
    public ResponseEntity getUserInfo(HttpSession session) {
        UserInfo userInfo = (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null) {
            return ResponseEntityUtil.success(userInfo);
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
        UserInfo userInfo = (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
        if (userInfo != null && userInfo.getMemberId() != null) {
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
        UserInfo userInfo = (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
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

}
