package com.csxx.bo.contact;

import com.csxx.dao.contact.TelAddressList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    @JsonProperty(value = "ISOCountryCode")
    private String isoCountryCode;

    private String city;

    private String country;

    private String label;

    private String postalCode;

    private String state;

    private String street;

    private String subAdministrativeArea;

    private String subLocality;

    @JsonProperty(value = "ISOCountryCode")
    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public Address() {
    }

    public Address(TelAddressList telAddressList) {

        this.isoCountryCode = telAddressList.getIsoCountryCode();
        this.city = telAddressList.getCity();
        this.country = telAddressList.getCountry();
        this.label = telAddressList.getLabel();
        this.postalCode = telAddressList.getPostalCode();
        this.state = telAddressList.getState();
        this.street = telAddressList.getStreet();
        this.subAdministrativeArea = telAddressList.getSubAdministrativeArea();
        this.subLocality = telAddressList.getSubLocality();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubAdministrativeArea() {
        return subAdministrativeArea;
    }

    public void setSubAdministrativeArea(String subAdministrativeArea) {
        this.subAdministrativeArea = subAdministrativeArea;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }
}
