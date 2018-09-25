package com.csxx.config.unifiedLogin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "common")
@Component
public class UnifiedLoginConfig {


    /**
     * 统一登录接口地址
     */
    public String tokenValidPage;

    /**
     * 统一登录令牌
     */
    public String requestTokenSign;

}
