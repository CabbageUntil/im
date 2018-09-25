package com.csxx.enums.webOrg;

import com.csxx.enums.common.CodeEnum;
import lombok.Getter;

@Getter
public enum MemberStatusEnum implements CodeEnum {
    PENDING(0, "PENDING"),
    FORMAL(1, "FORMAL"),
    LEAVE(2, "LEAVE")
    ;

    private Integer code;
    private String message;

    MemberStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
