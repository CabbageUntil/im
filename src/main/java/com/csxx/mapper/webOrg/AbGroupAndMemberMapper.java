package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbGroupAndMember;
import org.apache.ibatis.annotations.Param;

public interface AbGroupAndMemberMapper {
    /**
     * 根据成员编号解除成员和群的关系
     * @param groupId
     * @return
     */
    int deleteByGroupId(String groupId);
    /**
     * 根据群编号解除成员和群的关系
     * @param groupMemberId
     * @return
     */
    int deleteByMemberId(String groupMemberId);

    /**
     * 添加群和成员的对应关系
     * @param record
     * @return
     */
    int insert(AbGroupAndMember record);

    /**
     * 审核群组成员
     * @param groupMemberId
     * @param groupId
     * @return
     */
    int verifyGroupMember(AbGroupAndMember abGroupAndMember);

}