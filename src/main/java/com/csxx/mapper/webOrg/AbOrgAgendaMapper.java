package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbOrgAgenda;
import com.csxx.utils.ScheduleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbOrgAgendaMapper {
    int deleteByPrimaryKey(String agendaId);
    int insert(AbOrgAgenda record);

    int insertSelective(AbOrgAgenda record);

    AbOrgAgenda selectByPrimaryKey(String agendaId);

    int updateByPrimaryKeySelective(AbOrgAgenda record);

    int updateByPrimaryKey(AbOrgAgenda record);

    /**
     * 2018年8月1日11:56:32
     * 实现日程信息批量删除
     */
    int  deleteOrgAgenda(List<String> list);


    List<AbOrgAgenda>  selectByYear(@Param("year") Integer year);

    List<AbOrgAgenda> selectByMonth(@Param("year") Integer year, @Param("month") Integer month);

    List<AbOrgAgenda> selectByDay(@Param("year") Integer year,@Param("month") Integer month,@Param("day") Integer day);

    /**
     * 查询公司的日程信息
     * @return
     */
    List<AbOrgAgenda>  abOrgAgendaList(AbOrgAgenda abOrgAgenda);

    /**
     * 日程记录单条删除
     * @param Id
     * @return
     */
    int  deleteOrgAgendaByOne(@Param("agendaId") String Id);

    /**
     * 根据用户编号和时间查询用户日程信息
     * @return
     */
    List<ScheduleList> selectByMemberIdAndDate(AbOrgAgenda abOrgAgenda);

}