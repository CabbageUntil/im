package com.csxx.service.webOrg;

import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface WebOrgAgendaService {

    int deleteOrgAgenda(String[] ids);
    TableDTO findByYear(Integer year,Integer page,Integer perPage,String sort);
    TableDTO findByMonth(Integer year,Integer month,Integer page,Integer perPage,String sort);
    TableDTO findByDay(Integer year,Integer month,Integer day,Integer page,Integer perPage,String sort);

    /**
     * 创建日程
     * @param userInfo 用户登录信息
     * @param deptId 部门编号
     * @param memberId 员工编号
     * @param note1 班组备注
     * @param note2 注意事宜
     * @param note3 个人备注
     * @param createDatetime
     * @return
     */
    int  addAgenda(UserInfo userInfo,Integer deptId,Integer memberId,String note1,String note2,String note3,Long createDatetime);

    /**
     * 查询日程的员工的日程信息
     * @param sort 排序
     * @param page 当前页数
     * @param perPage 每页显示
     * @param filter  员工姓名
     * @param userInfo 登录信息
     * @param start_time 查询开始时间
     * @param end_time 查询结束时间
     * @param deptId  部门编号
     * @return
     */
    ResponseEntity allAgendaList(String sort, Integer page, Integer perPage, String  filter, UserInfo userInfo,Long start_time, Long end_time,String deptId);

    /**
     * 根据日程编号删除日程
     * 注：单记录删除
     * @param userInfo
     * @param Id
     * @return
     */
    int deleteOrgAgendaByOne(UserInfo userInfo,String Id);

    /**
     * 批量删除公司日程信息
     * @param userInfo
     * @param Id
     * @return
     */
    int deleteOrgAgendaByOnes(UserInfo userInfo,String Id);
}
