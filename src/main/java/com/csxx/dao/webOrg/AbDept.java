package com.csxx.dao.webOrg;

import java.util.Date;
import java.util.List;

public class AbDept {
    private Integer deptId;

    private Integer orgId;

    private String deptName;

    private Integer parentId;
    /**
     * time:2018年7月28日14:04:07
     * author:peng
     */

    public Date deptRemoveDatetime;
    public Date deptCreateDatetime;
    public  String  deptStatus;
    //2018年7月31日11:19:30
    public   List<AbDept> abDept;
    public   String  parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    //2018年7月31日11:29:20


    public List<AbDept> getAbDept() {
        return abDept;
    }

    public void setAbDept(List<AbDept> abDept) {
        this.abDept = abDept;
    }
    public Date getDeptRemoveDatetime() {
        return deptRemoveDatetime;
    }

    public void setDeptRemoveDatetime(Date deptRemoveDatetime) {
        this.deptRemoveDatetime = deptRemoveDatetime;
    }

    public Date getDeptCreateDatetime() {
        return deptCreateDatetime;
    }

    public void setDeptCreateDatetime(Date deptCreateDatetime) {
        this.deptCreateDatetime = deptCreateDatetime;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public AbDept(Integer deptId, Integer orgId, String deptName, Integer parentId) {
        this.deptId = deptId;
        this.orgId = orgId;
        this.deptName = deptName;
        this.parentId = parentId;
    }

    public AbDept() {
        super();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}