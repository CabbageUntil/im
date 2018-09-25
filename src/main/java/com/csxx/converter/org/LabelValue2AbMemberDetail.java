package com.csxx.converter.org;

import com.csxx.dao.webOrg.AbMemberDetail;
import com.csxx.dto.webOrg.form.LabelValue;

import java.util.List;
import java.util.stream.Collectors;

public class LabelValue2AbMemberDetail {

    public static AbMemberDetail convert(LabelValue labelValue, String category, Integer memberId) {
        AbMemberDetail abMemberDetail = new AbMemberDetail();
        abMemberDetail.setCategory(category);
        abMemberDetail.setLabel(labelValue.getLabel());
        abMemberDetail.setContent(labelValue.getValue());
        abMemberDetail.setMemberId(memberId);
        return abMemberDetail;
    }

    public static List<AbMemberDetail> convertToList(List<LabelValue> labelValueList, String category,  Integer memberId) {
        return labelValueList.stream().map(e -> convert(e, category, memberId)).collect(Collectors.toList());
    }

}
