package com.csxx.mapper.app;

import com.csxx.vo.app.AppDTO;
import com.csxx.dto.app.form.AppQueryForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface DianAppListMapper {

    @Select("select * from dian_applist")
    @Results({
            @Result(column = "id", property = "appId"),
            @Result(column = "appname", property = "appName"),
            @Result(column = "name", property = "name"),
            @Result(column = "pic_link", property = "img"),
            @Result(column = "link_str", property = "linkStr"),
            @Result(column = "is_other", property = "isOther"),
            @Result(column = "app_type", property = "appType")
    })
    List<AppDTO> findAll();

    @SelectProvider(type = AppDaoProvider.class, method = "findAppByAttr")
    @Results({
            @Result(column = "id", property = "appId"),
            @Result(column = "appname", property = "appName"),
            @Result(column = "name", property = "name"),
            @Result(column = "pic_link", property = "img"),
            @Result(column = "link_str", property = "linkStr"),
            @Result(column = "is_other", property = "isOther"),
            @Result(column = "app_type", property = "appType")
    })
    List<AppDTO> findAppByAttr(AppQueryForm queryForm);

    @SelectProvider(type = AppDaoProvider.class, method = "findAppByUserName")
    @Results({
            @Result(column = "id", property = "appId"),
            @Result(column = "appname", property = "appName"),
            @Result(column = "name", property = "name"),
            @Result(column = "pic_link", property = "img"),
            @Result(column = "link_str", property = "linkStr"),
            @Result(column = "is_other", property = "isOther"),
            @Result(column = "app_type", property = "appType")
    })
    List<AppDTO> findAppByUserName(AppQueryForm queryForm);

    class AppDaoProvider {
        public String findAppByAttr(AppQueryForm queryForm) {
            return new SQL() {{
                SELECT("appname,is_other,app_type");
                SELECT("id,name,link_str,pic_link");
                FROM("dian_applist");
                if (StringUtils.isNotEmpty(queryForm.getAppName())) {
                    WHERE("name like #{appName}");
                }
                if (StringUtils.isNotEmpty(queryForm.getAppType())) {
                    WHERE("app_type like #{appType}");
                }
            }}.toString();
        }
        public String findAppByUserName(AppQueryForm queryForm) {
            return new SQL() {{
                SELECT("da.appname,da.is_other,da.app_type");
                SELECT("da.id,name,da.link_str,da.pic_link");
                FROM("dian_applist da");
                FROM("dian_userapp du");
                WHERE("da.id = du.app_id");
                WHERE("du.username=#{userName}");
            }}.toString();
        }
    }

}
