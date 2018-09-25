package com.csxx.dao.contact;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tel_detail_list")
@Data
public class TelDetailList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String category;

    private String label;

    private String content;

    private String remark;

    @ManyToOne(cascade = { CascadeType.MERGE/*, CascadeType.REFRESH*/ }, optional = false)
    @JoinColumn(name = "user_id")
    private TelUserList telUserList;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, /*CascadeType.REFRESH, */CascadeType.REMOVE }, fetch = FetchType.LAZY, targetEntity = TelAddressList.class)
    @JoinColumn(name = "address_id")
    private TelAddressList telAddressList;

    public TelDetailList() {
    }

    public TelDetailList(String category, String label, String content) {
        this.category = category;
        this.label = label;
        this.content = content;
    }

}
