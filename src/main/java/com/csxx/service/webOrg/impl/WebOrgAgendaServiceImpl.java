package com.csxx.service.webOrg.impl;

import com.csxx.config.webOrg.WebOrgConfig;
import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.dao.webOrg.AbMember;
import com.csxx.dao.webOrg.AbOrgAgenda;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.MemberStatusEnum;
import com.csxx.enums.webOrg.RoleEnum;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.mapper.webOrg.AbOrgAgendaMapper;
import com.csxx.utils.DateUtils;
import com.csxx.utils.NumberUtil;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.utils.ScheduleList;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.service.webOrg.WebOrgAgendaService;
import com.csxx.vo.webOrg.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebOrgAgendaServiceImpl implements WebOrgAgendaService {

    @Autowired
    private AbMemberMapper abMemberMapper;
    @Autowired
    private AbOrgAgendaMapper abOrgAgendaMapper;
    @Autowired
    private WebOrgConfig webOrgConfig;
    /**
     * 根据年份查询公司日程
     * @param year
     * @param page
     * @param perPage
     * @param sort
     * @return
     */
    @Override
    public TableDTO findByYear(Integer year,Integer page,Integer perPage,String sort) {
        String sortPredcate;
        TableDTO<AbOrgAgenda> tableDTO;
        if(StringUtils.isNotBlank(sort)){
            sortPredcate = "member_id asc";
        }else{
            sortPredcate = sort.split("\\|")[0]+" "+sort.split("\\|")[1];
        }
        PageHelper.startPage(page,perPage,sortPredcate);
        String pageParam = "sort=".concat(sortPredcate).concat("&per_page=").concat(perPage.toString());
        if(year!=null){
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByYear(year);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig().concat(pageParam).concat("&year=").concat(year+""));
            return tableDTO;
        }else {
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByYear(000);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig().concat(pageParam).concat("&year="));
            return tableDTO;

        }
    }

    /**
     * 根据年月查询公司日程
     * @param year
     * @param month
     * @param page
     * @param perPage
     * @param sort
     * @return
     */
    @Override
    public TableDTO findByMonth(Integer year, Integer month, Integer page, Integer perPage, String sort) {
        String sortPredcate;
        TableDTO<AbOrgAgenda> tableDTO;
        if(StringUtils.isNotBlank(sort)){
            sortPredcate = "member_id asc";
        }else{
            sortPredcate = sort.split("\\|")[0]+" "+sort.split("\\|")[1];
        }
        PageHelper.startPage(page,perPage,sortPredcate);
        String pageParam = "sort=".concat(sortPredcate).concat("&per_page=").concat(perPage.toString());
        if(year!=null){
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByMonth(year,month);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig()
                    .concat(pageParam).concat("&month=").concat(month+"").concat("&year=").concat(year+""));
            return tableDTO;
        }else {
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByMonth(year,month);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig().concat(pageParam).concat("&year=").concat("&month="));
            return tableDTO;

        }
    }

    /**
     * 根据年月日公司日程
     * @param year
     * @param month
     * @param day
     * @param page
     * @param perPage
     * @param sort
     * @return
     */
    @Override
    public TableDTO findByDay(Integer year, Integer month, Integer day, Integer page, Integer perPage, String sort) {
        String sortPredcate;
        TableDTO<AbOrgAgenda> tableDTO;
        if(StringUtils.isNotBlank(sort)){
            sortPredcate = "member_id asc";
        }else{
            sortPredcate = sort.split("\\|")[0]+" "+sort.split("\\|")[1];
        }
        PageHelper.startPage(page,perPage,sortPredcate);
        String pageParam = "sort=".concat(sortPredcate).concat("&per_page=").concat(perPage.toString());
        if(year!=null){
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByDay(year,month,day);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig()
                    .concat(pageParam).concat("&month=").concat(month+"").concat("&year=").concat(year+"").concat("&day=").concat(day+""));
            return tableDTO;
        }else {
            List<AbOrgAgenda> abOrgAgenda = abOrgAgendaMapper.selectByDay(year,month,day);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgenda);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,webOrgConfig.getFindOrgAgendaByYearConfig().concat(pageParam).concat("&year=").concat("&month=").concat("&day="));
            return tableDTO;

        }
    }

    /**
     * 创建日程
     *
     * @param userInfo       用户登录信息
     * @param deptId         部门编号
     * @param memberId       员工编号
     * @param note1          班组备注
     * @param note2          注意事宜
     * @param note3          个人备注
     * @param createDatetime
     * @return
     */
    @Override
    @Transactional
    public int addAgenda(UserInfo userInfo, Integer deptId, Integer memberId, String note1, String note2, String note3, Long createDatetime) {
        AbOrgAgenda abOrgAgenda =  new AbOrgAgenda();
        abOrgAgenda.setArrange_date(DateUtils.longToDate(createDatetime));
        abOrgAgenda.setOrgId(userInfo.getOrgId());
        abOrgAgenda.setDeptId(deptId);
        abOrgAgenda.setNote1(note1);
        abOrgAgenda.setNote2(note2);
        abOrgAgenda.setNote3(note3);
        abOrgAgenda.setMemberId(memberId);
        /**
         * 添加随机数编号
         */
        abOrgAgenda.setAgendaId(NumberUtil.newNumberID());
        return abOrgAgendaMapper.insertSelective(abOrgAgenda);
    }

    /**
     * 查询日程的员工的日程信息
     *
     * @param sort      排序
     * @param page      当前页数
     * @param perPage   每页显示
     * @param filter    员工姓名
     * @param userInfo  登录信息
     * @param start_time 查询开始时间
     * @param end_time 查询结束时间
     * @param deptId    部门编号
     * @return
     */
    @Override
    public ResponseEntity allAgendaList(String sort, Integer page, Integer perPage, String filter, UserInfo userInfo, Long start_time, Long end_time, String deptId) {
        // 校验是否有权查询
        String sortPredicate;
        TableDTO<AbOrgAgenda> tableDTO;

        if (StringUtils.isBlank(sort)) {
            sortPredicate = "member_id asc";
        } else {
            sortPredicate = sort.split("\\|")[0] + " " + sort.split("\\|")[1];
        }
        PageHelper.startPage(page, perPage, sortPredicate);
        String pageParam = "sort=".concat(sortPredicate).concat("&page=").concat(page.toString()).concat("&per_page=").concat(perPage.toString());

        if (StringUtils.isEmpty(filter)) {
            List<AbOrgAgenda> abOrgAgendaList;
            AbOrgAgenda abOrgAgenda = new AbOrgAgenda();
            /**
             * 公司编号
             */
            abOrgAgenda.setOrgId(userInfo.getOrgId());
            /**
             * 部门编号
             */
            if(deptId!=null&&!"".equals(deptId)){
                abOrgAgenda.setDeptId(Integer.parseInt(deptId));
            }
            /**
             * 判断查询时间是否为空
             */
            if(start_time!=null&&end_time!=null&&!"".equals(start_time)&&!"".equals(end_time)){
                abOrgAgenda.setStartDate(DateUtils.longToDate(start_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(end_time));
            }
            if(start_time!=null&&!"".equals(start_time)&&end_time == null){
                abOrgAgenda.setStartDate(DateUtils.longToDate(start_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(start_time));
            }
            if(end_time!=null&&!"".equals(end_time)&&start_time == null ){
                abOrgAgenda.setStartDate(DateUtils.longToDate(end_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(end_time));
            }

            abOrgAgendaList = abOrgAgendaMapper.abOrgAgendaList(abOrgAgenda);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgendaList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo, pageParam);
        } else {
            List<AbOrgAgenda> abOrgAgendaList;
            AbOrgAgenda abOrgAgenda = new AbOrgAgenda();
            /**
             * 公司编号
             */
            abOrgAgenda.setOrgId(userInfo.getOrgId());
            /**
             * 部门编号
             */
            if(deptId!=null&&!"".equals(deptId)){
                abOrgAgenda.setDeptId(Integer.parseInt(deptId));
            }
            /**
             * 判断查询时间是否为空
             */
            if(start_time!=null&&end_time!=null&&!"".equals(start_time)&&!"".equals(end_time)){
                abOrgAgenda.setStartDate(DateUtils.longToDate(start_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(end_time));
            }
            if(start_time!=null&&!"".equals(start_time)&&end_time == null){
                abOrgAgenda.setStartDate(DateUtils.longToDate(start_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(start_time));
            }
            if(end_time!=null&&!"".equals(end_time)&&start_time == null ){
                abOrgAgenda.setStartDate(DateUtils.longToDate(end_time));
                abOrgAgenda.setEndDate(DateUtils.longToDate(end_time));
            }
            abOrgAgenda.setMemberName(filter);
            abOrgAgendaList = abOrgAgendaMapper.abOrgAgendaList(abOrgAgenda);
            PageInfo<AbOrgAgenda> pageInfo = new PageInfo<>(abOrgAgendaList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo, pageParam);
        }
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     * 根据日程编号删除日程
     * 注：单记录删除
     *
     * @param userInfo
     * @param Id
     * @return
     */
    @Override
    public int deleteOrgAgendaByOne(UserInfo userInfo, String Id) {
        return abOrgAgendaMapper.deleteOrgAgendaByOne(Id);
    }

    /**
     * 批量删除公司日程信息
     *
     * @param userInfo
     * @param Id
     * @return
     */
    @Override
    @Transactional
    public int deleteOrgAgendaByOnes(UserInfo userInfo, String Id) {
        int status = 0;
        String[] arr = Id.split("-");
        for (int i = 0;i<arr.length;i++){
            abOrgAgendaMapper.deleteOrgAgendaByOne(arr[i]);
            status ++;
        }
        return status;
    }

    /**
     * 根据用户编号查询用户的日程信息
     *
     * @param info
     * @param memberId
     * @return
     */
    @Override
    public ResponseEntity selectByMemberIdAndDate(UserInfo info, Integer memberId) {
        TableDTO<ScheduleList> tableDTO;
        List<ScheduleList> allDept  = abOrgAgendaMapper.selectByMemberIdAndDate(info.getOrgId());
        PageInfo<ScheduleList> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public int deleteOrgAgenda(String[] ids) {
        int i = 0;
        List<String> delList = new ArrayList<String>();
        for (String str : ids) {
            delList.add(str);
        }
        if(abOrgAgendaMapper.deleteOrgAgenda(delList)>0){
            i++;
        }
        return i;
    }

}
