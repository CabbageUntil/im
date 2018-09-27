package com.csxx.converter.org;

import com.csxx.dao.webOrg.AbMemberDetail;
import com.csxx.dto.webOrg.form.LabelValue;

import java.util.List;
import java.util.stream.Collectors;

public class LabelValue2AbMemberDetail {

    /**
     * 将详细信息的编号也传入到AbMemberDetail对象之中
     * @param labelValue
     * @param category
     * @param memberId
     * @return
     */
    public static AbMemberDetail convert(LabelValue labelValue, String category, Integer memberId) {
        AbMemberDetail abMemberDetail = new AbMemberDetail();
        abMemberDetail.setCategory(category);
        abMemberDetail.setLabel(labelValue.getLabel());
        abMemberDetail.setContent(labelValue.getValue());
        abMemberDetail.setMemberId(memberId);
        //2018年9月27日09:47:10
        //abMemberDetail.setDetailId(labelValue.getId());
        return abMemberDetail;
    }

    public static List<AbMemberDetail> convertToList(List<LabelValue> labelValueList, String category,  Integer memberId) {
        return labelValueList.stream().map(e -> convert(e, category, memberId)).collect(Collectors.toList());
    }

}
