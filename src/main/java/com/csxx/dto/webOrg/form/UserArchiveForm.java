package com.csxx.dto.webOrg.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserArchiveForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String memberName;

    private Byte education;

    private Boolean maritalStatus;

    private Byte sex;

    private Byte animal;

    private Date birthday;

    private Date idCardExp;

    private String emergencyUser;


    private String emergencyMobile;

    private List<LabelValue> telList;

    private List<LabelValue>  phoneList;

    private List<LabelValue> emailList;

    private List<LabelValue> addressList;

    private List<LabelValue> otherList;

    private String idCard;

    private String blankId;

    private String blankName;

    private String nativePlace;

    @Override
    public String toString() {
        return "UserArchiveForm{" +
                "memberName='" + memberName + '\'' +
                ", education=" + education +
                ", maritalStatus=" + maritalStatus +
                ", sex=" + sex +
                ", animal=" + animal +
                ", birthday=" + birthday +
                ", idCardExp=" + idCardExp +
                ", emergencyUser='" + emergencyUser + '\'' +
                ", emergencyMobile='" + emergencyMobile + '\'' +
                ", telList=" + telList +
                ", phoneList=" + phoneList +
                ", emailList=" + emailList +
                ", addressList=" + addressList +
                ", otherList=" + otherList +
                ", idCard='" + idCard + '\'' +
                ", blankId='" + blankId + '\'' +
                ", blankName='" + blankName + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                '}';
    }
}
