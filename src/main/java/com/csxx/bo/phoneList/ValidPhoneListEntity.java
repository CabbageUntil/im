package com.csxx.bo.phoneList;

/**
 * @author penghaijie
 * @time 2018年10月26日17:09:52
 */

import lombok.Data;
import java.util.List;

@Data
public class ValidPhoneListEntity {
    private Integer code;
    private String msg;
    private List<ValidPhoneListData> data;

    @Override
    public String toString() {
        return "ValidPhoneListEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
