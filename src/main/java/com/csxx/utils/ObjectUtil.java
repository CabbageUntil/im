package com.csxx.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class ObjectUtil {

    public static void convertObjectStrAttrToUrlEncode(Object model, String encoding)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
        Field[] field = model.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组

        for(int j = 0; j < field.length; j++) { //遍历所有属性
            String name = field[j].getName(); //获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); //获取属性的类型
            if (type.equals("class java.lang.String")) { //如果type是类类型，则前面包含"class "，后面跟类名
                Method getter = model.getClass().getMethod("get" + name); // 获取getter方法
                String value = StringUtils.defaultString((String) getter.invoke(model)); // 执行getter方法
                Method setter = model.getClass().getMethod("set"+name, new Class[] {String.class}); // 获取setter方法
                setter.invoke(model, new Object[] {URLEncoder.encode(value, encoding) }); // 执行setter方法
            }
        }
    }

    public static void convertObjectStrAttrToUrlDecode(Object model, String encoding)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException {
        Field[] field = model.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组

        for(int j = 0; j < field.length; j++) { //遍历所有属性
            String name = field[j].getName(); //获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); //获取属性的类型
            if (type.equals("class java.lang.String")) { //如果type是类类型，则前面包含"class "，后面跟类名
                Method getter = model.getClass().getMethod("get" + name); // 获取getter方法
                String value = StringUtils.defaultString((String) getter.invoke(model)); // 执行getter方法
                Method setter = model.getClass().getMethod("set"+name, new Class[] {String.class}); // 获取setter方法
                setter.invoke(model, new Object[] {URLDecoder.decode(value, encoding) }); // 执行setter方法
            }
        }
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

}

