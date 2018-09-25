package com.csxx.controller.common;

import com.csxx.enums.common.ResultEnum;
import com.csxx.utils.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
public class ShiroController {

    /**
     * 认证失败
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(path = "/unauthorized/{message}")
    public Object unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), message);
    }

}
