package com.csxx.controller.webOrg;

import com.csxx.dao.webOrg.AbGroup;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebGroupService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 群组管理
 */
@RestController
@RequestMapping(value="/org")
@Slf4j
public class WebGroupController {

    @Autowired
    private WebGroupService webGroupService;

    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }

    /**
     * 创建群组
     * @param session
     * @param groupName
     * @param userName
     * @return
     */
    @PostMapping("/createGroup")
    public ResponseEntity createGroup(HttpSession session,
                                      @RequestParam("groupName") String groupName,
                                      @RequestParam("userName") String userName
    ){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            webGroupService.createGroup(userInfo,groupName,userName);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 加入分组功能
     * @param session
     * @return
     */
    @PostMapping("/joinGroup")
    public ResponseEntity joinGroup(HttpSession session,
                                    @RequestParam("groupId") String groupId,
                                    @RequestParam("userName") String userName){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            webGroupService.joinGroup(userInfo,groupId,userName);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 查询群组信息
     * @param session
     * @return
     */
    @GetMapping("/selectgrouptList")
    public ResponseEntity groupList(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return webGroupService.selectGroupList(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 查询加入分组的信息
     * @param session
     * @return
     */
    @PostMapping("/joinGroupList")
    public ResponseEntity joinGroupList(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return webGroupService.joinGroupList(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询加入创建分组的信息
     * @param session
     * @return
     */
    @PostMapping("/createGroupList")
    public ResponseEntity createGroupList(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return webGroupService.createGroupList(userInfo);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 审核群成员信息
     * @param session
     * @param groupMemberId
     * @return
     */
    @PostMapping("/verifyGroupMember")
    public ResponseEntity verifyGroupMember(HttpSession session,
                                            @RequestParam("groupMemberId") String groupMemberId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
           if(webGroupService.verifyGroupMember(userInfo,groupMemberId)>0){
              return  ResponseEntityUtil.success();
           }else{
             return  ResponseEntityUtil.error(1144,"网络异常操作失败！");
           }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 移除群组成员
     * @param session
     * @param groupMemberId
     * @return
     */
    @PostMapping("/deleteGroupMember")
    public ResponseEntity deleteGroupMember(HttpSession session,
                                            @RequestParam("groupMemberId") String groupMemberId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            if(!userInfo.getUsername().equals(groupMemberId)){
                return  ResponseEntityUtil.error(1144,"不能删除群组管理员！");
            }else{
                if(webGroupService.deleteGroupMember(userInfo,groupMemberId)>0){
                    return  ResponseEntityUtil.success();
                }else{
                    return  ResponseEntityUtil.error(1144,"网络异常操作失败！");
                }
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
}
