package com.csxx.bo.contact;

import lombok.Data;

@Data
public class WebContactCatAndAddress {

    private Integer userId;

    private Integer detailId;

    private String category;

    private String label;

    private String content;

    private String remark;

    private Integer addressId;

    private String countryCode;

    private String country;

    private String state;

    private String city;

    private String admin;

    private String locality;

    private String street;

    private String postcode;

}
