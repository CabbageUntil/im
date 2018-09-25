package com.csxx.controller.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ModelAndView("common/403");
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ModelAndView("common/404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ModelAndView("common/500");
            }
        }
        return new ModelAndView("common/error");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
