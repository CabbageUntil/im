package com.csxx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * author:penghaijie
 * time:2018年8月29日18:01:57
 * 作用：ID生成器
 */
public class NumberUtil {
    public static String newNumberID() {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmSSS");
        String timeStampStr = simpleDateFormat.format(date);
        sb.append(timeStampStr);
        for (int i=0;i<4;i++){
            Random r = new Random();
            int n = r.nextInt(10);
            sb.append(n);
        }
        return sb.toString();
    }
   /* public static void main(String[] agrs){
        System.out.println("时间字符串" +newNumberID());
    }*/
}
