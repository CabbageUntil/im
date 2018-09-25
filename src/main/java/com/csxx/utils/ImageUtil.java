package com.csxx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
public class ImageUtil {

    public static String saveImg(MultipartFile multipartFile, String path) throws IOException {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileSuffix;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        bos.close();

        return fileName;

    }

    public static String getFilePath() {

        // 根据当前时间生成目录yyyy/MM/dd
        Calendar calendar = Calendar.getInstance();
        String filePath = calendar.get(Calendar.YEAR) + File.separator +
                calendar.get(Calendar.MONTH) + File.separator +
                calendar.get(Calendar.DATE);

        return filePath;

    }

    public static String img2Base64(String path) {
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            log.error("图片{}转换失败：" + e.getMessage(), imgFile);
            return null;
        }
        return Base64.encodeBase64String(data);
    }

}
