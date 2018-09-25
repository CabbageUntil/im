package com.csxx.controller.webOrg;

import com.alibaba.fastjson.JSONObject;
import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebCompanyService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/org")
@Slf4j
public class WebCompanyController {

    private final WebCompanyService webCompanyService;

    @Autowired
    public WebCompanyController(WebCompanyService webCompanyService) {
        this.webCompanyService = webCompanyService;
    }

    /**
     * 获取用户session信息，并转换数据类型
     * @param session
     * @return
     */
    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }

    @GetMapping("/joinComCount")
    public ResponseEntity joinComCount(HttpSession session) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            String user = userInfo.getUsername();
            return ResponseEntityUtil.success(webCompanyService.getJoinComCount(user));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 查询加入公司的数量
     * @param session
     * @param sort
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    @GetMapping("/joinComList")
    public ResponseEntity joinComList(HttpSession session,
                                      String sort,
                                      Integer page,
                                      @RequestParam("per_page") Integer perPage,
                                      @RequestParam(value = "filter", required = false) String filter) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            String user = userInfo.getUsername();
            return ResponseEntityUtil.success(webCompanyService.getJoinComList(user, sort, page, perPage, filter));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询未加入的公司的名称
     * @param session
     * @param sort
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    @GetMapping("/otherComList")
    public ResponseEntity otherComList(HttpSession session,
                                       @RequestParam("sort")String sort,
                                       @RequestParam("page") Integer page,
                                       @RequestParam("per_page") Integer perPage,
                                       @RequestParam(value = "filter", required = false) String filter) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            String user = userInfo.getUsername();
            return ResponseEntityUtil.success(webCompanyService.getOtherComList(user, sort, page, perPage, filter));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询创建公司的数量
     * @param session
     * @return
     */
    @GetMapping("/ownComCount")
    public ResponseEntity ownComCount(HttpSession session) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            String user = userInfo.getUsername();
            return ResponseEntityUtil.success(webCompanyService.getOwnComCount(user));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询已经拥有的公司
     * @param session
     * @param sort
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    @GetMapping("/ownComList")
    public ResponseEntity ownComList(HttpSession session,
                                     String sort,
                                     Integer page,
                                     @RequestParam("per_page") Integer perPage,
                                     @RequestParam(value = "filter", required = false) String filter) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            String user = userInfo.getUsername();
            return ResponseEntityUtil.success(webCompanyService.getOwnComList(user, sort, page, perPage, filter));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 创建公司
     * 参数：
     * 创建人的信息
     * 创建公司的信息
     * @param session
     * @param
     * @return
     */
    @PostMapping("/createCom")
    @ResponseBody
    public ResponseEntity createCom(HttpSession session, @RequestParam("archiveForm") String  ar, @RequestParam("orgForm") String  org ) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            webCompanyService.createCom(userInfo.getUsername(),ar,org);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 加入公司
     * @param session
     * @param form 包含用户信息和公司编号
     * @return
     */
    @PostMapping("/joinCom")
    public ResponseEntity joinCom(HttpSession session,@RequestBody JoinComForm form) {
        UserInfo userInfo = getUserInfo(session);
        if(userInfo != null){
            webCompanyService.joinCom(userInfo.getUsername(),form);
            return ResponseEntityUtil.success();
        }
        return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
    }
    /**
     * 公司名称是否存在
     * @param value
     * @param type
     * @return
     */
    @GetMapping("/validateRepeatCom")
    public ResponseEntity validateRepeatCom(@RequestParam("value") String value,
                                            @RequestParam("type") String type
                                             ){
        if(!webCompanyService.validRepeat(value,type)){
            return ResponseEntityUtil.error(ResultEnum.ORG_DUP_ERR);
        }else {
            return ResponseEntityUtil.success();
        }
    }

}
