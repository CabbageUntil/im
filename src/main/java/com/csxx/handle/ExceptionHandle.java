package com.csxx.handle;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.exception.BizException;
import com.csxx.utils.ResponseEntityUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @ControllerAdvice
 * */
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity handle(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException)e;
            return ResponseEntityUtil.error(bizException.getCode(), bizException.getMessage());
        }
        return ResponseEntityUtil.error(ResultEnum.INTERNAL_ERROR.getCode(), e.toString());
    }

}
