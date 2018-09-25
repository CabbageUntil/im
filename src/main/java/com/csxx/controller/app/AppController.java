package com.csxx.controller.app;

import com.csxx.vo.app.AppDTO;
import com.csxx.enums.common.ResultEnum;
import com.csxx.exception.BizException;
import com.csxx.dto.app.form.AppForm;
import com.csxx.dto.app.form.AppQueryForm;
import com.csxx.service.app.AppService;
import com.csxx.utils.ResponseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/im/app")
public class AppController {

    @Autowired
   /* @Resource(name = "appService")*/
    private AppService appService;

    @PostMapping(value = "/api/query")
    public Object query(AppQueryForm appQueryForm) {
        List<AppDTO> appDTOList = appService.query(appQueryForm);
        return ResponseEntityUtil.success(appDTOList);
    }

    @PostMapping(value = "/api/operate")
    public Object operate(@Valid AppForm appForm,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BizException(ResultEnum.REQ_FMT_ERR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        appService.operate(appForm);
        return ResponseEntityUtil.success();
    }

}
