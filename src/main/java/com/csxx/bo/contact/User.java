package com.csxx.bo.contact;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String firstName;

    private String midName;

    private String lastName;

    private Date birthday;

    private String departmentName;

    private String jobTitle;

    private String organizationName;

    private String note;

    public User() {
    }

    public User(String firstName, String midName, String lastName, Date birthday, String departmentName, String jobTitle, String organizationName, String note) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.departmentName = departmentName;
        this.jobTitle = jobTitle;
        this.organizationName = organizationName;
        this.note = note;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
