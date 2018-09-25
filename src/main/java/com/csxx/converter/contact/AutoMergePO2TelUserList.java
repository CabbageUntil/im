package com.csxx.converter.contact;

import com.csxx.dao.contact.TelDetailList;
import com.csxx.dao.contact.TelUserList;
import com.csxx.bo.contact.AutoMergePO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AutoMergePO2TelUserList {

    public static TelUserList convert(AutoMergePO autoMergePO) {

        TelUserList telUserList = new TelUserList();
        BeanUtils.copyProperties(autoMergePO, telUserList);
        telUserList.setId(autoMergePO.getUserId());
        telUserList.setJobTitle(autoMergePO.getJob());
        telUserList.setOrganizationName(autoMergePO.getCompany());
        telUserList.setDepartmentName(autoMergePO.getDepartment());

        List<TelDetailList> telDetailLists = new ArrayList<>();
        telDetailLists.addAll(AllInOne2PO.AllInOneStrToTelDetailList(
                autoMergePO.getMobiles(), "mobile"));
        telDetailLists.addAll(AllInOne2PO.AllInOneStrToTelDetailList(
                autoMergePO.getEmails(), "email"));
        telDetailLists.addAll(AllInOne2PO.AllInOneStrToTelDetailList(
                autoMergePO.getUrls(), "urlAddress"));
        telDetailLists.addAll(AllInOne2PO.AllInOneStrToTelDetailList(
                autoMergePO.getAddresses(), "address"));

        telUserList.setTelDetailLists(new HashSet<>(telDetailLists));

        return telUserList;
    }

}
