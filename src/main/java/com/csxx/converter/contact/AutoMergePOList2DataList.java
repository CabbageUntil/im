package com.csxx.converter.contact;

import com.csxx.bo.contact.AutoMergePO;
import com.csxx.bo.contact.Address;
import com.csxx.bo.contact.Data;
import com.csxx.bo.contact.ThreeAttr;
import com.csxx.bo.contact.User;
import com.csxx.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AutoMergePOList2DataList {

    private static List<ThreeAttr<String>> buildNormalAttr(List<String> labelGroup, List<String> contentGroup, String category) {
        List<ThreeAttr<String>> threeAttrList = new ArrayList<>();
        for (int i = 0; i < labelGroup.size(); i++) {
            ThreeAttr<String> obj = new ThreeAttr<>(category, labelGroup.get(i), contentGroup.get(i));
            try {
                obj.setLabel(URLDecoder.decode(obj.getLabel(), "utf-8"));
                obj.setContent(URLDecoder.decode(obj.getContent(), "utf-8"));
            } catch (Exception e) {
                log.error("【通讯录同步转换编码失败】");
            }
            threeAttrList.add(obj);
        }

        return threeAttrList;
    }

    private static List<ThreeAttr<Address>> buildAddressAttr(List<String> labelGroup, List<String> contentGroup) {
        List<ThreeAttr<Address>> threeAttrList = new ArrayList<>();
        for (int i = 0; i < labelGroup.size(); i++) {
            // 包装Address对象
            Address address = new Address();
            String[] addressContent;
            addressContent = contentGroup.get(i).split("\\+", -1);
            address.setIsoCountryCode(addressContent[0]);
            address.setCountry(addressContent[1]);
            address.setState(addressContent[2]);
            address.setCity(addressContent[3]);
            address.setSubAdministrativeArea(addressContent[4]);
            address.setSubLocality(addressContent[5]);
            address.setStreet(addressContent[6]);
            address.setPostalCode(addressContent[7]);
            try {
                ObjectUtil.convertObjectStrAttrToUrlDecode(address, "utf-8");
            } catch (Exception e) {
                log.error("【通讯录同步转换编码失败】");
            }
            try {
                ThreeAttr<Address> obj = new ThreeAttr<>(
                        "address",
                        URLDecoder.decode(labelGroup.get(i), "utf-8"),
                        address);
                threeAttrList.add(obj);
            } catch (Exception e) {
                log.error("【通讯录同步转换编码失败】");
            }
        }

        return threeAttrList;
    }

    private static User buildUserInfo(AutoMergePO autoMergePO) {
        User user = new User();
        user.setFirstName(autoMergePO.getFirstName());
        user.setLastName(autoMergePO.getLastName());
        user.setBirthday(autoMergePO.getBirthday());
        user.setOrganizationName(autoMergePO.getCompany());
        user.setDepartmentName(autoMergePO.getDepartment());
        user.setJobTitle(autoMergePO.getJob());
        user.setNote(autoMergePO.getNote());
        user.setMidName("");

        return user;
    }

    private static Data convertAutoMergePOToData(AutoMergePO autoMergePO) {
        List<String> labelGroup;
        List<String> contentGroup;
        List<String> remarkGroup;
        Data data = new Data();
        data.setUser(buildUserInfo(autoMergePO));
        if (StringUtils.isNotEmpty(autoMergePO.getMobiles())) {
            labelGroup = new ArrayList<>();
            contentGroup = new ArrayList<>();
            remarkGroup = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getMobiles().split(",", -1));
            AutoMergePO2WebContactDetailDTO.strToGrp(list, labelGroup, contentGroup, remarkGroup);
            data.setMobiles(buildNormalAttr(labelGroup, contentGroup, "mobile"));
        }
        if (StringUtils.isNotEmpty(autoMergePO.getEmails())) {
            labelGroup = new ArrayList<>();
            contentGroup = new ArrayList<>();
            remarkGroup = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getEmails().split(",", -1));
            AutoMergePO2WebContactDetailDTO.strToGrp(list, labelGroup, contentGroup, remarkGroup);
            data.setEmails(buildNormalAttr(labelGroup, contentGroup, "email"));
        }
        if (StringUtils.isNotEmpty(autoMergePO.getUrls())) {
            labelGroup = new ArrayList<>();
            contentGroup = new ArrayList<>();
            remarkGroup = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getUrls().split(",", -1));
            AutoMergePO2WebContactDetailDTO.strToGrp(list, labelGroup, contentGroup, remarkGroup);
            data.setUrlAddresses(buildNormalAttr(labelGroup, contentGroup, "urlAddress"));
        }
        if (StringUtils.isNotEmpty(autoMergePO.getAddresses())) {
            labelGroup = new ArrayList<>();
            contentGroup = new ArrayList<>();
            remarkGroup = new ArrayList<>();
            List<String> list = Arrays.asList(autoMergePO.getAddresses().split(",", -1));
            AutoMergePO2WebContactDetailDTO.strToGrp(list, labelGroup, contentGroup, remarkGroup);
            data.setAddresses(buildAddressAttr(labelGroup, contentGroup));
        }

        return data;
    }

    public static List<Data> convert(List<AutoMergePO> autoMergePOList) {
        return autoMergePOList.stream().map(e -> convertAutoMergePOToData(e)).collect(Collectors.toList());
    }

}
