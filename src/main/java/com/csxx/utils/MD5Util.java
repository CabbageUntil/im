package com.csxx.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * Created by Kenzo on 5/15/2018
 */
public class MD5Util {

    //生成MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }

    public static Boolean validate(String userName, String key, String code) {

        Boolean isValid = true;

        if (StringUtils.isEmpty(userName) || StringUtils.isBlank(key) || key.length() != 32) {
            isValid = false;
        } else if (!MD5Util.getMD5(userName + code).equalsIgnoreCase(key)) {
            isValid = false;
        }

        return isValid;
    }

}