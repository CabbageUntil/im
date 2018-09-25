package com.csxx.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StrUtil {

    public static String CovertNullToEmptyStr(String buf) {
        if (buf == null) {
            return "";
        } else {
            return buf;
        }
    }

    public static String convertIntListToStr(List<Integer> intList, String split) {
        return String.join(split, intList.stream().map(e -> e.toString()).collect(Collectors.toList()));
    }

}
