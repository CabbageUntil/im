package com.csxx.dao.webOrg;

import lombok.Data;

import java.util.Date;


@Data
public class AbOrgAgenda {
    /* 2018年8月2日14:03:59  by  penghaijie*/
    private  String  memberName;
    private  String orgName;
    /**
     * 2018年9月20日14:08:57
     * 添加部门编号字段
     */
    private Integer deptId;
    private String deptName;

    private String agendaId;

    private Integer year;

    private Byte month;

    private Byte day;

    private Integer orgId;

    private Integer memberId;

    private String note1;

    private String note2;

    private String note3;

    /**
     * 添加安排时间
     * @return
     */
    private Date arrange_date;

    /**
     * 时间查询需要字段
     * @return
     */
    private Date startDate;
    private  Date endDate;


    public Integer getOrgId() {
        return orgId;
    }

    public AbOrgAgenda(String agendaId, Integer year, Byte month, Byte day, Integer orgId, Integer memberId, String note1, String note2, String note3) {
        this.agendaId = agendaId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.orgId = orgId;
        this.memberId = memberId;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
    }

    public AbOrgAgenda() {
        super();
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId == null ? null : agendaId.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getMonth() {
        return month;
    }

    public void setMonth(Byte month) {
        this.month = month;
    }

    public Byte getDay() {
        return day;
    }

    public void setDay(Byte day) {
        this.day = day;
    }

    public Integer getOrgId(Integer orgId) {
        return this.orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1 == null ? null : note1.trim();
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2 == null ? null : note2.trim();
    }

    public String getNote3() {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3 == null ? null : note3.trim();
    }


}