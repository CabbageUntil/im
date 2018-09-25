package com.csxx.mapper.contact;

import com.csxx.bo.contact.WebContactCatAndAddress;
import com.csxx.dao.contact.mybatisModel.TelAddressList;
import com.csxx.utils.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TelAddressListMapper extends MyMapper<TelAddressList> {

    List<WebContactCatAndAddress> selectByAddressId(List<Integer> addressId);

    @Update("update tel_address_list set iso_country_code=#{iso_country_code},city=#{city},country=#{country}," +
        "label=#{label},postal_code=#{postal_code},state=#{state},street=#{street},sub_administrative_area=#{sub_administrative_area}," +
        "sub_locality=#{sub_locality} where id=#{id}")
    Integer updateDataToUrlEncode(@Param("iso_country_code") String iso_country_code,
                                  @Param("city") String city,
                                  @Param("country") String country,
                                  @Param("label") String label,
                                  @Param("postal_code") String postal_code,
                                  @Param("state") String state,
                                  @Param("street") String street,
                                  @Param("sub_administrative_area") String sub_administrative_area,
                                  @Param("sub_locality") String sub_locality,
                                  @Param("id") Integer id);

    /**
     * 用于自动合并后删除地址记录
     * @param ids
     * @return
     */
    @Delete(" delete from tel_address_list" +
            " where id in " +
            " (" +
            " select address_id" +
            " from tel_detail_list tdl" +
            " inner join tel_user_list tul" +
            " on tdl.user_id = tul.id" +
            " where tul.id in (${ids})" +
            " )")
    Integer deleteByAutoMergeIds(@Param(value = "ids") String ids);

    int batchUpdate(List<TelAddressList> list);

}
