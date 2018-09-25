package com.csxx.dao.app;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dian_userapp")
@Data
public class DianUserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "appname")
    private String appName;

//    private Integer appId;

    @Column(name = "username")
    private String userName;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "app_id", referencedColumnName = "id")
    private DianAppList dianAppList;

}
