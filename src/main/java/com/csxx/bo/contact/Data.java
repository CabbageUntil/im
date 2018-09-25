package com.csxx.bo.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

    @JsonProperty(value = "userInfo")
    private User user;

    private List<ThreeAttr<Address>> addresses;

    private List<ThreeAttr<String>> emails;

    private List<ThreeAttr<String>> mobiles;

    private List<ThreeAttr<String>> urlAddresses;

    public List<ThreeAttr<Address>> getAddresses() {
        return addresses;
    }

    @JsonProperty(value = "userInfo")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAddresses(List<ThreeAttr<Address>> addresses) {
        this.addresses = addresses;
    }

    public List<ThreeAttr<String>> getEmails() {
        return emails;
    }

    public void setEmails(List<ThreeAttr<String>> emails) {
        this.emails = emails;
    }

    public List<ThreeAttr<String>> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<ThreeAttr<String>> mobiles) {
        this.mobiles = mobiles;
    }

    public List<ThreeAttr<String>> getUrlAddresses() {
        return urlAddresses;
    }

    public void setUrlAddresses(List<ThreeAttr<String>> urlAddresses) {
        this.urlAddresses = urlAddresses;
    }

}
