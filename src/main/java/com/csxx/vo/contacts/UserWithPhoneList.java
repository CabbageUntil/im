package com.csxx.vo.contacts;

import lombok.Data;

import java.util.List;

@Data
public class UserWithPhoneList {

    private String prettyName;

    private String rawName;

    private List<String> phoneList;

}
