package com.csxx.bo.contact;

import lombok.Data;

@Data
public class WebContactThreeAttr<T> {

    private Integer id;

    private String label;

    private String customLabel;

    private T content;

    private String remark;

    public WebContactThreeAttr() {
    }

    public WebContactThreeAttr(Integer id, String label, T content, String remark) {
        this.id = id;
        this.label = label;
        this.content = content;
        this.remark = remark;
    }

}
