package com.csxx.exception;

import com.csxx.enums.common.ResultEnum;

public class BizException extends RuntimeException {

    private Integer code;

    public BizException(ResultEnum resultEnum) {
        super(resultEnum.getTransMsg());
        this.code = resultEnum.getCode();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
