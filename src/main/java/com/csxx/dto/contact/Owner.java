package com.csxx.dto.contact;

import com.csxx.bo.contact.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Owner {

    private String userName;

    private String key;

    @JsonProperty(value = "data")
    private List<Data> dataList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty(value = "data")
    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

}
