package com.csxx.enums.webOrg;

import com.csxx.enums.common.CodeEnum;
import lombok.Getter;

@Getter
public enum SexEnum implements CodeEnum {
    MALE(0, "MALE"),
    FEMALE(1, "FEMALE"),
    ;

    private Integer code;
    private String message;

    SexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
