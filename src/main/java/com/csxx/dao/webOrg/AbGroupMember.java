package com.csxx.dao.webOrg;

import lombok.Data;

import java.util.Date;

/**
 * @name群组成员实体类
 * @author:peng
 */
@Data
public class AbGroupMember {
    private String groupMemberId;//编号
    private String name ;//群名称
    private String  mebile;//手机号
    private String applicateRemark;//申请备注

    private Date applicateDate;//申请日期
    private Date verifyDate;//通过日期

}
