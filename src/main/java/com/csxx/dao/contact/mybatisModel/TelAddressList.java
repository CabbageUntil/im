package com.csxx.dao.contact.mybatisModel;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class TelAddressList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String iso_country_code;

    private String city;

    private String country;

    private String label;

    private String postal_code;

    private String state;

    private String street;

    private String sub_administrative_area;

    private String sub_locality;

}
