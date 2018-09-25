package com.csxx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     *  根据响应的参数返回响应的数值
     * @param str:0  month:1  day:2
     * @param time
     * @return
     */
    public static Integer   splitDate(int str,long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        if(str==0) {
            return cal.get(Calendar.YEAR);
        }else if(str == 1) {
            return cal.get(Calendar.MONTH)+1;
        }else if(str == 2) {
            return cal.get(Calendar.DATE);
        }
        return 0;
    }

    /**
     * 年份-月份-天
     * @param d
     * @return
     */
    public static Date  forMatter(String d){
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dd = sdf.parse(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dd;
    }
    /**
     * 年份-月份-天  时：分：秒
     * @param d
     * @return
     */
    public static Date  forMatterBySeconde(String d){
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dd = sdf.parse(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dd;
    }

    /**
     * 时间毫秒数转成时间
     * @return
     */
    public static Date longToDate(long t){
        Date date = new Date();
        date.setTime(t);
        return date;
    }
    public static void main(String args[]){
        System.out.println(longToDate(1537492709000L));
    }
}
