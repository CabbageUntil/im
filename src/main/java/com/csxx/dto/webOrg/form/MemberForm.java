package com.csxx.dto.webOrg.form;

import lombok.Data;

import java.util.List;

@Data
public class MemberForm {

    private String memberName;

    private Byte education;

    private Boolean maritalStatus;

    private Byte sex;

    private String  birthday;

    private String  idCardExp;

    private String emergencyUser;

    private String emergencyMobile;

    private String orgId;

    private List<LabelValue> telList;

    private List<LabelValue> emailList;

    private List<LabelValue> phoneList;

    private List<LabelValue> addressList;

    private List<LabelValue> otherList;

}
