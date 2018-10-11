package com.csxx.controller.webOrg;

import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.service.webOrg.WebDeptmentServic;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/org")
@Slf4j
public class WebDeptController {
    private final WebDeptmentServic  webDeptmentServic;
    @Autowired
    public WebDeptController(WebDeptmentServic webDeptmentServic) {

        this.webDeptmentServic = webDeptmentServic;
    }

    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }

    /**
     * 查询部门的相关信息
     * @param session
     * @param page
     * @param PerPage
     * @param sort
     * @param filter
     * @return
     */
    @GetMapping("/apartmentList")
    public ResponseEntity allDeptMent(HttpSession session,
                                @RequestParam("page") Integer page,
                                @RequestParam("per_page") Integer PerPage,
                                @RequestParam("sort") String sort,
                                @RequestParam("type") Integer type,
                                String filter
                                ){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            return webDeptmentServic.apartmentList(sort,page,PerPage,userInfo,filter,type);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    @GetMapping("/selectdetpList")
    public ResponseEntity allDeptMent(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            return webDeptmentServic.selectList(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 解散部门
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/removeDeptMent")
    public ResponseEntity  removeDeptMent(
            HttpSession session,
            @RequestParam("dept_id")  Integer deptId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            webDeptmentServic.RemoveDeptMent(deptId,userInfo);
            return  ResponseEntityUtil.error(ResultEnum.SUCCESS);
        } else {
            return null;
        }
    }

    /**
     * 部门名称和隶属部门的编号
     * 隶属部门账号可有可无 默认值：0
     * @param deptName
     * @param parentId
     * @return
     */
    @GetMapping("/insertDeptMent")
    public ResponseEntity insertDeptMent(
            HttpSession session,
            @RequestParam("dept_name") String deptName,
            @RequestParam(value = "parent_id",defaultValue ="0",required = false) String  parentId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            if(webDeptmentServic.addDeptMent(deptName,Integer.parseInt(parentId),userInfo)>0){
                return ResponseEntityUtil.success(ResultEnum.SUCCESS);
            }else{
                return  ResponseEntityUtil.error(00021,"新增部门失败！");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
}
