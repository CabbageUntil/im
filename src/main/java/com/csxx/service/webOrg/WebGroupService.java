package com.csxx.service.webOrg;

import com.csxx.dao.webOrg.AbGroup;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;

import java.util.List;

public interface WebGroupService {
    /**
     * 创建群组信息
     * @param userInfo
     * @param groupName
     * @param userName
     * @return
     */
    void createGroup(UserInfo userInfo,String groupName,String userName);

    /**
     * 查询加入的群组的信息
     * @param userInfo
     * @return
     */
    ResponseEntity selectGroupList(UserInfo userInfo);

    /**
     * 加入分组的功能
     * @param userInfo
     * @param groupId
     */
    void  joinGroup(UserInfo userInfo,String groupId,String userName);

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

    int verifyGroupMember(UserInfo userInfo,String groupMemberId);
}
