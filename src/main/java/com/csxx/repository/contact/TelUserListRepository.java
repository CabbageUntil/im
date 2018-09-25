package com.csxx.repository.contact;

import com.csxx.dao.contact.TelOwnerList;
import com.csxx.dao.contact.TelUserList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TelUserListRepository extends JpaRepository<TelUserList, Integer>, JpaSpecificationExecutor<TelUserList> {

    @Query("select count(t) from TelUserList t where owner_id = :ownerId")
    Integer sumByOwnerId(@Param("ownerId") Integer ownerId);

    Page<TelUserList> findByTelOwnerList(TelOwnerList telOwnerList, Pageable pageable);

    Page<TelUserList> findByIdIn(List<Integer> id, Pageable pageable);

    TelUserList findAllById(Integer id);

    @Modifying
    @Query(value = "update tel_user_list set status = ?2, del_time = ?3 where id in (?1) and owner_id = (select id from tel_owner_list where user_name = ?4)",
        nativeQuery = true)
    Integer updateStatusByIdIn(List<Integer> ids, Integer status, Date delTime, String owner);

    @Query(value = "select tdl.id as detail_id, tdl.address_id as address_id" +
        " from tel_user_list tul, tel_detail_list tdl where tul.id = tdl.user_id and tul.id in (?1)",
        nativeQuery = true)
    List<Object[]> findRelatedIdByUserIdIn(List<Integer> ids);

    @Modifying
    @Query(value = "delete from tel_user_list where id in (?1)",
        nativeQuery = true)
    Integer nativeDeleteById(List<Integer> ids);

    @Query(value = "select distinct (first_name + ' ' + last_name) from tel_user_list where owner_id = ?1",
        nativeQuery = true)
    List<String> getAllName(Integer ownerId);

    @Query(value = "select distinct tdl.content" +
            " from" +
            " tel_user_list tul" +
            " inner join tel_detail_list tdl" +
            " on tdl.user_id = tul.id" +
            " where (tul.first_name + ' ' + tul.last_name) like '%'+?2+'%'" +
            " and tdl.category = 'mobile'" +
            " and tul.owner_id = ?1",
        nativeQuery = true)
    List<String> getPhoneByName(Integer id, String name);

    @Query("select t from TelUserList t where t.telOwnerList = ?1 and (t.status = ?2 or t.status is null)")
    List<TelUserList> findByTelOwnerListAndStatus(TelOwnerList telOwnerList, Boolean status);

    void deleteById(Integer id);

    void deleteByIdIn(List<Integer> ids);

}
