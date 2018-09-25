package com.csxx.dao.app;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dian_applist")
@Data
public class DianAppList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "appname")
    private String appName;

    private String name;

    private String picLink;

    private Integer Status;

    @OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "dianAppList", targetEntity = DianUserApp.class)
    private Set<DianUserApp> dianUserAppSet;

}
