package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbDept;
import com.csxx.dao.webOrg.AbGroupMember;
import com.csxx.utils.groupList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbMemberGroupMapper {
    /**
     * 根据成员编号移除对应的信息
     * @param groupMemberId
     * @return
     */
    int deleteByGroupMemberId(String groupMemberId);

    /**
     * 添加群成员信息
     * @param abGroupMember
     * @return
     */
    int insert(AbGroupMember abGroupMember);

    /**
     * 选择性添加成员信息
     * @param abGroupMember
     * @return
     */
    int insertSelective(AbGroupMember abGroupMember);

    /**
     * 更新成员信息
     * @param abGroupMember
     * @return
     */
    int updateByPrimaryKeySelective(AbGroupMember abGroupMember);

    /**
     * 审核成员
     * @param abGroupMember
     * @return
     */
    int updateByPrimaryKey(AbGroupMember abGroupMember);

    /**
     *
     * @param abGroupMember
     * @return
     */
    List<AbGroupMember> findGroupMember(AbGroupMember abGroupMember) ;

    /**
     *查询审核通过的群员的信息
     * @param groupId
     * @return
     */
    List<AbGroupMember> selectNotViewGroupList(@Param(value = "aa") String groupId);

    /**
     * 获取成员的编号信息
     * @param mebile
     * @return
     */
     AbGroupMember getMemberId(String mebile);
}