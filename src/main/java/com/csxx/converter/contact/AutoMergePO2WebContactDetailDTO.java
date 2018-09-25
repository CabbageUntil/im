package com.csxx.converter.contact;

import com.csxx.bo.contact.AutoMergePO;
import com.csxx.bo.contact.WebContactAddress;
import com.csxx.dto.contact.WebContactDetailDTO;
import com.csxx.bo.contact.WebContactThreeAttr;
import com.csxx.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AutoMergePO2WebContactDetailDTO {

    private static void buildStrList(List<WebContactThreeAttr<String>> toList, List<String> labelGrp, List<String> contentGrp, List<String> remarkGrp) {
        for (int i = 0; i < labelGrp.size(); i++) {
            WebContactThreeAttr<String> obj = new WebContactThreeAttr<>();
            obj.setLabel(labelGrp.get(i));
            obj.setContent(contentGrp.get(i));
            obj.setRemark(remarkGrp.get(i));
            try {
                ObjectUtil.convertObjectStrAttrToUrlDecode(obj, "utf-8");
                obj.setContent(URLDecoder.decode(obj.getContent(), "utf-8"));
            } catch (Exception e) {
                log.error("AutoMergePO2WebContactDetailDTO.buildStrList方法解码失败");
            }
            toList.add(obj);
        }
    }

    private static void buildSpecList(List<WebContactThreeAttr<WebContactAddress>> toList, List<String> labelGrp,
                                     List<String> contentGrp, List<String> remarkGrp) {
        for (int i = 0; i < labelGrp.size(); i++) {
            WebContactThreeAttr<WebContactAddress> specObj = new WebContactThreeAttr<>();
            specObj.setLabel(labelGrp.get(i));
            try {
                ObjectUtil.convertObjectStrAttrToUrlDecode(specObj, "utf-8");
            } catch (Exception e) {
                log.error("AutoMergePO2WebContactDetailDTO.buildSpecList方法解码地址失败");
            }
            String[] addressGrp = contentGrp.get(i).split("\\+", -1);
            WebContactAddress contactAddress = new WebContactAddress();
            contactAddress.setCountryCode(addressGrp[0]);
            contactAddress.setCountry(addressGrp[1]);
            contactAddress.setState(addressGrp[2]);
            contactAddress.setCity(addressGrp[3]);
            contactAddress.setAdmin(addressGrp[4]);
            contactAddress.setLocality(addressGrp[5]);
            contactAddress.setStreet(addressGrp[6]);
            contactAddress.setPostcode(addressGrp[7]);
            try {
                ObjectUtil.convertObjectStrAttrToUrlDecode(contactAddress, "utf-8");
            } catch (Exception e) {
                log.error("AutoMergePO2WebContactDetailDTO.buildSpecList方法解码地址明细失败");
            }
            specObj.setContent(contactAddress);

            toList.add(specObj);
        }
    }

    static void strToGrp(List<String> fromList, List<String> toLabelGrp, List<String> toContentGrp, List<String> toRemarkGrp) {
        for (String str : fromList) {
            String[] group = str.split(";", -1);
            toLabelGrp.add(group[0]);
            toContentGrp.add(group[1]);
            toRemarkGrp.add(group[2]);
        }
    }

    public static WebContactDetailDTO convert(AutoMergePO autoMergePO) {
        WebContactDetailDTO webContactDetailDTO = new WebContactDetailDTO();
        BeanUtils.copyProperties(autoMergePO, webContactDetailDTO);
        webContactDetailDTO.setId(autoMergePO.getUserId());

        List<String> labelGrp;
        List<String> contentGrp;
        List<String> remarkGrp;

        // 组装电话数据
        List<WebContactThreeAttr<String>> phoneList = new ArrayList<>();
        if (StringUtils.isNotEmpty(autoMergePO.getMobiles())) {
            labelGrp = new ArrayList<>();
            contentGrp = new ArrayList<>();
            remarkGrp = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getMobiles().split(",", -1));
            strToGrp(list, labelGrp, contentGrp, remarkGrp);
            buildStrList(phoneList, labelGrp, contentGrp, remarkGrp);
            webContactDetailDTO.setPhoneList(phoneList);
        }

        // 组装电子邮件数据
        List<WebContactThreeAttr<String>> emailList = new ArrayList<>();
        if (StringUtils.isNotEmpty(autoMergePO.getEmails())) {
            labelGrp = new ArrayList<>();
            contentGrp = new ArrayList<>();
            remarkGrp = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getEmails().split(",", -1));
            strToGrp(list, labelGrp, contentGrp, remarkGrp);
            buildStrList(emailList, labelGrp, contentGrp, remarkGrp);
            webContactDetailDTO.setEmailList(emailList);
        }

        // 组装网址数据
        List<WebContactThreeAttr<String>> urlList = new ArrayList<>();
        if (StringUtils.isNotEmpty(autoMergePO.getUrls())) {
            labelGrp = new ArrayList<>();
            contentGrp = new ArrayList<>();
            remarkGrp = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getUrls().split(",", -1));
            strToGrp(list, labelGrp, contentGrp, remarkGrp);
            buildStrList(urlList, labelGrp, contentGrp, remarkGrp);
            webContactDetailDTO.setUrlList(urlList);
        }

        // 组装地址数据
        List<WebContactThreeAttr<WebContactAddress>> addressList = new ArrayList<>();
        if (StringUtils.isNotEmpty(autoMergePO.getAddresses())) {
            labelGrp = new ArrayList<>();
            contentGrp = new ArrayList<>();
            remarkGrp = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getAddresses().split(",", -1));
            strToGrp(list, labelGrp, contentGrp, remarkGrp);
            buildSpecList(addressList, labelGrp, contentGrp, remarkGrp);
            webContactDetailDTO.setAddressList(addressList);
        }

        return webContactDetailDTO;
    }

    public static List<WebContactDetailDTO> convertToList(List<AutoMergePO> autoMergePOList) {
        return autoMergePOList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
