package com.csxx.dto.contact;

import com.csxx.bo.contact.WebContactAddress;
import com.csxx.bo.contact.WebContactThreeAttr;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WebContactDetailDTO implements Serializable {

    private static final long serialVersionUID = -1526770057321156045L;

    private Integer id;

    private String avatar;

    private String firstName;

    private String lastName;

    private Date birthday;

    private String company;

    private String department;

    private String job;

    private String note;

    private List<WebContactThreeAttr<String>> phoneList;

    private List<WebContactThreeAttr<String>> emailList;

    private List<WebContactThreeAttr<WebContactAddress>> addressList;

    private List<WebContactThreeAttr<String>> urlList;

}
