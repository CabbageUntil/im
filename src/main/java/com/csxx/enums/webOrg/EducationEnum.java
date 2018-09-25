package com.csxx.enums.webOrg;

import com.csxx.enums.common.CodeEnum;
import lombok.Getter;

@Getter
public enum EducationEnum implements CodeEnum {
    ILLITERACY(0, "ILLITERACY"),
    PRIMARY_SCH(1, "PRIMARY_SCH"),
    JUNIOR_SCH(2, "JUNIOR_SCH"),
    SENIOR_SCH(3, "SENIOR_SCH"),
    JUNIOR_COL(4, "JUNIOR_COL"),
    UNDERGRADUATE(5, "UNDERGRADUATE"),
    MASTER(6, "MASTER"),
    DOCTOR(7, "DOCTOR"),
    ;

    private Integer code;
    private String message;

    EducationEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
