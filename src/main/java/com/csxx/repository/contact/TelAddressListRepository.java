package com.csxx.repository.contact;

import com.csxx.dao.contact.TelAddressList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TelAddressListRepository extends JpaRepository<TelAddressList, Integer> {

    @Modifying
    @Query(value = "delete from tel_address_list where id in (?1)",
            nativeQuery = true)
    Integer deleteByIdIn(List<Integer> ids);

}
