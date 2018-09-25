package com.csxx.dao.contact.mybatisModel;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class TelUserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer ownerId;

    private String firstName;

    private String lastName;

    private String midName;

    private Date birthday;

    private String departmentName;

    private String jobTitle;

    private String organizationName;

    private String note;

    private Boolean status;

    private Date delTime;

}
