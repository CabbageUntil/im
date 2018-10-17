package com.csxx.controller.webOrg;

import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebMemberService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/org")
@Slf4j
public class WebMemberController {

    private final WebMemberService webMemberService;

    @Autowired
    public WebMemberController(WebMemberService webMemberService) {
        this.webMemberService = webMemberService;
    }

    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }

    /**
     * 联动查询部门员工的名单(日程管理)
     * @param session
     * @param deptId 部门编号
     * @return
     */
    @GetMapping(value = "/memSelectList")
    public ResponseEntity memSelectList(HttpSession session,
                                     @RequestParam("deptId") Integer deptId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
           return webMemberService.memSelectList(userInfo,deptId);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询公司员工列表
     * @param session 会话
     * @param sort 排序
     * @param page 第几页
     * @param perPage 每页数量
     * @param filter 公司名称，及输入框中的内容
     * @param type 指定要查找的员工状态
     * @return
     */
    @GetMapping(value = "/memberList")
    public ResponseEntity memberList(HttpSession session,
                                     @RequestParam("sort") String sort,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("per_page") Integer perPage,
                                     String filter,
                                     @RequestParam("type") Integer type){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            return webMemberService.memberList(sort, page, perPage, filter, userInfo, type);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    @GetMapping(value = "/memberListDetail")
    public ResponseEntity memberListDetail(HttpSession session,
                                           @RequestParam("memberId") Integer memberId) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null && userInfo.getMemberId() != null) {
            return webMemberService.memberListDetail(userInfo, memberId);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 主要用于查询某个员工的详细信息
     * 包含详细信息，部门信息，以及基本信息
     * @param session
     * @param menberId  用户的编号
     * @return  用户信息
     */
    @RequestMapping(value = "/findDetailMember")
    public TableDTO findDetailMe(HttpSession session,
                                    @RequestParam("id") Integer menberId
                                 ){
        return webMemberService.findDetailMe(menberId);
     }

    @GetMapping(value = "/fetchArchiveGeneral")
    public ResponseEntity fetchArchiveGeneral(HttpSession session) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return ResponseEntityUtil.success(webMemberService.fetchArchiveGeneral(userInfo.getUsername()));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 根据公司编号查询员工的信息
     * @param session
     * @param orgId 公司编号
     * @return
     */
    @GetMapping(value = "/fetchSpecArchive")
    public ResponseEntity fetchSpecArchive(HttpSession session,
                                           Integer orgId) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return ResponseEntityUtil.success(webMemberService.fetchSpecArchive(userInfo.getUsername(), orgId));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 根据公司编号查询员工的信息
     * @param session
     * @param memberId 公司编号
     * @return
     */
    @GetMapping(value = "/finMemberByMemberId")
    public ResponseEntity finMemberByMemberId(HttpSession session,
                                           @RequestParam("memberId") Integer memberId ) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return ResponseEntityUtil.success(webMemberService.findMemberByMemberId(userInfo, memberId));
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 更具用户的编号移除移除用户及是改变员工的状态：
     * member_status :1在职 0 离职
     * leave_date  离职时间
     * @param session
     * @param memberId
     * @return
     */
    @DeleteMapping(value = "/removeMember")
    public ResponseEntity removeMember(HttpSession session,
                                       @RequestParam("id") Integer memberId){
        UserInfo userInfo = getUserInfo(session);
        Integer memberId1 = userInfo.getMemberId();
        if(memberId == memberId1){
            return ResponseEntityUtil.error(10001, "不能移除当前用户账号！");
        }else{
            int i = webMemberService.updateMemberStatus(memberId);
            if(i>0){
                return ResponseEntityUtil.success(ResultEnum.SUCCESS);
            }else{
                return ResponseEntityUtil.error(ResultEnum.Fail);
            }
        }
    }

    /**
     * 修改用户信息
     * @param session
     * @param form
     * @return
     */
    @PostMapping("/saveMember")
    public ResponseEntity saveMember(HttpSession session,@RequestBody JoinComForm form) {
        UserInfo userInfo = getUserInfo(session);
        if(userInfo != null){
            webMemberService.saveMember(userInfo.getUsername(),form);
            return ResponseEntityUtil.success();
        }
        return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
    }
}
