package com.csxx.utils;

import lombok.Data;

@Data
public class PhoneChildList {
    private String value;
    private String label;

    @Override
    public String toString() {
        return "PhoneChildList{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
