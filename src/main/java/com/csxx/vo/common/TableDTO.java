package com.csxx.vo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableDTO<T> {

    private Long total;

    private Integer per_page;

    private Integer current_page;

    private Integer last_page;

    private String next_page_url;

    private String prev_page_url;

    private Long from;

    private Long to;

    private List<T> data;

}
