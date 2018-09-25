package com.csxx.converter.contact;

import com.csxx.bo.contact.WebContactThreeAttr;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
public class List2Object {

    public static void convertToThreeAttr(List<String> fromList, List<WebContactThreeAttr<String>> toList) {
        for (int i = 0; i < fromList.size(); i++) {
            String[] group = fromList.get(i).split(";", -1);
            if (group[1].contains("+")) {
                group[1] = group[1].replaceAll("\\+", "");
            }
            try {
                toList.add(new WebContactThreeAttr<>(
                        null,
                        URLDecoder.decode(StringUtils.defaultString(group[0]), "utf-8"),
                        URLDecoder.decode(StringUtils.defaultString(group[1]), "utf-8"),
                        URLDecoder.decode(StringUtils.defaultString(group[2]), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                log.error("URL解码失败:{}", e.getMessage());
            }
        }
    }

}
