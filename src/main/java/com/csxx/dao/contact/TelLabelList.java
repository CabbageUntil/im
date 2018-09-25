package com.csxx.dao.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tel_label_list")
@Data
public class TelLabelList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer labelId;

    private String category;

    @JsonProperty(value = "value")
    private String en;

    @JsonProperty(value = "label")
    private String cn;

    public TelLabelList() {
    }

}
