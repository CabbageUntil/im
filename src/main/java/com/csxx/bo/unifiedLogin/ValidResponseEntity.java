package com.csxx.bo.unifiedLogin;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ValidResponseEntity {

    @SerializedName("boolen")
    private Integer isOk;

    private String message;

    private ValidResponseData data;

}
