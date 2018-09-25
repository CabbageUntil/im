package com.csxx.enums.app;

import lombok.Getter;

@Getter
public enum AppOpEnum {
    ADD(0, "Add"),
    DEL(1, "Delete")
    ;

    private Integer code;

    private String operation;

    AppOpEnum(Integer code, String operation) {
        this.code = code;
        this.operation = operation;
    }
}
