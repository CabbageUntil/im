package com.csxx.dao.webOrg;

import java.util.Date;

public class AbOrg {
    private Integer orgId;

    private String orgName;

    private String tel;

    private String email;

    private String orgAddress;

    private Date createDatetime;

    private Date dissolvedDatetime;

    public AbOrg(Integer orgId, String orgName, String tel, String email, String orgAddress, Date createDatetime, Date dissolvedDatetime) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.tel = tel;
        this.email = email;
        this.orgAddress = orgAddress;
        this.createDatetime = createDatetime;
        this.dissolvedDatetime = dissolvedDatetime;
    }

    public AbOrg() {
        super();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getDissolvedDatetime() {
        return dissolvedDatetime;
    }

    public void setDissolvedDatetime(Date dissolvedDatetime) {
        this.dissolvedDatetime = dissolvedDatetime;
    }
}