package com.csxx.dao.contact;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "region_tab")
@Data
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String en;

    private String cn;

    public Region() {
    }

}
