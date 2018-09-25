package com.csxx.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class memList {
    @Getter
    @Setter
    private Integer memberId;
    @Getter
    @Setter
    private String memberName;
}
