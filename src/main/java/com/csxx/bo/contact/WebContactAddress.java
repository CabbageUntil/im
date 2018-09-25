package com.csxx.bo.contact;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class WebContactAddress {

    private String countryCode;

    private String country;

    private String state;

    private String city;

    private String admin;

    private String locality;

    private String street;

    private String postcode;

    private String data;

    public String getData() {
        String data = StringUtils.defaultString(country)
                .concat(StringUtils.defaultString(state))
                .concat(StringUtils.defaultString(city))
                .concat(StringUtils.defaultString(admin))
                .concat(StringUtils.defaultString(locality))
                .concat(StringUtils.defaultString(street));
        if (StringUtils.isNotEmpty(this.postcode)) {
            data.concat("，" + this.postcode);
        }
        return data;
    }

}
