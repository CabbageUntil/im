package com.csxx.service.webOrg;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;

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
}
