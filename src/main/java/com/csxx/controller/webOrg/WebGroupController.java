package com.csxx.controller.webOrg;

import com.csxx.dao.webOrg.AbGroup;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.service.webOrg.WebGroupService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.utils.groupList;
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
     * @return
     */
    @PostMapping("/createGroup")
    public ResponseEntity createGroup(HttpSession session,
                                      @RequestParam("groupName") String groupName
    ){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            webGroupService.createGroup(userInfo,groupName);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 加入分组功能
     * @param session
     * @return
     * addGroupMember
     */
    @PostMapping("/joinGroup")
    public ResponseEntity joinGroup(HttpSession session,
                                    @RequestParam("groupId") String groupId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            webGroupService.joinGroup(userInfo,groupId);
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
            if(userInfo.getGroupMemberId().equals(groupMemberId)){
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
    /**
     * 退群即群组成员主动离开群组
     * 参数说明：groupMemberId 群成员编号  groupId 群编号
     * @param session
     * @return
     */
    @PostMapping("/leaveGroup")
    public ResponseEntity leaveGroup(HttpSession session,@RequestParam("groupId") String groupId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            if(webGroupService.leaveGroup(userInfo,groupId)>0){
                return  ResponseEntityUtil.success();
            }else{
                return  ResponseEntityUtil.error(1144,"网络异常操作失败！");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 群组组长解散群组
     * 参数说明：groupId 群编号
     * @param session
     * @param groupId
     * @return
     */
    @PostMapping("/removegroup")
    public ResponseEntity removeGroup(HttpSession session,@RequestParam("groupId") String groupId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            if(webGroupService.removeGroup(userInfo,groupId)>0){
                return  ResponseEntityUtil.success();
            }else{
                return  ResponseEntityUtil.error(1144,"网络异常操作失败！");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * get join group number
     * @param session
     * @return
     */
    @PostMapping("/getJionGroupCount")
    public ResponseEntity getJionGroupCount(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if(userInfo != null){
            Integer count = webGroupService.getJionGroupCount(userInfo);
            groupList groupList = new groupList();
            groupList.setCount(count);
            return ResponseEntityUtil.success(groupList);
        }else{
           return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * get Not join group 0f number
     * @param session
     * @return
     */
    @PostMapping("/getCreateGroupCount")
    public ResponseEntity getCreateGroupCount(HttpSession session){
        UserInfo userInfo = getUserInfo(session);
        if(userInfo != null){
            Integer count  = webGroupService.getCreateGroupCount(userInfo);
            groupList groupList = new groupList();
            groupList.setCount(count);
            return ResponseEntityUtil.success(groupList);
        }else{
           return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 添加群成员功能
     * @param session
     * @return
     */
    @PostMapping("/addGroupMember")
    public ResponseEntity addGroupMember(HttpSession session,
                                    @RequestParam("name") String name, @RequestParam("mobile") String mobile ){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            System.out.println("添加群成员信息！");
            if(name!=null && mobile!=null&&name!="null"&&mobile!="null"&&name!=""&&mobile!=""){
                webGroupService.addGroupMember(userInfo,name,mobile);
                return ResponseEntityUtil.success();
            } else {
                return ResponseEntityUtil.error(101010,"姓名和手机号不能为空");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 通过手机号判断该成员是否已经加入群
     * @param session
     * @param mobile
     * @return
     */
    @PostMapping("/checkGroupMember")
    public ResponseEntity checkGroupMember(HttpSession session,@RequestParam("mobile") String mobile){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            if(!webGroupService.checkGroupMember(userInfo,mobile)){
                return ResponseEntityUtil.success();
            }else{
                return ResponseEntityUtil.error(122,"用户已经添加群组");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
}
