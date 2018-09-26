package com.csxx.dao.webOrg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbMember {
    private Integer memberId;
    private Integer orgId;
    private String memberName;
    private Integer deptId;
    private Integer postId;
    private Integer roleId;
    private Byte education;
    private Boolean maritalStatus;
    private String onenetOwner;
    private Byte memberStatus;
    private Byte sex;
    private Date birthday;
    private String emergencyUser;
    private String emergencyMobile;
    private Date applicateDate;
    private Date updateDatetime;
    private Date entryDate;
    private Date leaveDate;
    private Date idCardExp;
    private String nativePlace;
    private Byte animal;


    private String idCard;

    private String blankId;

    private String blankName;

}