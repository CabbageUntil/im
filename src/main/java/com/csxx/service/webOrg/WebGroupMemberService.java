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
    /**
     * 查询审核通过的群组成员
     * @param userInfo
     * @param page
     * @param perPage
     * @return
     */
    ResponseEntity selectViewGroupList(UserInfo userInfo,Integer page,Integer perPage,String name);


}
