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
     * 审核 、删除
     * @param abGroupAndMember
     * @return
     */
    int verifyGroupMember(AbGroupAndMember abGroupAndMember);

    /**
     * 根据成员编号和群组编号删除成员信息
     * @param memberId
     * @param groupId
     * @return
     */
    int deleteGroupMember(@Param(value="mobile") String memberId,@Param(value="groupId") String groupId);

    int deleteByMemberIdAndGroupId(@Param(value="memberId") String memberId,@Param(value="groupId") String groupId);

}