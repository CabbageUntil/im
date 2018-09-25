package com.csxx.vo.webOrg;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberListDetail {

    private List<IdLabelContent> tel;

    private List<IdLabelContent> email;

    private List<IdLabelContent> address;

    private List<IdLabelContent> other;

}
