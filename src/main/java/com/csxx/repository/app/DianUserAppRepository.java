package com.csxx.repository.app;

import com.csxx.dao.app.DianUserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DianUserAppRepository extends JpaRepository<DianUserApp, Integer> {

    int deleteByUserNameAndAppName(String userName, String appName);

    boolean existsByUserNameAndAppName(String userName, String appName);

}
