package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbPost;

public interface AbPostMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(AbPost record);

    int insertSelective(AbPost record);

    AbPost selectByPrimaryKey(Integer postId);

    int updateByPrimaryKeySelective(AbPost record);

    int updateByPrimaryKey(AbPost record);
}