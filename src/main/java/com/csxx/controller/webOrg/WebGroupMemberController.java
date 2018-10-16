package com.csxx.controller.webOrg;

import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebGroupMemberService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/org")
@Slf4j
public class WebGroupMemberController {

    @Autowired
    private WebGroupMemberService webGroupMemberService;

    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }
    /**
     * 查询待审核的群成员的信息
     * @param session
     * @return
     */
    @PostMapping("/selectNotViewGroupList")
    public ResponseEntity selectNotViewGroupList(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getGroupId() != null) {
            return webGroupMemberService.selectNotViewGroupList(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询审核通过的群成员的信息
     * @param session
     * @return
     */
    @PostMapping("/selectViewGroupList")
    @ResponseBody
    public ResponseEntity selectViewGroupList(HttpSession session,
                                              @RequestParam(value="name", required = true) String  name,
                                              @RequestParam(value="page", required = true) int  page,
                                              @RequestParam(value="per_page", required = true) int  PerPage
                                              ){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getGroupId() != null) {
            return webGroupMemberService.selectViewGroupList(userInfo,page,PerPage,name);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

}
