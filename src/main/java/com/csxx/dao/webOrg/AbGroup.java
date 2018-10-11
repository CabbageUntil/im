package com.csxx.dao.webOrg;

import lombok.Data;

import java.util.Date;

/**
 * @name群组管理实体类
 * @author:peng
 */
@Data
public class AbGroup {
    private String groupId;//编号
    private String name ;//群名称
    private Byte   status;//状态
    private Date createDate;//创建日期
    private Date removeDate;//解散日期
}
