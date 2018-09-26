package com.csxx.vo.webOrg;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Archive {

    private Integer id;

    private String name;

    private String department;

    private String post;

    private String education;

    private Boolean maritalStatus;

    private Integer sex;

    private Date birthday;

    private Date idCardExp;

    private String nativePlace;

    private Integer animal;

    private String emergencyUser;

    private String emergencyMobile;

    private Date applicateDate;

    private Date entryDate;

    private Date leaveDate;

    private Integer memberStatus;

    private List<IdLabelContent> tel;

    private List<IdLabelContent> email;

    private List<IdLabelContent> address;

    private List<IdLabelContent> other;

    /**
     * 2018-9-26 14:51:28
     */
    private String idCard;
    private String blankId;
    private String blankName;

}
