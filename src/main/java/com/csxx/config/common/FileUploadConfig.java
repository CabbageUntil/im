package com.csxx.config.common;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig extends WebMvcConfigurationSupport {

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("2MB");
        factory.setMaxRequestSize("10MB");

        return factory.createMultipartConfig();

    }

}
