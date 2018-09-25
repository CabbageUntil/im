package com.csxx.dto.webOrg.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class  OrgForm {

    private String orgName;

    private String tel;

    private String email;

    private String orgAddress;

    @Override
    public String toString() {
        return "OrgForm{" +
                "orgName='" + orgName + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                '}';
    }
}
