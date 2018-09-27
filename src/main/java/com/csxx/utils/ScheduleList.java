package com.csxx.utils;

import lombok.Data;

import java.util.Date;

/**
 * @author peng
 * @date 2018年9月27日16:51:36
 * dec:用来接收个人的日程的实体类
 */
@Data
public class ScheduleList {

    private String id;//编号
    private String title; //标题
    private String description;//描述
    private String start;//日程执行时间
}
