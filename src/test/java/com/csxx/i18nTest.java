package com.csxx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = i18nTest.class)
@Import(Application.class)
public class i18nTest {

    @Test
    public void testCode() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("i18n/messages");
        resourceBundleMessageSource.setDefaultEncoding("utf-8");
        resourceBundleMessageSource.setCacheSeconds(60 * 60);
        String msg1 = resourceBundleMessageSource.getMessage("resultEnum.PARAM_ERROR", null, LocaleContextHolder.getLocale());
        System.out.println(msg1);
    }

    @Test
    public void testValidator() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("i18n/validation");
        resourceBundleMessageSource.setDefaultEncoding("utf-8");
        resourceBundleMessageSource.setCacheSeconds(60 * 60);
        String msg1 = resourceBundleMessageSource.getMessage("company.name.empty.error", null, LocaleContextHolder.getLocale());
        System.out.println(msg1);
    }

}
