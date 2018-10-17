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
}
