package com.csxx.config.webOrg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "web-org")
@Component
public class WebOrgConfig {

    // 登录相关
    public String domain;
    public Integer expireTime;
    public String loginPage;
    public String logoutPage;

    // 公司表格
    public String joinComPage;
    private String ownComPage;
    private String otherComPage;
    // 部门表格
    public String  allDeptConfig;

    // 成员表格
    public String findMemberConfig;

    // 日程表格
    public String  findOrgAgendaByYearConfig;
    public String  findOrgAgendaByMonthConfig;
    public String  findOrgAgendaByDayConfig;

}
