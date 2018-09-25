package com.csxx.enums.common;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

public enum ResultEnum {
    SUCCESS(0, "SUCCESS"),
    Fail(10011, "Fail"),
    INTERNAL_ERROR(-1, "INTERNAL_ERROR"),
    AUTH_FAILED(-2, "AUTH_FAILED"),
    NO_RECORD(-3, "NO_RECORD"),

    IMG_EMPTY(-4, "IMG_EMPTY"),
    IMG_FMT_ERR(-5, "IMG_FMT_ERR"),
    IMG_SAVE_FAILED(-6, "IMG_SAVE_FAILED"),

    REQ_FMT_ERR(-7, "REQ_FMT_ERR"),
    DUP_APP_ERR(-8, "DUP_APP_ERR"),
    APP_NOT_EXIST(-9, "APP_NOT_EXIST"),

    USER_NOT_EXIST(-10, "USER_NOT_EXIST"),
    ILLEGAL_OPERATION(-11, "ILLEGAL_OPERATION"),
    URLENCODE_FAIL(-12, "URLENCODE_FAIL"),

    ORG_DUP_ERR(-13, "ORG_DUP_ERR"),

    LOGIN_FAIL(-1000, "LOGIN_FAIL"),
    NEED_LOGIN(-1001, "NEED_LOGIN"),
    INVALID_TOKEN(-1002, "INVALID_TOKEN"),
    TOKEN_EXPIRED(-1003, "TOKEN_EXPIRED"),
    NO_PERMISSION(-1004, "NO_PERMISSION"),
    ORG_LOGIN_FAIL(-2000, "ORG_LOGIN_FAIL")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() { return message; }

    public String getTransMsg() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("i18n/messages");
        resourceBundleMessageSource.setDefaultEncoding("utf-8");
        resourceBundleMessageSource.setCacheSeconds(60 * 60);
        return resourceBundleMessageSource.getMessage("resultEnum.".concat(getMessage()), null, LocaleContextHolder.getLocale());
    }

}
