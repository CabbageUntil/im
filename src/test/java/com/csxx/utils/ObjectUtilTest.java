package com.csxx.utils;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectUtilTest {
    @Test
    public void convertObjectStrAttrToUrlEncode() throws Exception {

        @Data
        class MyClass {
            private String myName;

            private Integer age;
        }

        MyClass myClass = new MyClass();
        myClass.setMyName("ABC DEF");
        myClass.setAge(18);

        ObjectUtil.convertObjectStrAttrToUrlEncode(myClass, "utf-8");

        System.out.println(myClass.getMyName());
        System.out.println(myClass.getAge());

    }

}