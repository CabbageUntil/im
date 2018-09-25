package com.csxx.dao.webOrg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbMemberDetail {
    private Integer detailId;

    private Integer memberId;

    private String category;

    private String label;

    private String content;
}