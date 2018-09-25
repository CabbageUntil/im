package com.csxx.utils;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.enums.common.ResultEnum;

public class ResponseEntityUtil {

    public static ResponseEntity success(Object object) {
        return new ResponseEntity(ResultEnum.SUCCESS, object);
}

    public static ResponseEntity success() {
        return success(null);
    }

    public static ResponseEntity error(ResultEnum resultEnum) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(resultEnum.getCode());
        responseEntity.setMsg(resultEnum.getTransMsg());
        return responseEntity;
    }

    public static ResponseEntity error(Integer code, String msg) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        return responseEntity;
    }

}
