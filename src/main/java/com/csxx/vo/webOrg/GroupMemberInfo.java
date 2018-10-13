package com.csxx.vo.webOrg;
/**
 * 登录时获取成员信息（包含：群名称，姓名，角色）
 */

import lombok.Data;

@Data
public class GroupMemberInfo {
    private String groupId;//群组编号
    private String memberName;//成员姓名
    private String groupName;//群名称
    private String memberRole;//群的角色

    @Override
    public String toString() {
        return "GroupMemberInfo{" +
                "memberName='" + memberName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", memberRole='" + memberRole + '\'' +
                '}';
    }
}
