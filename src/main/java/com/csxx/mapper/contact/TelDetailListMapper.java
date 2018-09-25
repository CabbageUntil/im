package com.csxx.mapper.contact;

import com.csxx.bo.contact.WebContactAllInOne;
import com.csxx.bo.contact.WebContactCategory;
import com.csxx.bo.contact.WebContactThreeAttr;
import com.csxx.dao.contact.mybatisModel.TelDetailList;
import com.csxx.utils.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TelDetailListMapper extends MyMapper<TelDetailList> {

    @Select("select id,category,ISNULL(label,'') as label,ISNULL(content,'') as content,address_id,ISNULL(remark,'') as remark from tel_detail_list where user_id = #{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "category", property = "category"),
            @Result(column = "label", property = "label"),
            @Result(column = "content", property = "content"),
            @Result(column = "address_id", property = "addressId"),
            @Result(column = "remark", property = "remark")
    })
    List<WebContactCategory> findDetailByUserId(Integer userId);

    @Select("select address_id from tel_detail_list where category = #{category} and user_id = #{userId}")
    List<Integer> findByCategoryAndUserId(@Param("category") String category,
                                          @Param("userId") Integer userId);

    @Select("SELECT user_id," +
            "category=(STUFF((SELECT char(0)+category" +
            " FROM Tel_Detail_List" +
            " WHERE user_id = Test.user_id" +
            " FOR" +
            " XML PATH('')" +
            " ), 1, 1, '') )," +
            " content = ( STUFF(( SELECT char(0)+content" +
            " FROM Tel_Detail_List" +
            " WHERE user_id=Test.user_id" +
            " FOR" +
            " XML PATH('')" +
            " ),1,1,''))," +
            " label=(STUFF((SELECT char(0)+label" +
            " FROM Tel_Detail_List" +
            " WHERE user_id = Test.user_id" +
            " FOR" +
            " XML PATH('')" +
            " ),1,1,''))" +
            " FROM Tel_Detail_List AS Test" +
            " GROUP BY user_id")
    @Results({
            @Result(column = "user_id", property = "id"),
            @Result(column = "category", property = "label"),
            @Result(column = "label", property = "customLabel"),
            @Result(column = "content", property = "content")
    })
    List<WebContactThreeAttr<String>> testCombine();

    @Update("update tel_detail_list set category=#{category}, label=#{label}, content=#{content} where id = #{id}")
    Integer updateDataToUrlEncode(@Param("category") String category,
                                  @Param("label") String label,
                                  @Param("content") String content,
                                  @Param("id") Integer id);

    @Select("SELECT user_id," +
            "mobiles=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'mobile' FOR XML PATH('')),1,1,''), " +
            "emails=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'email' FOR XML PATH('')),1,1,''), " +
            "urls=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'urlAddress' FOR XML PATH('')),1,1,''), " +
            "addresses=stuff((SELECT ','+ISNULL(t2.label,'')+';'+(ISNULL(t1.iso_country_code,'')+'+'+ISNULL(t1.country,'')+'+'+ISNULL(t1.state,'')+'+'+ISNULL(t1.city,'')+'+'+ISNULL(t1.sub_administrative_area,'')+'+'+ISNULL(t1.sub_locality,'')+'+'+ISNULL(t1.street,'')+'+'+ISNULL(t1.postal_code,'')+';'+ISNULL(remark,'')) FROM Tel_Address_List t1 INNER JOIN Tel_Detail_List t2 ON t1.id = t2.address_id WHERE t2.user_id = tdl.user_id AND category = 'address' FOR XML PATH('')),1,1,'')" +
            " FROM Tel_Detail_List AS tdl" +
            " WHERE tdl.user_id in (${ids})" +
            " GROUP BY user_id")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "mobiles", property = "mobiles"),
            @Result(column = "emails", property = "emails"),
            @Result(column = "urls", property = "urls"),
            @Result(column = "addresses", property = "addresses")
    })
    List<WebContactAllInOne> findAllInOne(@Param("ids") String ids);

    /**
     * 用于自动合并后删除明细记录
     * @param ids
     * @return
     */
    @Delete(" delete from tel_detail_list" +
            " where id in " +
            " (" +
            " select tdl.id" +
            " from tel_detail_list tdl" +
            " inner join tel_user_list tul" +
            " on tdl.user_id = tul.id" +
            " where tul.id in (${ids})" +
            " )")
    Integer deleteByAutoMergeIds(@Param(value = "ids") String ids);

    int batchUpdate(List<TelDetailList> list);

    int insertOne(TelDetailList telDetailList);

}
