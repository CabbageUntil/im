package com.csxx.mapper.contact;

import com.csxx.bo.contact.AutoMergePO;
import com.csxx.bo.contact.IdAndName;
import com.csxx.bo.contact.WebContactCatAndAddress;
import com.csxx.bo.contact.WebContactLine;
import com.csxx.dto.contact.WebContactDetailDTO;
import com.csxx.dao.contact.mybatisModel.TelUserList;
import com.csxx.utils.MyMapper;
import com.csxx.vo.contacts.UserWithPhoneList;
import com.csxx.vo.webContact.DeleteList;
import com.csxx.vo.webContact.SearchTreeVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TelUserListMapper extends MyMapper<TelUserList> {

    @Select("select id from tel_owner_list where user_name = #{userName}")
    Integer findByUserName(String userName);

    @Select("select id from tel_user_list where owner_id = #{ownerId} and (first_name + last_name) like #{filter}")
    List<Integer> findUserIdByName(@Param("ownerId") Integer ownerId,
                                   @Param("filter") String filter);

    @Select("select tul.id from tel_detail_list tdl, tel_user_list tul where tdl.user_id = tul.id" +
        " and tdl.category in ('email', 'mobile') and tdl.content like #{filter}" +
        " and tul.owner_id = #{ownerId}")
    List<Integer> findUserIdByEmailOrMobile(@Param("ownerId") Integer ownerId,
                                            @Param("filter") String filter);

    @Select("select 1 from tel_owner_list tol, tel_user_list tul where tol.id = tul.owner_id" +
        " and tol.user_name = #{owner} and tul.id = #{userId} and (tul.status is null or tul.status = 0)")
    Integer existsByOwnerAndUserId(@Param("owner") String owner,
                                   @Param("userId") Integer userId);

    @Select("select id, first_name, last_name, birthday, organization_name, department_name, job_title, note" +
        " from tel_user_list where id = #{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "organization_name", property = "company"),
            @Result(column = "department_name", property = "department"),
            @Result(column = "job_title", property = "job"),
            @Result(column = "note", property = "note")
    })
    WebContactDetailDTO findByUserId(Integer userId);

    /**
     * 根据用户账号找出其下全部或者指定ID的通讯人基本信息
     * @param owner
     * @return
     */
    @Select("<script>" +
            " with tol as (select id from tel_owner_list where user_name = #{owner})" +
            " select tul.id, (tul.last_name + tul.first_name) as name, tul.birthday, tul.job_title," +
            " (tul.organization_name + tul.department_name) as company_department, note" +
            " from tel_user_list tul" +
            " inner join tol" +
            " on tul.owner_id = tol.id" +
            " where ((tul.status is null) or (tul.status = 0))" +
            "<if test='ids != null'>" +
            " and tul.id in (${ids})" +
            "</if>" +
            "</script>")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "job_title", property = "job"),
            @Result(column = "company_department", property = "company_department"),
            @Result(column = "note", property = "note")
    })
    List<WebContactLine> findContactLineByOwner(@Param("owner") String owner, @Param("ids") String ids);

    /**
     * 根据通讯人ID找出其所有的明细数据（电话、邮箱、URL、地址）
     * @param ids
     * @return
     */
    @Select(" select tdl.id detail_id, tdl.user_id, tdl.category, tdl.label, tdl.content," +
            " tal.id address_id, tal.iso_country_code, tal.country, tal.state, tal.city," +
            " tal.sub_administrative_area, tal.sub_locality, tal.street, tal.postal_code" +
            " from tel_detail_list tdl" +
            " left join tel_address_list tal" +
            " on tdl.address_id = tal.id" +
            " where tdl.user_id in ${ids}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "detail_id", property = "detailId"),
            @Result(column = "category", property = "category"),
            @Result(column = "label", property = "label"),
            @Result(column = "content", property = "content"),
            @Result(column = "address_id", property = "addressId"),
            @Result(column = "iso_country_code", property = "countryCode"),
            @Result(column = "country", property = "country"),
            @Result(column = "state", property = "state"),
            @Result(column = "city", property = "city"),
            @Result(column = "sub_administrative_area", property = "admin"),
            @Result(column = "sub_locality", property = "locality"),
            @Result(column = "street", property = "street"),
            @Result(column = "postal_code", property = "postcode")
    })
    List<WebContactCatAndAddress> findContactLineDetailByUserIds(String ids);

    /**
     * 搜索树使用
     * 查找指定用户下的所有通讯人
     * @param owner
     * @return
     */
    @Select("select tul.id, (tul.last_name + tul.first_name) as name from tel_owner_list tol, " +
            "tel_user_list tul where tol.id = tul.owner_id and tol.user_name = #{owner} and " +
            "((tul.status is null) or (tul.status = 0))")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "label")
    })
    List<SearchTreeVO> findAllByOwner(String owner);

    /**
     * 搜索树使用
     * 查找指定用户下姓名、邮箱或手机包含特定关键字的通讯人
     * @param owner
     * @param filter
     * @return
     */
    @Select("select distinct tul.id, (tul.last_name + tul.first_name) as name" +
        " from tel_user_list tul" +
        " inner join tel_owner_list tol on tul.owner_id = tol.id" +
        " left join tel_detail_list tdl on tul.id = tdl.user_id" +
        " where tol.user_name = #{owner} and" +
        " (((tul.last_name + tul.first_name) like #{filter}) or" +
        " (tdl.content like #{filter} and tdl.category in ('email', 'mobile')))" +
        " and ((tul.status is null) or (tul.status = 0))")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "label")
    })
    List<SearchTreeVO> findAllByOwnerAndFilter(@Param("owner") String owner,
                                               @Param("filter") String filter);

    /**
     * 找出某个用户下的下一个比当前传入通讯人ID大的通讯人ID
     * @param owner
     * @param id
     * @return
     */
    @Select("select top 1 tul.id from tel_user_list tul, tel_owner_list tol" +
        " where tol.id = tul.owner_id and tol.user_name = #{owner} and tul.id > #{id}" +
        " and ((tul.status is null) or (tul.status = 0))")
    Integer findNextIdByOwnerAndId(@Param("owner") String owner,
                                   @Param("id") Integer id);

    /**
     * 找出某个用户下特定或全部通讯人的ID、姓名、手机、删除时间信息列表
     * 如果手机有多个则显示ID最小的那一个
     * @param owner
     * @param ids
     * @return
     */
    @Select("<script>" +
            "select distinct tul.id, (tul.last_name + tul.first_name) as name," +
            " first_value(tdl.content) over (partition by tdl.user_id order by tdl.id asc) as phone," +
            " tul.del_time" +
            " from tel_user_list tul" +
            " inner join Tel_Owner_List tol" +
            " on tol.id = tul.owner_id" +
            " left join (select id, user_id, content from Tel_Detail_List where category = 'mobile') tdl" +
            " on tul.id = tdl.user_id" +
            " where tol.user_name = #{owner} and tul.status = 1" +
            "<if test='ids != null'>" +
            " and tul.id in (${ids})" +
            "</if>" +
            "</script>")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "del_time", property = "delTime")
    })
    List<DeleteList> findDelListByOwner(@Param("owner") String owner, @Param("ids") String ids);

    /**
     * 根据姓名、手机、邮箱找出相应的用户ID
     * @param owner
     * @param filter
     * @return
     */
    @Select("<script>" +
            " with tol as (select id from tel_owner_list where user_name = #{owner})" +
            " select tul.id, ltrim(tul.last_name+tul.first_name) as name, ltrim(organization_name+department_name) as com" +
            " from tel_user_list tul" +
            " inner join tol" +
            " on tul.owner_id = tol.id" +
            " where (tul.last_name + tul.first_name) like ('%' +  #{filter} + '%')" +
            "<if test='status == null'>" +
            " and ((tul.status is null) or (tul.status = 0))" +
            "</if>" +
            "<if test='status == 1'>" +
            " and tul.status = 1" +
            "</if>" +
            " union" +
            " select tul.id, ltrim(tul.last_name+tul.first_name) as name, ltrim(organization_name+department_name) as com" +
            " from tel_user_list tul" +
            " inner join tol" +
            " on tul.owner_id = tol.id" +
            " inner join (select user_id from tel_detail_list where content like ('%' +  #{filter} + '%') and category in ('mobile', 'email')) tdl" +
            " on tul.id = tdl.user_id" +
            "<if test='status == null'>" +
            " where ((tul.status is null) or (tul.status = 0))" +
            "</if>" +
            "<if test='status == 1'>" +
            " where tul.status = 1" +
            "</if>" +
            "</script>"
            )
    List<IdAndName> findUserIdByOwnerAndFilterAndStatus(@Param("owner") String owner, @Param("filter") String filter, @Param("status") Integer status);

    /**
     * 检查是否有不在回收站中的通讯人
     * @param ids
     * @return
     */
    @Select(" select top 1 1 from tel_user_list where ((status is null) or (status = 0))" +
            " and id in (${ids})")
    Integer getUserStatus(@Param("ids") String ids);

    /**
     * 获取当前用户自动合并的数据
     * @param owner
     * @return
     */
    @Select(" with tol as (select id from tel_owner_list where user_name = #{owner})" +
            " select tul.id,tul.last_name,tul.first_name,tul.birthday,tul.job_title," +
            " tul.organization_name,tul.department_name,tul.note,tdl.mobiles,tdl.emails,tdl.urls,tdl.addresses" +
            " from tel_user_list tul" +
            " inner join tol" +
            " on tul.owner_id = tol.id" +
            " left join" +
            " (" +
            " SELECT user_id," +
            " mobiles=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'mobile' FOR XML PATH('')),1,1,''), " +
             "emails=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'email' FOR XML PATH('')),1,1,''), " +
            " urls=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'urlAddress' FOR XML PATH('')),1,1,''), " +
            " addresses=stuff((SELECT ','+ISNULL(t2.label,'')+';'+(ISNULL(t1.iso_country_code,'')+'+'+ISNULL(t1.country,'')+'+'+ISNULL(t1.state,'')+'+'+ISNULL(t1.city,'')+'+'+ISNULL(t1.sub_administrative_area,'')+'+'+ISNULL(t1.sub_locality,'')+'+'+ISNULL(t1.street,'')+'+'+ISNULL(t1.postal_code,'')+';'+ISNULL(remark,'')) FROM Tel_Address_List t1 INNER JOIN Tel_Detail_List t2 ON t1.id = t2.address_id WHERE t2.user_id = tdl.user_id AND category = 'address' FOR XML PATH('')),1,1,'')" +
            " FROM Tel_Detail_List AS tdl" +
            " GROUP BY user_id" +
            " ) tdl" +
            " on tdl.user_id = tul.id" +
            " where ((tul.status is null) or (tul.status = 0))" +
            " and (tul.last_name + tul.first_name) in (select (last_name + first_name) as name from Tel_User_List where owner_id = tol.id group by (last_name + first_name) having count(*) > 1)")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "job_title", property = "job"),
            @Result(column = "organization_name", property = "company"),
            @Result(column = "department_name", property = "department"),
            @Result(column = "note", property = "note"),
            @Result(column = "mobiles", property = "mobiles"),
            @Result(column = "emails", property = "emails"),
            @Result(column = "urls", property = "urls"),
            @Result(column = "addresses", property = "addresses")
    })
    List<AutoMergePO> getAutoMergeByOwner(String owner);

    /**
     * 获取当前用户手动合并的数据
     * @param owner
     * @return
     */
    @Select("<script>" +
            " with tol as (select id from tel_owner_list where user_name = #{owner})" +
            " select tul.id, tul.last_name, tul.first_name, tul.birthday, tul.job_title," +
            " tul.organization_name, tul.department_name, tul.note, tdl.mobiles, tdl.emails,tdl.urls,tdl.addresses" +
            " from tel_user_list tul" +
            " inner join tol" +
            " on tul.owner_id = tol.id" +
            " left join" +
            " (" +
            " SELECT user_id," +
            " mobiles=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'mobile' FOR XML PATH('')),1,1,''), " +
            " emails=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'email' FOR XML PATH('')),1,1,''), " +
            " urls=stuff((SELECT ','+ISNULL(label,'')+';'+ISNULL(content,'')+';'+ISNULL(remark,'') FROM Tel_Detail_List WHERE user_id=tdl.user_id AND category = 'urlAddress' FOR XML PATH('')),1,1,''), " +
            " addresses=stuff((SELECT ','+ISNULL(t2.label,'')+';'+(ISNULL(t1.iso_country_code,'')+'+'+ISNULL(t1.country,'')+'+'+ISNULL(t1.state,'')+'+'+ISNULL(t1.city,'')+'+'+ISNULL(t1.sub_administrative_area,'')+'+'+ISNULL(t1.sub_locality,'')+'+'+ISNULL(t1.street,'')+'+'+ISNULL(t1.postal_code,'')+';'+ISNULL(remark,'')) FROM Tel_Address_List t1 INNER JOIN Tel_Detail_List t2 ON t1.id = t2.address_id WHERE t2.user_id = tdl.user_id AND category = 'address' FOR XML PATH('')),1,1,'')" +
            " FROM Tel_Detail_List AS tdl" +
            " GROUP BY user_id" +
            " ) tdl" +
            " on tdl.user_id = tul.id" +
            " where ((tul.status is null) or (tul.status = 0))" +
            "<if test='ids != null'>" +
            " and tul.id in (${ids})" +
            "</if>" +
            "</script>")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "job_title", property = "job"),
            @Result(column = "organization_name", property = "company"),
            @Result(column = "department_name", property = "department"),
            @Result(column = "note", property = "note"),
            @Result(column = "mobiles", property = "mobiles"),
            @Result(column = "emails", property = "emails"),
            @Result(column = "urls", property = "urls"),
            @Result(column = "addresses", property = "addresses"),
    })
    List<AutoMergePO> getManualMergeByOwnerAndIds(@Param("owner") String owner,
                                                  @Param("ids") String ids);

    /**
     * 根据ID删除通讯人记录
     * @param ids
     * @return
     */
    @Delete("delete from tel_user_list where id in (${ids})")
    Integer deleteByIds(@Param(value = "ids") String ids);

    List<UserWithPhoneList> getUser(Integer owner_id);

    @Select("select t2.id from tel_owner_list t1,tel_user_list t2 where t1.id=t2.owner_id and (t2.status = 0 or t2.status is null) and t1.user_name = #{owner}")
    List<Integer> findNormalUserIdByOwnerId(String owner);

    @Select(" SELECT tul.id" +
            " FROM tel_user_list tul" +
            " INNER JOIN tel_owner_list tol" +
            " ON tol.id = tul.owner_id" +
            " WHERE (tul.first_name+tul.last_name)=#{name}" +
            " AND ((tul.status is null) or (tul.status = 0))" +
            " AND tol.user_name=#{owner}" +
            " ORDER BY tul.id" +
            " OFFSET 0 ROWS" +
            " FETCH NEXT 1 ROWS ONLY")
    Integer findTopOneByOwnerAndName(@Param("owner") String owner,
                                     @Param("name") String name);

    int insertOne(TelUserList telUserList);

    @Select("select tul.id from tel_user_list tul inner join tel_owner_list tol on tul.owner_id = tol.id" +
            " where tol.user_name = #{owner} and tul.status = 1")
    List<Integer> findAllDelIdByOwner(String owner);

    @Select("select 1 from tel_user_list tul inner join tel_owner_list tol on tul.owner_id = tol.id where " +
            "tul.id in (${ids}) and tol.user_name <> #{owner}")
    int findNotBelongOwner(@Param("owner") String owner, @Param("ids") String ids);

}
