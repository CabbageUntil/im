package com.csxx.aspect;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.dto.contact.Owner;
import com.csxx.enums.common.ResultEnum;
import com.csxx.dto.app.form.AppForm;
import com.csxx.dto.app.form.AppQueryForm;
import com.csxx.repository.contact.TelOwnerListRepository;
import com.csxx.utils.MD5Util;
import com.csxx.utils.ResponseEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpAspect {

    @Value("${code}")
    private String code;

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;

    @Pointcut("(execution(public * com.csxx.controller.contact.ContactsController.*(..))) && " +
            "!(execution(public * com.csxx.controller.contact.ContactsController.getSyncTime(..)))")
    public void contactsRequest() {
    }

    @Pointcut("(execution(public * com.csxx.controller.app.AppController.*(..)))")
    public void appRequest() {}

    @Around("contactsRequest()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Boolean isValid;
        Boolean isExists;
        String userName;
        String key;

        if (methodName.equals("upload") || methodName.equals("download")) {
            Owner owner = (Owner) joinPoint.getArgs()[0];
            userName = owner.getUserName();
            key = owner.getKey();
        } else {
            key = (String)joinPoint.getArgs()[0];
            userName = (String)joinPoint.getArgs()[1];
        }

        // 校验key是否合法
        isValid = MD5Util.validate(userName, key, code);

        Object obj;

        if (isValid) {
            if (joinPoint.getSignature().getName().equals("upload")) {
                obj = joinPoint.proceed();
            } else if (joinPoint.getSignature().getName().equals("download")) {
                isExists = telOwnerListRepository.existsByUserName(userName);
                if (!isExists) {
                    obj = new ResponseEntity(ResultEnum.NO_RECORD);
                } else {
                    obj = joinPoint.proceed();
                }
            } else {
                obj = joinPoint.proceed();
            }
        } else {
            obj = new ResponseEntity(ResultEnum.AUTH_FAILED);
        }

        return obj;
    }

    @Around("appRequest()")
    public Object appValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object;

        String methodName = joinPoint.getSignature().getName();

        if (methodName.equals("query")) {
            AppQueryForm appQueryForm = (AppQueryForm)joinPoint.getArgs()[0];
            String userName = appQueryForm.getUserName();
            String key = appQueryForm.getKey();
            if (!(StringUtils.isEmpty(userName) && StringUtils.isEmpty(key))) {
                if (!MD5Util.validate(userName, key, code)) {
                    return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(),
                            ResultEnum.AUTH_FAILED.getTransMsg());
                }
            }
            object = joinPoint.proceed();
        } else {
            AppForm appForm = (AppForm)(joinPoint.getArgs()[0]);
            if (!MD5Util.validate(appForm.getUserName(), appForm.getKey(), code)) {
                return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(),
                        ResultEnum.AUTH_FAILED.getTransMsg());
            }
            object = joinPoint.proceed();
        }

        return object;
    }

}
