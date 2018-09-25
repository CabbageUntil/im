package com.csxx.enums.webOrg;

import com.csxx.enums.common.CodeEnum;
import lombok.Getter;

@Getter
public enum RoleEnum implements CodeEnum {
    CREATOR(0, "CREATOR"),
    ADMIN(1, "ADMIN"),
    MEMBER(2, "MEMBER"),
    ;

    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
