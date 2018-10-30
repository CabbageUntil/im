package com.csxx.service.unifiedLogin;

import com.csxx.bo.unifiedLogin.ValidResponseEntity;


public interface UnifiedLoginService {

    ValidResponseEntity unifiedLogin(String jwtToken);

    void initUser(String owner);



}
