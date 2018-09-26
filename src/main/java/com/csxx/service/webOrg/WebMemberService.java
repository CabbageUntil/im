package com.csxx.service.webOrg;

import com.csxx.bo.contact.User;
import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.Archive;
import com.csxx.vo.webOrg.UserInfo;

import java.util.Map;

public interface WebMemberService {

    /**
     * 查询公司员工列表
     * @param sort 排序
     * @param page 第几页
     * @param perPage 每页数量
     * @param filter 过滤
     * @param userInfo 当前用户身份信息
     * @param type 指定要查找的员工状态
     * @return
     */
    ResponseEntity memberList(String sort, Integer page, Integer perPage, String filter, UserInfo userInfo, Integer type);

    /**
     * 查询某位员工的明细信息
     * @param userInfo 当前用户身份信息
     * @param memberId 要查找的员工工号
     * @return
     */
    ResponseEntity memberListDetail(UserInfo userInfo, Integer memberId);

    TableDTO findDetailMe(Integer memberId);

    /**
     * 找出跟某个用户有关联的员工信息总数量
     */
    Map<Integer,String> fetchArchiveGeneral(String username);

    /**
     * 查询员工详细信息
     * @param username 用户名
     * @param orgId 公司ID
     * @return
     */
    Archive fetchSpecArchive(String username, Integer orgId);

    /**
     * 移除员工
     * @param memberId
     * @return
     */
    int  updateMemberStatus(Integer memberId);

    /**
     * 根据部门编号查询员工的信息
     * @param userInfo
     * @param deptId
     * @return
     */
    ResponseEntity memSelectList(UserInfo userInfo,Integer deptId);

    /**
     * 根据用户编号查询用户信息
     * @param info
     * @param memberId
     * @return
     */
    Archive findMemberByMemberId(UserInfo info, Integer memberId);

    /**
     * 保存用户修改信息
     * @param user
     * @param jionComForm
     */
    void saveMember(String user, JoinComForm jionComForm);

}
