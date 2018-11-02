package com.csxx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 根据时间获取生肖
 */
public class AnimalUtils {
    public static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final String[] zodiacArray = {"8", "9", "10", "11", "0", "1", "2", "3", "4", "5", "6", "7"};
    /**
     * 根据日期获取生肖	 * 	 * @return
     */
    public static String date2Zodica(Calendar time) {
        return zodiacArray[time.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取生肖	 * 	 * @return
     */
    public static String getAnimal(String time) {
        Calendar c = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(time);
            c.setTime(date);
            String zodica = date2Zodica(c);
            return zodica;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String  formateDate(Date date){
        try {
            return sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
