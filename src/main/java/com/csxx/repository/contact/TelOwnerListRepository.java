package com.csxx.repository.contact;

import com.csxx.dao.contact.TelOwnerList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelOwnerListRepository extends JpaRepository<TelOwnerList, Integer> {

    TelOwnerList findByUserName(String userName);

    boolean existsByUserName(String userName);

}
