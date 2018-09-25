package com.csxx.dto.webOrg.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class LabelValue {

    private String label;

    @JsonAlias(value = "content")
    private String value;

}
