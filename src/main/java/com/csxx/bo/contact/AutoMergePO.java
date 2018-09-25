package com.csxx.bo.contact;

import lombok.Data;

import java.util.Date;

@Data
public class AutoMergePO {

    private Integer userId;

    private String lastName;

    private String firstName;

    private Date birthday;

    private String job;

    private String company;

    private String department;

    private String note;

    /**
     * 格式：标签;内容;备注,标签;内容;备注
     */
    private String mobiles;

    /**
     * 格式：标签;内容;备注,标签;内容;备注
     */
    private String emails;

    /**
     * 格式：标签;内容;备注,标签;内容;备注
     */
    private String urls;

    /**
     * 格式：标签;内容(各地址字段用+连接);备注,标签;内容(各地址字段用+连接);备注
     */
    private String addresses;

}
