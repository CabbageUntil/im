package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbMember;
import com.csxx.utils.memList;
import com.csxx.vo.webOrg.Archive;
import com.csxx.vo.webOrg.MemberInfoVO;
import com.csxx.vo.webOrg.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface AbMemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    /**
     * 查询指定公司员工列表
     * @param orgId 公司ID
     * @param filter 过滤条件：员工姓名
     * @param status 员工状态
     * @return
     */
    List<MemberInfoVO> fetchMemberListByFilterAndStatus(@Param("orgId") Integer orgId,
                                                        @Param("filter") String filter,
                                                        @Param("status") Integer status);
    /**
     * 查看所有员工的名单包含离职员工
     * @param orgId 公司ID
     * @param filter 过滤条件：员工姓名
     * @return
     */
    List<MemberInfoVO> fetchMemberListByFilter(@Param("orgId") Integer orgId,
                                                        @Param("filter") String filter);

    /**
     * 部门联动
     * 查询员工编号和姓名
     * @param abMember
     * @return
     */
    List<memList> selectByOrgIdAndDeptId(AbMember abMember);

    /**
     * 添加员工信息
     * @param record
     * @return
     */
    int insert(AbMember record);

    int insertSelective(AbMember record);
    //查询单个员工信息
    AbMember selectByPrimaryKey( Integer memberId);
    AbMember selectByMemberId(@Param( value="memberId") Integer memberId);

    /**
     * 更新员工
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AbMember record);

    int updateByPrimaryKey(AbMember record);

    /**
     * 找指定与指定用户有关联的全部公司名
     */
    HashMap<Integer, String> findAllCompanyByUsername(String username);
    /**
     * 找指定用户在某家公司下的员工信息
     */
    Archive findArchiveByUsernameAndOrgId(@Param("username") String username,
                                          @Param("orgId") Integer orgId);

    /**
     * 根据员工编号和公司编号查看员工详细信息
     * @param memberId
     * @return
     */
    Archive findArchiveByUsernameAndMemberId(@Param("memberId")Integer memberId ,@Param("orgId") Integer orgId );
    /**
     * 查找某个用户是否存在
     * @param orgId
     * @param username
     * @param status
     * @return
     */
    UserInfo selectByOrgIdAndUsernameAndStatus(@Param("orgId") Integer orgId,
                                               @Param("username") String username,
                                               @Param("status") Integer status);

    /**
     * 移除员工
     * @param record
     * @return
     */
    int RemoveMember(AbMember record);

}