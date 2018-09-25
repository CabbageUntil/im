package com.csxx.dao.contact;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tel_user_list")
@Data
public class TelUserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String midName;

    private Date birthday;

    private String departmentName;

    private String jobTitle;

    private String organizationName;

    private String note;

    private Boolean status;

    private Date delTime;

    @ManyToOne(cascade = { CascadeType.MERGE/*, CascadeType.REFRESH*/ }, optional = false)
    @JoinColumn(name = "owner_id")
    private TelOwnerList telOwnerList;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, /*CascadeType.REFRESH, */CascadeType.REMOVE }, fetch = FetchType.EAGER, targetEntity = TelDetailList.class)
    @JoinColumn(name = "user_id")
    private Set<TelDetailList> telDetailLists = new HashSet<>();

    public TelUserList() {
    }

    public TelUserList(String firstName, String lastName, String midName, Date birthday, String departmentName, String jobTitle, String organizationName, String note, Boolean status, Date delTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.birthday = birthday;
        this.departmentName = departmentName;
        this.jobTitle = jobTitle;
        this.organizationName = organizationName;
        this.note = note;
        this.status = status;
        this.delTime = delTime;
    }

    public void addDetail(TelDetailList telDetailList) {
        telDetailLists.add(telDetailList);
    }

    public void addBatchDetail(Set<TelDetailList> telDetailLists) {
        this.telDetailLists.addAll(telDetailLists);
    }

}
