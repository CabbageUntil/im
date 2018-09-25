package com.csxx.dao.webOrg;

public class AbRole {
    private Integer roleId;

    private String roleName;

    public AbRole(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public AbRole() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}