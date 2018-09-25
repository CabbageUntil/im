package com.csxx.utils;
/**
 * 改类主要是为了获取下拉列表菜单
 */

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class deptList {
    @Getter
    @Setter
    private String deptName;
    @Getter
    @Setter
    private Integer deptId;
}
