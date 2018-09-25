package com.csxx.mapper.unifiedLogin;

import org.apache.ibatis.annotations.Select;

public interface FreeHostUserMapper {

    @Select("select top 1 1 from freehost_user where mobi = #{username}")
    Integer getExists(String username);

}
