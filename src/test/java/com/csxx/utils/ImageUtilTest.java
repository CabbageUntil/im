package com.csxx.utils;

import org.junit.Test;

public class ImageUtilTest {
    @Test
    public void img2Base64() throws Exception {

        String str = ImageUtil.img2Base64("E:/JavaResources/imgs/app/onenet.png");
        System.out.println(str);

    }

}