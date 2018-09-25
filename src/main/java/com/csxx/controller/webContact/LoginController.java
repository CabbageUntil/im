package com.csxx.controller.webContact;

import com.csxx.config.contact.WebContactConfig;
import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.service.webContact.WebContactService;
import com.csxx.utils.CookieUtil;
import com.csxx.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping(value = "/im/webContacts")
public class LoginController {

    @Autowired
    private WebContactConfig webContactConfig;

    @Autowired
    private UnifiedLoginService unifiedLoginService;

    /**
     * 登录
     * @param token
     * @param response
     * @throws Exception
     */
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("jwt-token") String token,
                              HttpServletResponse response,
                              Map<String, String> map) throws Exception {
        ValidResponseEntity responseEntity = unifiedLoginService.unifiedLogin(URLEncoder.encode(token, "utf-8"));
        // 登录失败
        if (responseEntity.getIsOk().equals(0)) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getTransMsg());
            map.put("url", webContactConfig.getLogoutPage());
            return new ModelAndView("webContact/error").addAllObjects(map);
        // 登录成功
        } else {
            // 用户名
            map.put("username", responseEntity.getData().getMobile());
            // 昵称
            map.put("nickname", responseEntity.getData().getNickname());
            // 统一登录接口返回的令牌
            map.put("token", responseEntity.getData().getToken());
            response.addCookie(CookieUtil.buildCookie("token",
                    JWTUtil.createToken(map, webContactConfig.getExpireTime() * 1000),
                    webContactConfig.getExpireTime(), webContactConfig.domain, "/"));
            return new ModelAndView("redirect:" + webContactConfig.getLoginPage());
        }
    }

}
