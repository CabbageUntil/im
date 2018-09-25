package com.csxx.bo.contact;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WebContactLine {

    /**
     * 通讯人ID
     */
    private Integer id;

    /**
     * 通讯人姓名：last_name + first_name
     */
    private String name;

    /**
     * 通讯人电话（有多个则存放第一个）
     */
    private String phone;

    /**
     * 通讯人电话列表
     */
    private List<WebContactThreeAttr<String>> phoneList;

    /**
     * 通讯人地址：国家 + 省份 + 城市 + 行政区 + 区 + 街道（如果有邮政编码，则末尾追加【，邮政编码】）（有多个则存放第一个）
     */
    private String address;

    /**
     * 上述地址格式的地址列表
     */
    private List<WebContactThreeAttr<String>> addressList;

    /**
     * 电子邮箱（有多个则存放第一个）
     */
    private String mail;

    /**
     * 电子邮箱列表
     */
    private List<WebContactThreeAttr<String>> mailList;

    /**
     * 出生日期
     * 格式：年份-月-日
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 公司部门
     * 格式：公司（如果有部门，则末尾追加【，部门】）
     */
    private String company_department;

    /**
     * 职位
     */
    private String job;

    /**
     * 网址
     */
    private String url;

    /**
     * 网址列表
     */
    private List<WebContactThreeAttr<String>> urlList;

    /**
     * 备注
     */
    private String note;

}
