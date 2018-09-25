package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbDept;
import com.csxx.utils.deptList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbDeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(AbDept record);

    int insertSelective(AbDept record);

    AbDept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(AbDept record);

    int updateByPrimaryKey(AbDept record);

    /**
     * 查询部门的相关信息
     * @param deptName 部门名称
     * @param orgId  公司编号
     * @param status  部门的状态
     * @return
     */
    List<AbDept> selectByNameAndStatus(@Param(value = "deptName") String deptName,
                                       @Param(value = "orgId") Integer orgId,
                                       @Param(value = "status") Integer status
    ) ;

    /**
     * 查询全部部门的信息(包含解散部门和正常部门)
     * @param deptName 部门名称
     * @param orgId  公司编号
     * @return
     */
    List<AbDept> selectByNameAndOrgId(@Param(value = "deptName") String deptName,
                                       @Param(value = "orgId") Integer orgId) ;
    /**
     *
     */
    List<deptList> selectDeptByOrgId(@Param(value = "orgId") Integer orgId) ;
}