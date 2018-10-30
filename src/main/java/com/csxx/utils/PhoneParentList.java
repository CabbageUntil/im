package com.csxx.utils;


import lombok.Data;

import java.util.List;

@Data
public class PhoneParentList {
    private String value;
    private String label;
    private List<PhoneChildList> children;

    @Override
    public String toString() {
        return "PhoneParentList{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
