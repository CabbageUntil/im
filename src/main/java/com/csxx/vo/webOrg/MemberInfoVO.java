package com.csxx.vo.webOrg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoVO {

    /**
     * 工号
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门名
     */
    private String department;

    /**
     * 职位名
     */
    private String post;

    /**
     * 学历代码
     */
    private Integer education;

    /**
     * 婚否代码
     */
    private Integer maritalStatus;

    /**
     * 性别代码
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 身份证过期日
     */
    private Date idCardExp;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 生肖代码
     */
    private Integer animal;

    /**
     * 紧急联系人
     */
    private String emergencyUser;

    /**
     * 紧急联系电话
     */
    private String emergencyMobile;

    /**
     * 申请加入日期
     */
    private Date applicateDate;

    /**
     * 正式加入日期
     */
    private Date entryDate;

    /**
     * 离职日期
     */
    private Date leaveDate;

    /**
     * 员工状态代码
     */
    private Integer memberStatus;

    /**
     * 2018-9-26 14:51:28
     */
    private String idCard;
    private String blankId;
    private String blankName;
    /**
     * 员工的onenet_owner账号
     */
    private String onenetOwner;

}
