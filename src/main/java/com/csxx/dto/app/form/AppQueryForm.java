package com.csxx.dto.app.form;

import lombok.Data;

@Data
public class AppQueryForm {

    private String userName;

    private String appName;

    private String linkStr;

    private Boolean isOther;

    private String appType;

    private String key;

}
