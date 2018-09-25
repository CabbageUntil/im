package com.csxx.vo.common;

import com.csxx.enums.common.ResultEnum;

import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseEntity(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getTransMsg();
        this.data = null;
    }

    public ResponseEntity(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getTransMsg();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
