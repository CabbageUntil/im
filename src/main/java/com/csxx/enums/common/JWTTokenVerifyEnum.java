package com.csxx.enums.common;

public enum JWTTokenVerifyEnum {
    VALID(0, "VALID_TOKEN"),
    ALGORITHM_MISMATCH(1, "ALGORITHM_MISMATCH"),
    INVALID_SIGNATURE(2, "INVALID_SIGNATURE"),
    TOKEN_EXPIRED(3, "TOKEN_EXPIRED"),
    INVALID_CLAIM(4, "INVALID_CLAIM"),
    ;

    private Integer code;

    private String message;

    JWTTokenVerifyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
