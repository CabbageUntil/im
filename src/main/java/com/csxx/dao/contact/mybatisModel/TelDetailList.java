package com.csxx.dao.contact.mybatisModel;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class TelDetailList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;

    private String category;

    private String label;

    private String content;

    private Integer address_id;

}
