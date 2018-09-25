package com.csxx.bo.unifiedLogin;

import lombok.Data;

import java.util.Date;

@Data
public class ValidResponseData {

    private Integer userid;

    private Integer idenum;

    private String username;

    private String nickname;

    private String prefix;

    private String mobile;

    private String email;

    private Integer status;

    private Integer is_super;

    private Integer not_active;

    private Integer login_count;

    private String last_login_ip;

    private Date last_login_time;

    private String avatar;

    private String source;

    private String mail_name;

    private String mail_pwd;

    private String voip_pwd;

    private Date voip_time;

    private String token;

    private String ticket;

}
