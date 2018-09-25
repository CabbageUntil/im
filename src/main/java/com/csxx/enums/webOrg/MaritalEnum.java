package com.csxx.enums.webOrg;

import com.csxx.enums.common.CodeEnum;
import lombok.Getter;

@Getter
public enum MaritalEnum implements CodeEnum {
    MARRIED(0, "MARRIED"),
    SINGLE(1, "SINGLE"),
    ;

    private Integer code;
    private String message;

    MaritalEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
