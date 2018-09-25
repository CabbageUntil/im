package com.csxx.utils;

import com.csxx.bo.contact.AutoMergePO;
import org.apache.commons.lang3.StringUtils;

public class ObjectMatcher {

    public static boolean matchAutoMergePO(AutoMergePO one, AutoMergePO another) {
        boolean isMatch = true;

        if (one.getBirthday() != null &&
                another.getBirthday() != null &&
                !(one.getBirthday().equals(another.getBirthday()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getCompany()) &&
                StringUtils.isNotEmpty(another.getCompany()) &&
                !(one.getCompany().equals(another.getCompany()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getDepartment()) &&
                StringUtils.isNotEmpty(another.getDepartment()) &&
                !(one.getDepartment().equals(another.getDepartment()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getJob()) &&
                StringUtils.isNotEmpty(another.getJob()) &&
                !(one.getJob().equals(another.getJob()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getNote()) &&
                StringUtils.isNotEmpty(another.getNote()) &&
                !(one.getNote().equals(another.getNote()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getMobiles()) &&
                StringUtils.isNotEmpty(another.getMobiles()) &&
                !(one.getMobiles().equals(another.getMobiles()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getEmails()) &&
                StringUtils.isNotEmpty(another.getEmails()) &&
                !(one.getEmails().equals(another.getEmails()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getUrls()) &&
                StringUtils.isNotEmpty(another.getUrls()) &&
                !(one.getUrls().equals(another.getUrls()))) {
            isMatch = false;
        }
        else if (StringUtils.isNotEmpty(one.getAddresses()) &&
                StringUtils.isNotEmpty(another.getAddresses()) &&
                !(one.getAddresses().equals(another.getAddresses()))) {
            isMatch = false;
        }

        return isMatch;
    }

}
