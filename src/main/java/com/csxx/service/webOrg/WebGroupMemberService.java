package com.csxx.service.webOrg;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.webOrg.UserInfo;

public interface WebGroupMemberService {
    /**
     * 查询未审核通过的群组成员
     * @param userInfo
     * @return
     */
    ResponseEntity selectNotViewGroupList(UserInfo userInfo);
}
