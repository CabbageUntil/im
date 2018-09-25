package com.csxx.converter.contact;

import com.csxx.dao.contact.TelAddressList;
import com.csxx.dao.contact.TelDetailList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AllInOne2PO {

    public static List<TelDetailList> AllInOneStrToTelDetailList(String content, String category) {
        List<TelDetailList> telDetailLists = new ArrayList<>();
        if (StringUtils.isEmpty(content)) {
            return telDetailLists;
        }
        String[] contentGroup = content.split(",", -1);
        for (int i = 0; i < contentGroup.length; i++) {
            String[] temp = contentGroup[i].split(";", -1);
            TelDetailList telDetailList = new TelDetailList();
            telDetailList.setCategory(category);
            telDetailList.setLabel(temp[0]);
            if (category.equals("address")) {
                telDetailList.setTelAddressList(AllInOneStrToTelAddressList(temp[1]));
            } else {
                telDetailList.setContent(temp[1]);
            }
            telDetailList.setRemark(temp[2]);
            telDetailLists.add(telDetailList);
        }
        return telDetailLists;
    }

    public static TelAddressList AllInOneStrToTelAddressList(String content) {
        String[] addresses = content.split("\\+", -1);
        TelAddressList telAddressList = new TelAddressList();
        telAddressList.setIsoCountryCode(addresses[0]);
        telAddressList.setCountry(addresses[1]);
        telAddressList.setState(addresses[2]);
        telAddressList.setCity(addresses[3]);
        telAddressList.setSubAdministrativeArea(addresses[4]);
        telAddressList.setSubLocality(addresses[5]);
        telAddressList.setStreet(addresses[6]);
        telAddressList.setPostalCode(addresses[7]);
        return telAddressList;
    }

}
