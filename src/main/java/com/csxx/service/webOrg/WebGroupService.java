package com.csxx.service.webOrg;

import com.csxx.dao.webOrg.AbGroup;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;

import java.util.List;

public interface WebGroupService {

    /**
     * 查询加入的群组的信息
     * @param userInfo
     * @return
     */
    ResponseEntity selectGroupList(UserInfo userInfo);

    /**
     * 创建群组信息
     * @param userInfo
     * @param groupName
     * @return
     */
    void createGroup(UserInfo userInfo,String groupName);
    /**
     * 加入分组的功能
     * @param userInfo
     * @param groupId
     */
    void  joinGroup(UserInfo userInfo,String groupId);

    /**
     * 查询已经有群组信息
     * @param userInfo
     * @return
     */
    ResponseEntity joinGroupList(UserInfo userInfo);

    /**
     * 查询创建群组信息
     * @param userInfo
     * @return
     */
    ResponseEntity createGroupList(UserInfo userInfo);

    /**
     * 确定成员审核是否通过
     * @param userInfo
     * @param groupMemberId
     * @return
     */
    int verifyGroupMember(UserInfo userInfo,String groupMemberId);

    /**
     * 移除群组成员
     * @param userInfo
     * @param groupMemberId
     * @return
     */
    int deleteGroupMember(UserInfo userInfo,String groupMemberId);

    /**
     * 群成员退群
     * @param userInfo
     * @param groupId
     * @return
     */
    int leaveGroup(UserInfo userInfo,String groupId);

    /**
     * 解散群组
     * @param userInfo
     * @param groupId
     * @return
     */
    int removeGroup(UserInfo userInfo,String groupId);

    /**
     * 获取加入的群组的数量
     * @param userInfo
     * @return
     */
    int getJionGroupCount(UserInfo userInfo);
    /**
     * 获取创建数组数量
     * @param userInfo
     * @return
     */
    int getCreateGroupCount(UserInfo userInfo);
    /**
     * 群组添加成员 参数说明 手机号码和姓名
     * @param userInfo
     * @param mobile
     */
    void  addGroupMember(UserInfo userInfo,String name,String mobile);
    /**
     * 根据手机号判断该成员是否已经加入群组
     * @param userInfo
     * @param mobile
     */
    boolean  checkGroupMember(UserInfo userInfo,String mobile);

}
