package com.csxx.converter.contact;

import com.csxx.dao.contact.TelDetailList;
import com.csxx.bo.contact.WebContactThreeAttr;
import org.apache.commons.lang3.StringUtils;

public class WebContactThreeAttr2TelDetailList {

    public static TelDetailList convertStr(WebContactThreeAttr<String> item,
                                           String category) {
        TelDetailList telDetailList = new TelDetailList();

        telDetailList.setId(item.getId());
        telDetailList.setCategory(category);
        telDetailList.setRemark(item.getRemark());
        if (StringUtils.isNotEmpty(item.getCustomLabel())) {
            telDetailList.setLabel(item.getCustomLabel());
        } else {
            telDetailList.setLabel(item.getLabel());
        }
        telDetailList.setContent(item.getContent());

        return telDetailList;
    }

}
