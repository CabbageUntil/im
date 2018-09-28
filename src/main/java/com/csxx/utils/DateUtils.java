package com.csxx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        System.out.println(getDateFormat("2018-08-05T16:00:00.000Z")

        );
    }

    /**
     * 2018-08-05T16:00:00.000Z
     * @param create_time
     * @return
     */
    public static Date getDateFormat(String create_time){
        Date d = new Date();
        if (create_time != null && create_time != "NULL" && create_time != "") {
                //转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
                create_time = create_time.replace("Z", " UTC");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                return sdf1.parse(create_time);//Mon Mar 06 00:00:00 CST 2017
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    private static boolean isDate(String date)
    {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

}
