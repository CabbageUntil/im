package com.csxx.converter.contact;

import com.csxx.dao.contact.TelAddressList;
import com.csxx.dao.contact.TelDetailList;
import com.csxx.bo.contact.Address;
import com.csxx.bo.contact.ThreeAttr;
import com.csxx.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ThreeAttrToTelDetailSet {

    public static TelDetailList convert(ThreeAttr<String> threeAttr) {
        TelDetailList tmpList = new TelDetailList(
                threeAttr.getCategory(),
                threeAttr.getLabel(),
                threeAttr.getContent());
        try {
            tmpList.setLabel(URLEncoder.encode(tmpList.getLabel(), "utf-8"));
            tmpList.setContent(URLEncoder.encode(tmpList.getContent(), "utf-8"));
        } catch (Exception e) {
            log.error("ThreeAttrToTelDetailSet.convert UrlEncode转换失败");
        }
        return tmpList;
    }

    public static TelDetailList convertAddr(ThreeAttr<Address> threeAttr) {
        TelDetailList tmpList = new TelDetailList(
                threeAttr.getCategory(),
                threeAttr.getLabel(),
                null);
        try {
            tmpList.setLabel(URLEncoder.encode(tmpList.getLabel(), "utf-8"));
        } catch (Exception e) {
            log.error("ThreeAttrToTelDetailSet.convertAddr UrlEncode转换失败");
        }

        TelAddressList address = new TelAddressList(
                threeAttr.getContent().getIsoCountryCode(),
                threeAttr.getContent().getCity(),
                threeAttr.getContent().getCountry(),
                threeAttr.getContent().getLabel(),
                threeAttr.getContent().getPostalCode(),
                threeAttr.getContent().getState(),
                threeAttr.getContent().getStreet(),
                threeAttr.getContent().getSubAdministrativeArea(),
                threeAttr.getContent().getSubLocality()
        );
        try {
            ObjectUtil.convertObjectStrAttrToUrlEncode(address, "utf-8");
        } catch (Exception e) {
            log.error("ThreeAttrToTelDetailSet.convertAddr UrlEncode转换失败");
        }
        tmpList.setTelAddressList(address);
        return tmpList;
    }

    public static Set<TelDetailList> convertToSet(List<ThreeAttr<String>> threeAttrList) {
        return threeAttrList.stream().map(e -> convert(e)).collect(Collectors.toSet());
    }

    public static Set<TelDetailList> convertToAddrSet(List<ThreeAttr<Address>> threeAttrList) {
        return threeAttrList.stream().map(e -> convertAddr(e)).collect(Collectors.toSet());
    }

}
