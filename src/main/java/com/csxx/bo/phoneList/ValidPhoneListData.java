package com.csxx.bo.phoneList;
/**
 * @author penghaijie
 * @time 2018年10月26日17:07:46
 */

import lombok.Data;

import java.util.Arrays;

@Data
public class ValidPhoneListData {
    private String prettyName;
    private String rawName;
    private String[] phoneList;

    @Override
    public String toString() {
        return "ValidPhoneListData{" +
                "prettyName='" + prettyName + '\'' +
                ", rawName='" + rawName + '\'' +
                ", phoneList=" + Arrays.toString(phoneList) +
                '}';
    }
}
