package com.csxx.vo.webContact;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeleteList implements Serializable {

    private static final long serialVersionUID = 7771639104562468214L;

    private Integer id;

    private String name;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date delTime;

}
