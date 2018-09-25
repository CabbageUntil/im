package com.csxx.dto.app.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class AppForm {

    private String userName;

    private String key;

    private String appName;

    @Min(value = 0, message = "The op code can't exceed the lower limit 0!")
    @Max(value = 1, message = "The op code can't exceed the upper limit 1!")
    private Integer op;

}
