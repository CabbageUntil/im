package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbRole;

public interface AbRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(AbRole record);

    int insertSelective(AbRole record);

    AbRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(AbRole record);

    int updateByPrimaryKey(AbRole record);
}