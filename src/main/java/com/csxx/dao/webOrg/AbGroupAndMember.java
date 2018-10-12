package com.csxx.dao.webOrg;

import lombok.Data;

import java.util.Date;

/**
 * @name群和成员关系实体类
 * @author:peng
 */
@Data
public class AbGroupAndMember {
    private String groupMemberId;//群成员编号
    private String groupId;//群编号
    private Byte memberStatus;//成员状态信息
    private Byte memberRole;//成员角色
}
