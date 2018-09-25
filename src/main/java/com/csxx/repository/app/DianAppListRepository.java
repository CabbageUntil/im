package com.csxx.repository.app;

import com.csxx.dao.app.DianAppList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DianAppListRepository extends JpaRepository<DianAppList, Integer> {

    DianAppList findByAppName(String appName);

    @Query("select al from DianAppList al left join al.dianUserAppSet du" +
        " where du.userName = ?1")
    List<DianAppList> findByUserNameOrderById(String userName);

}
