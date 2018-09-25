package com.csxx.dto.webOrg.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JoinComForm implements Serializable {

    private static final long serialVersionUID = -6155846975177455979L;

    private String memberName;

    private Byte education;

    private Boolean maritalStatus;

    private Byte sex;

    private String  birthday;

    private String  idCardExp;

    private String emergencyUser;

    private String emergencyMobile;

    private Integer orgId;

    private List<LabelValue> telList;

    private List<LabelValue>  phoneList;

    private List<LabelValue> emailList;

    private List<LabelValue> addressList;

    private List<LabelValue> otherList;

    public JoinComForm() {
    }
}
