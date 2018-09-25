package com.csxx.converter.contact;

import com.csxx.bo.contact.WebContactCatAndAddress;
import com.csxx.bo.contact.WebContactCategory;
import com.csxx.bo.contact.WebContactAddress;
import com.csxx.bo.contact.WebContactThreeAttr;
import com.csxx.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CategoryList2ThreeAttrList {

    private static WebContactThreeAttr<String> convertToStr(WebContactCategory webContactCategory) {
        WebContactThreeAttr<String> webContactThreeAttr = new WebContactThreeAttr();
        BeanUtils.copyProperties(webContactCategory, webContactThreeAttr);
        try {
            webContactThreeAttr.setLabel(URLDecoder.decode(webContactCategory.getLabel(), "utf-8"));
            webContactThreeAttr.setContent(URLDecoder.decode(webContactCategory.getContent(), "utf-8"));
            webContactThreeAttr.setRemark(URLDecoder.decode(webContactCategory.getRemark(), "utf-8"));
        } catch (Exception e) {
            log.error("解码失败：" + e);
        }
        return webContactThreeAttr;
    }

    private static WebContactThreeAttr<WebContactAddress> convertToWebContactAddress(WebContactCatAndAddress source) {
        WebContactThreeAttr<WebContactAddress> webContactThreeAttr = new WebContactThreeAttr();
        webContactThreeAttr.setId(source.getDetailId());
        webContactThreeAttr.setLabel(source.getLabel());
        try {
            webContactThreeAttr.setRemark(URLDecoder.decode(source.getRemark(), "utf-8"));
        } catch (Exception e) {
            log.error("解码失败：" + e);
        }
        WebContactAddress webContactAddress = new WebContactAddress();
        BeanUtils.copyProperties(source, webContactAddress);
        webContactAddress.setData(
                StringUtils.defaultString(webContactAddress.getCountry()) +
                StringUtils.defaultString(webContactAddress.getState()) +
                StringUtils.defaultString(webContactAddress.getCity()) +
                StringUtils.defaultString(webContactAddress.getAdmin()) +
                StringUtils.defaultString(webContactAddress.getLocality()) +
                StringUtils.defaultString(webContactAddress.getStreet())
        );
        if (StringUtils.isNotEmpty(webContactAddress.getPostcode())) {
            webContactAddress.setData(webContactAddress.getData() + ", " + webContactAddress.getPostcode());
        }
        try {
            ObjectUtil.convertObjectStrAttrToUrlDecode(webContactAddress, "utf-8");
        } catch (Exception e) {
            log.error("解码失败：" + e);
        }
        webContactThreeAttr.setContent(webContactAddress);
        return webContactThreeAttr;
    }

    // content为String类型
    public static List<WebContactThreeAttr<String>> convertToStrList(List<WebContactCategory> webContactCategoryList,
                                                   String category) {
        return webContactCategoryList.stream().filter(e ->
            e.getCategory().equals(category)
        ).map(e -> convertToStr(e)).collect(Collectors.toList());
    }

    // content为特殊对象类型
    public static List<WebContactThreeAttr<WebContactAddress>> convertToWebContactAddressList(List<WebContactCatAndAddress> sourceList) {
        return sourceList.stream().map(e ->
                convertToWebContactAddress(e)
        ).collect(Collectors.toList());
    }

}
