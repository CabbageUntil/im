package com.csxx.utils;

import com.csxx.bo.contact.AutoMergePO;
import org.apache.commons.lang3.StringUtils;

public class ObjectMixer {

    public static AutoMergePO mixAutoMergePO(AutoMergePO owning, AutoMergePO owned) {
        if (owning.getBirthday() == null && owned.getBirthday() != null) {
            owning.setBirthday(owned.getBirthday());
        }
        if (StringUtils.isEmpty(owning.getCompany()) && StringUtils.isNotEmpty(owned.getCompany())) {
            owning.setCompany(owned.getCompany());
        }
        if (StringUtils.isEmpty(owning.getDepartment()) && StringUtils.isNotEmpty(owned.getDepartment())) {
            owning.setDepartment(owned.getDepartment());
        }
        if (StringUtils.isEmpty(owning.getJob()) && StringUtils.isNotEmpty(owned.getJob())) {
            owning.setJob(owned.getJob());
        }
        if (StringUtils.isEmpty(owning.getNote()) && StringUtils.isNotEmpty(owned.getNote())) {
            owning.setNote(owned.getNote());
        }
        if (StringUtils.isEmpty(owning.getMobiles()) && StringUtils.isNotEmpty(owned.getMobiles())) {
            owning.setMobiles(owned.getMobiles());
        }
        if (StringUtils.isEmpty(owning.getEmails()) && StringUtils.isNotEmpty(owned.getEmails())) {
            owning.setEmails(owned.getEmails());
        }
        if (StringUtils.isEmpty(owning.getUrls()) && StringUtils.isNotEmpty(owned.getUrls())) {
            owning.setUrls(owned.getUrls());
        }
        if (StringUtils.isEmpty(owning.getAddresses()) && StringUtils.isNotEmpty(owned.getAddresses())) {
            owning.setAddresses(owned.getAddresses());
        }

        return owning;
    }

}
