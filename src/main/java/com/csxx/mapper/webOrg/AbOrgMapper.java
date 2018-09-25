package com.csxx.mapper.webOrg;

import com.csxx.bo.webOrg.JoinCom;
import com.csxx.bo.webOrg.OwnCom;
import com.csxx.dao.webOrg.AbOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbOrgMapper {
    // 标准BEGIN
    int deleteByPrimaryKey(Integer orgId);

    int insert(AbOrg record);

    int insertSelective(AbOrg record);

    AbOrg selectByPrimaryKey(Integer orgId);

    int updateByPrimaryKeySelective(AbOrg record);

    int updateByPrimaryKey(AbOrg record);
    // 标准END

    /**
     * 检查公司是否已存在
     * @param orgName 公司名
     * @return
     */
    boolean existsByOrgName(String orgName);

    /**
     * 根据用户名和公司名查询已加入公司
     * @param user 用户名
     * @param filter 公司名
     * @return
     */
    List<JoinCom> selectJoinComByUserFilter(@Param("user") String user, @Param("filter") String filter);

    /**
     * 根据用户名称查询未加入的公司的名称
     * @param user
     * @param filter
     * @return
     */
    List<AbOrg> selectOtherComByUserFilter(@Param("user") String user, @Param("filter") String filter);
    /**
     * 根据用户名和公司名查询已拥有公司
     * @param user 用户名
     * @param filter 公司名
     * @return
     */
    List<OwnCom> selectOwnComByUserFilter(@Param("user") String user, @Param("filter") String filter);

    /**
     * 查询指定公司名的数量
     * @param orgName 公司名
     * @return
     */
    int selectCountByOrgName(String orgName);

    /**
     * 查询已加入的公司数量
     * @param username 用户名
     * @return
     */
    int selectJoinComCountByUsername(String username);

    /**
     * 查询已拥有的公司数量
     * @param username 用户名
     * @return
     */
    int selectOwnComCountByUsername(String username);
}