package com.csxx.dao.contact;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tel_address_list")
@Data
public class TelAddressList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String isoCountryCode;

    private String city;

    private String country;

    private String label;

    private String postalCode;

    private String state;

    private String street;

    private String subAdministrativeArea;

    private String subLocality;

    public TelAddressList() {
    }

    public TelAddressList(String isoCountryCode, String city, String country, String label, String postalCode, String state, String street, String subAdministrativeArea, String subLocality) {
        this.isoCountryCode = isoCountryCode;
        this.city = city;
        this.country = country;
        this.label = label;
        this.postalCode = postalCode;
        this.state = state;
        this.street = street;
        this.subAdministrativeArea = subAdministrativeArea;
        this.subLocality = subLocality;
    }

}
