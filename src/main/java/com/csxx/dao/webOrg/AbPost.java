package com.csxx.dao.webOrg;

public class AbPost {
    private Integer postId;

    private Integer orgId;

    private String postName;

    public AbPost(Integer postId, Integer orgId, String postName) {
        this.postId = postId;
        this.orgId = orgId;
        this.postName = postName;
    }

    public AbPost() {
        super();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName == null ? null : postName.trim();
    }
}