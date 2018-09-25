package com.csxx.bo.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ThreeAttr<T> {

    private String category;

    private String label;

    private T content;

    public ThreeAttr() {
    }

    public ThreeAttr(String category, String label, T content) {
        this.category = category;
        this.label = label;
        this.content = content;
    }
}
