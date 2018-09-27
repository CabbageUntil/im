package com.csxx.controller.webOrg;

import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.UserInfoEnum;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.service.webOrg.WebOrgAgendaService;
import com.csxx.utils.DateUtils;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.webOrg.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


@RestController
@RequestMapping("/org")
@Slf4j
public class WebOrgAgendaContnroller {

    @Autowired
    private WebOrgAgendaService webOrgAgendaService;
    /**
     * 获取用户session信息，并转换数据类型
     * @param session
     * @return
     */
    private UserInfo getUserInfo(HttpSession session) {
        return (UserInfo)session.getAttribute(UserInfoEnum.USERINFO);
    }
    /**
     * 删除公司日程
     * @param session
     * @return
     */
    @DeleteMapping("/deleteOrgAgendaByOne")
    public ResponseEntity deleteOrgAgenda(HttpSession session,@RequestParam("agendaId") String Id){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            int i = webOrgAgendaService.deleteOrgAgendaByOne(userInfo,Id);
            if(i>0){
                return ResponseEntityUtil.success(ResultEnum.SUCCESS);
            }else{
                return  ResponseEntityUtil.error(1000011,"删除日程失败！！");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 批量删除日程
     * @param session
     * @param Id
     * @return
     */
    @RequestMapping("/deleteOrgAgendaByOnes")
    @ResponseBody
    public ResponseEntity deleteOrgAgendas(HttpSession session,@RequestParam("agendaIds") String Id){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            int i = webOrgAgendaService.deleteOrgAgendaByOnes(userInfo,Id);
            if(i>0){
                return ResponseEntityUtil.success(ResultEnum.SUCCESS);
            }else{
                return  ResponseEntityUtil.error(1000011,"删除日程失败！！");
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 查询日程信息
     * @param session
     * @param sort 排序
     * @param page  页码
     * @param perPage 每页显示条数
     * @param start_time 起始时间
     * @param end_time  结束时间
     * @param deptId 部门编号
     * @param filter 个人姓名
     * @return
     */
    @RequestMapping("/scheduleList")
    @ResponseBody
    public ResponseEntity allAgendaList(HttpSession session,
                                      @RequestParam("sort") String sort,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("per_page") Integer perPage,
                                      @RequestParam("date1") String   start_time,
                                      @RequestParam("date2") String   end_time,
                                      @RequestParam("deptId") String  deptId,
                                      @RequestParam(value = "filter", required = false) String filter) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            //return null;end\
            if(start_time!=null&&!"".equals(start_time)&&end_time!=null&&!"".equals(end_time)&&!"null".equals(start_time)&&!"null".equals(end_time)){
                Long date1= Long.parseLong(start_time);
                Long date2= Long.parseLong(end_time);
                return webOrgAgendaService.allAgendaList(sort, page, perPage, filter, userInfo,date1,date2,deptId);
            }
            else  if(start_time!=null&&!"".equals(start_time)&&!"null".equals(start_time)&&end_time == null&&"".equals(end_time)){
                Long date1= Long.parseLong(start_time);
                return webOrgAgendaService.allAgendaList(sort, page, perPage, filter, userInfo,date1,null,deptId);
            }
           else if(end_time!=null&&!"".equals(end_time)&&!"null".equals(end_time)&&start_time == null&&"".equals(start_time)){
                Long date2 = Long.parseLong(end_time);
                return webOrgAgendaService.allAgendaList(sort, page, perPage, filter, userInfo,null,date2,deptId);
            }else {
                return webOrgAgendaService.allAgendaList(sort, page, perPage, filter, userInfo,null,null,deptId);
            }

        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 查询制定用户的日程信息
     * @param session
     * @param memberId
     * @return
     */
    @RequestMapping("/fetchCalendar")
    @ResponseBody
    public ResponseEntity selectByMemberIdAndDate(HttpSession session,
                                        @RequestParam("memberId") Integer memberId){
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
            return webOrgAgendaService.selectByMemberIdAndDate(userInfo,memberId);
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }
    /**
     * 创建日程
     * @param session
     * @param note1 班组备注
     * @param note2 注意事项
     * @param note3 个人备注
     * @param memberId 员工编号
     * @param deptId 部门编号
     * @param date 日程时间
     * @return
     */
    @RequestMapping("/addSchedule")
    @ResponseBody
    public ResponseEntity addAgenda(HttpSession session,
                                        @RequestParam("note1") String note1,
                                        @RequestParam("note2") String note2,
                                        @RequestParam("note3") String note3,
                                        @RequestParam("memberId") Integer memberId,
                                        @RequestParam("deptId") Integer deptId,
                                        @RequestParam("createDatetime") Long date) {
        UserInfo userInfo = getUserInfo(session);
        if (userInfo != null) {
           int i = webOrgAgendaService.addAgenda(userInfo,deptId,memberId,note1,note2,note3,date);
            if(i>0){
                return ResponseEntityUtil.success(ResultEnum.SUCCESS);
            }else{
                return  ResponseEntityUtil.error(ResultEnum.Fail);
            }
        } else {
            return ResponseEntityUtil.error(ResultEnum.NEED_LOGIN);
        }
    }

    /**
     * 根据年份查询公司日程
     * @param session
     * @param page
     * @param perPage
     * @param sort
     * @param year
     * @return
     */
    @GetMapping("/findOrAgendaByYear")
    public TableDTO findByYear(HttpSession session,
                               @RequestParam("page") Integer page,
                               @RequestParam("per_page") Integer perPage,
                               @RequestParam("sort") String  sort,
                               @RequestParam("year") Integer year){
        return webOrgAgendaService.findByYear(year,page,perPage,sort);
    }

    /**
     * 根据年月查询公司日程
     * @param request
     * @param page
     * @param perPage
     * @param sort
     * @param year
     * @param month
     * @return
     */
    @GetMapping("/findOrAgendaByMonth")
    public TableDTO findMonth(HttpServletRequest request,
              @RequestParam("page") Integer page,
              @RequestParam("per_page") Integer perPage,
              @RequestParam("sort") String  sort,
              @RequestParam("year") Integer year,
             @RequestParam("month") Integer month){
        return webOrgAgendaService.findByMonth(year,month,page,perPage,sort);
    }

    /**
     * 根据年月日查询日程
     * @param request
     * @param page
     * @param perPage
     * @param sort
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GetMapping("/findOrAgendaByDay")
    public TableDTO findDay(HttpServletRequest request,
              @RequestParam("page") Integer page,
              @RequestParam("per_page") Integer perPage,
              @RequestParam("sort") String  sort,
              @RequestParam("year") Integer year,
             @RequestParam("month") Integer month,
             @RequestParam("day") Integer day){
        return webOrgAgendaService.findByDay(year,month,day,page,perPage,sort);
    }

}
