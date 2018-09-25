package com.csxx.repository.contact;

import com.csxx.dao.contact.TelDetailList;
import com.csxx.dao.contact.TelUserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TelDetailListRepository extends JpaRepository<TelDetailList, Integer> {

    @Modifying
    @Query(value = "delete from tel_detail_list where user_id = ?1" +
            " and category in (?2)",
            nativeQuery = true)
    Integer deleteByUserIdAndCategoryIn(Integer userId,
                                        List<String> categories);

    @Query(value = "select t from TelDetailList t where t.id = ?1 and t.category = 'address'")
    TelDetailList findByUserAddress(Integer userId);

    List<TelDetailList> findByTelUserListAndCategory(TelUserList telUserList, String category);

    @Modifying
    @Query(value = "delete from tel_detail_list where id in (?1)",
        nativeQuery = true)
    Integer nativeDeleteById(List<Integer> ids);

}
