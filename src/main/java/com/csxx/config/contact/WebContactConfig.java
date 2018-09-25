package com.csxx.config.contact;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "web-contact")
@Component
public class WebContactConfig {

    /**
     * 网站域名
     */
    public String domain;

    /**
     * TOKEN过期时间
     */
    public Integer expireTime;

    /**
     * 登录地址
     */
    public String loginPage;

    /**
     * 登出地址
     */
    public String logoutPage;

    /**
     * 通讯录网站表格翻页URL
     */
    public String webContactPage;

    /**
     * 通讯录网站回收站表格翻页URL
     */
    public String webContactDelPage;

}
