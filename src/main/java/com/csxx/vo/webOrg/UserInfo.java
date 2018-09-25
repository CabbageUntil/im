package com.csxx.vo.webOrg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private String username;

    private String role;

    private String name;

    private String avatar;

    private Integer orgId;

    private Integer memberId;

    private String orgName;

    private String memberName;

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", orgId=" + orgId +
                ", memberId=" + memberId +
                ", orgName='" + orgName + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
