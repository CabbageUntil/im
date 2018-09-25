package com.csxx.repository.webOrg;

import com.csxx.handle.MyResultHandler;
import com.csxx.mapper.webOrg.AbMemberMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class MapRepository extends SqlSessionDaoSupport {
    @Override
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<Integer, String> findAllCompanyByUsername(String username){
        MyResultHandler handler = new MyResultHandler();
        this.getSqlSession().select(AbMemberMapper.class.getName()+".findAllCompanyByUsername", username, handler);
        return handler.getMappedResults();
    }
}
