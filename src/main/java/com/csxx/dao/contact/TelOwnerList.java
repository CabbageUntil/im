package com.csxx.dao.contact;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tel_owner_list")
public class TelOwnerList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    private Date syncTime;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, targetEntity = TelUserList.class)
    @JoinColumn(name = "owner_id")
    private Set<TelUserList> telUserLists = new HashSet<>();

    public TelOwnerList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Set<TelUserList> getTelUserLists() {
        return telUserLists;
    }

    public void setTelUserLists(Set<TelUserList> telUserLists) {
        this.telUserLists = telUserLists;
    }

    public void addTelUser(TelUserList telUserList) {
        telUserLists.add(telUserList);
    }

}
