package com.csxx.service.webOrg.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.csxx.bo.webOrg.JoinCom;
import com.csxx.bo.webOrg.OwnCom;
import com.csxx.config.webOrg.WebOrgConfig;
import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.converter.org.LabelValue2AbMemberDetail;
import com.csxx.dao.webOrg.AbMember;
import com.csxx.dao.webOrg.AbMemberDetail;
import com.csxx.dao.webOrg.AbOrg;
import com.csxx.dto.webOrg.form.CreateComForm;
import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.dto.webOrg.form.LabelValue;
import com.csxx.dto.webOrg.form.UserArchiveForm;
import com.csxx.enums.common.ResultEnum;
import com.csxx.exception.BizException;
import com.csxx.mapper.webOrg.AbMemberDetailMapper;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.mapper.webOrg.AbOrgMapper;
import com.csxx.service.webOrg.WebCompanyService;
import com.csxx.utils.AnimalUtils;
import com.csxx.utils.DateUtils;
import com.csxx.vo.common.TableDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WebCompanyServiceImpl implements WebCompanyService {

    @Autowired
    private WebOrgConfig webOrgConfig;
    @Autowired
    private AbOrgMapper abOrgMapper;
    @Autowired
    private AbMemberMapper abMemberMapper;
    @Autowired
    private AbMemberDetailMapper abMemberDetailMapper;
    /**
     * 获取已经加入的公司数量
     * @param username 用户名
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int getJoinComCount(String username) {
        return abOrgMapper.selectJoinComCountByUsername(username);
    }
    /**
     * 获取没有加入的公司的清单
     * @param user
     * @param sort
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TableDTO getOtherComList(String user, String sort, Integer page, Integer perPage, String filter) {
        String sortPredicate;
        TableDTO<AbOrg> tableDTO;
        if (StringUtils.isBlank(sort)) {
            sortPredicate = "org_id asc";
        } else {
            sortPredicate = sort.split("\\|")[0] + " " + sort.split("\\|")[1];
        }
        PageHelper.startPage(page, perPage, sortPredicate);
        String pageParam = "sort=".concat(sort).concat("&per_page=").concat(perPage.toString());
        if (StringUtils.isNotBlank(filter)) {
            List<AbOrg> otherComList = abOrgMapper.selectOtherComByUserFilter(user, new StringBuilder().append("%").append(filter).append("%").toString());
            PageInfo<AbOrg> pageInfo = new PageInfo<>(otherComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getOtherComPage().concat(pageParam).concat("&filter=").concat(filter));
        } else {
            List<AbOrg> otherComList = abOrgMapper.selectOtherComByUserFilter(user, null);
            PageInfo<AbOrg> pageInfo = new PageInfo<>(otherComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getOtherComPage().concat(pageParam));
        }
        return tableDTO;
    }
    /**
     * 获取加入的公司
     * @param user 用户名
     * @param sort 排序
     * @param page 页码
     * @param perPage 每页数量
     * @param filter 过滤条件
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TableDTO getJoinComList(String user, String sort, Integer page, Integer perPage, String filter) {
        String sortPredicate;
        TableDTO<JoinCom> tableDTO;
        if (StringUtils.isBlank(sort)) {
            sortPredicate = "org_id asc";
        } else {
            sortPredicate = sort.split("\\|")[0] + " " + sort.split("\\|")[1];
        }
        PageHelper.startPage(page, perPage, sortPredicate);
        String pageParam = "sort=".concat(sort).concat("&per_page=").concat(perPage.toString());
        if (StringUtils.isNotBlank(filter)) {
            List<JoinCom> joinComList = abOrgMapper.selectJoinComByUserFilter(user, new StringBuilder().append("%").append(filter).append("%").toString());
            PageInfo<JoinCom> pageInfo = new PageInfo<>(joinComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getJoinComPage().concat(pageParam).concat("&filter=").concat(filter));
        } else {
            List<JoinCom> joinComList = abOrgMapper.selectJoinComByUserFilter(user, null);
            PageInfo<JoinCom> pageInfo = new PageInfo<>(joinComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getJoinComPage().concat(pageParam));
        }
        return tableDTO;
    }
    @Override
    @Transactional(readOnly = true)
    public int getOwnComCount(String username) {
        return abOrgMapper.selectOwnComCountByUsername(username);
    }
    /**
     * 获取已经创建的公司
     * @param user 用户名
     * @param sort 排序
     * @param page 页码
     * @param perPage 每页数量
     * @param filter 过滤条件
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TableDTO getOwnComList(String user, String sort, Integer page, Integer perPage, String filter) {
        String sortPredicate;
        TableDTO<OwnCom> tableDTO;
        if (StringUtils.isBlank(sort)) {
            sortPredicate = "org_id asc";
        } else {
            sortPredicate = sort.split("\\|")[0] + " " + sort.split("\\|")[1];
        }
        PageHelper.startPage(page, perPage, sortPredicate);
        String pageParam = "sort=".concat(sort).concat("&per_page=").concat(perPage.toString());
        if (StringUtils.isNotBlank(filter)) {
            List<OwnCom> ownComList = abOrgMapper.selectOwnComByUserFilter(user, new StringBuilder().append("%").append(filter).append("%").toString());
            PageInfo<OwnCom> pageInfo = new PageInfo<>(ownComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getOwnComPage().concat(pageParam).concat("&filter=").concat(filter));
        } else {
            List<OwnCom> ownComList = abOrgMapper.selectOwnComByUserFilter(user, null);
            PageInfo<OwnCom> pageInfo = new PageInfo<>(ownComList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webOrgConfig.getOwnComPage().concat(pageParam));
        }
        return tableDTO;
    }
    /**
     * 校验公司是否存在
     * @param value 校验值
     * @param type 校验类型
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validRepeat(String value, String type) {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(type)) {
            throw new BizException(ResultEnum.REQ_FMT_ERR);
        }
        boolean result = false;
        /**
         * 校验公司
         */
        if (type.equals("orgName")) {
            if (abOrgMapper.selectCountByOrgName(value) == 0) {
                result = true;
            }
        }
        return result;
    }
    /**
     * 创建公司
     * @param user
     * @param createComForm
     */
    @Override
    @Transactional
    public void createCom(String user, CreateComForm createComForm) {
        /**
         * 新建公司
         * create company
         */
        AbOrg abOrg = new AbOrg();
        BeanUtils.copyProperties(createComForm.getOrgForm(), abOrg);
        abOrg.setCreateDatetime(new Date());
        abOrgMapper.insertSelective(abOrg);
        /**
         * 保存公司创始人信息
         * save message of creator
         */
        AbMember abMember = new AbMember();
        BeanUtils.copyProperties(createComForm.getArchiveForm(), abMember);
        abMember.setOrgId(abOrg.getOrgId());
        abMember.setOnenetOwner(user);
        abMember.setMemberStatus(new Byte("1"));
        abMember.setApplicateDate(new Date());
        abMember.setEntryDate(new Date());
        abMemberMapper.insertSelective(abMember);
        // 创建员工明细
        List<AbMemberDetail> abMemberDetailList = new ArrayList<>();
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                createComForm.getArchiveForm().getAddressList(), "address", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                createComForm.getArchiveForm().getEmailList(), "email", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                createComForm.getArchiveForm().getTelList(), "tel", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                createComForm.getArchiveForm().getOtherList(), "other", abMember.getMemberId()));
        abMemberDetailMapper.batchInsert(abMemberDetailList);
    }

    /**
     * 加入公司
     * @param user
     * @param jionComForm
     */
    @Override
    @Transactional
    public void joinCom(String user, JoinComForm jionComForm) {
        /**
         * 新建个人的记录信息
         */
        AbMember abMember = new AbMember();
        /**
         * 属性复制
         */
        BeanUtils.copyProperties(jionComForm, abMember);
        abMember.setOnenetOwner(user);
        /**
         * 转换时间格式化 yyyy-MM-dd
         */
        abMember.setBirthday(DateUtils.forMatter(jionComForm.getBirthday()));
        abMember.setIdCardExp(DateUtils.forMatter(jionComForm.getIdCardExp()));
        /**
         * 通过选择生日获取生肖
         */
        Date a = DateUtils.forMatter(jionComForm.getBirthday());
        String date = AnimalUtils.formateDate(a);
        Byte animal = Byte.valueOf(AnimalUtils.getAnimal(date));
        abMember.setAnimal(animal);

        abMember.setAnimal(jionComForm.getSybolicAnimal());
        abMember.setMemberStatus(new Byte("1"));
        abMember.setApplicateDate(new Date());
        abMember.setEntryDate(new Date());



        abMember.setAnimal(jionComForm.getSybolicAnimal());
        abMember.setRoleId(2);
        abMember.setMemberStatus(new Byte("1"));
        abMember.setApplicateDate(new Date());
        abMember.setEntryDate(new Date());
        abMemberMapper.insertSelective(abMember);
        /**
         * 添加用户后添加用户详细信息
         */
        List<AbMemberDetail> abMemberDetailList = new ArrayList<>();
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getAddressList(), "address", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getEmailList(), "email", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getTelList(), "tel", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getOtherList(), "other", abMember.getMemberId()));
        abMemberDetailMapper.batchInsert(abMemberDetailList);
    }





    /**
     * 创建公司
     * @param  user 关联登录账号
     * @param userInfo 创建人信息
     * @param orgInfo  创建公司信息
     */
    @Override
    @Transactional
    public void createCom(String user,String userInfo, String orgInfo) {
        /**
         * Java对象转换
         * 直接转成javabean对象无需属性复制
         */
        AbOrg abOrg = JSON.parseObject(orgInfo,new TypeReference<AbOrg>() {});
        //AbOrg abOrg = JSON.parseObject(orgInfo,AbOrg.class);
        /**
         * 添加公司信息
         */
        abOrg.setCreateDatetime(new Date());
        abOrgMapper.insertSelective(abOrg);
        /**
         * 保存创建人信息
         */
        AbMember abMember = new AbMember();
        UserArchiveForm archiveForm = JSON.parseObject(userInfo,UserArchiveForm.class);
        archiveForm.setTelList(archiveForm.getPhoneList());
        BeanUtils.copyProperties(archiveForm, abMember);
        /**
         * 生肖
         */
        String date = AnimalUtils.formateDate(archiveForm.getBirthday());
        Byte animal = Byte.valueOf(AnimalUtils.getAnimal(date));
        abMember.setAnimal(animal);

        abMember.setPostId(1002);//赋予公司职位：公司创始人
        abMember.setDeptId(3013);//部门信息：总裁办
        abMember.setOrgId(abOrg.getOrgId());
        abMember.setOnenetOwner(user);
        abMember.setMemberStatus(new Byte("1"));
        abMember.setApplicateDate(new Date());
        abMember.setEntryDate(new Date());
        abMemberMapper.insertSelective(abMember);
        /**
         * 保存用户详细信息
         */
        List<AbMemberDetail> abMemberDetailList = new ArrayList<>();
        List<LabelValue> address = archiveForm.getAddressList();
        List<LabelValue> email = archiveForm.getEmailList();
        List<LabelValue> tel = archiveForm.getTelList();
        List<LabelValue> other = archiveForm.getOtherList();
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                address, "address", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                email, "email", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                tel, "tel", abMember.getMemberId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                other, "other", abMember.getMemberId()));
        abMemberDetailMapper.batchInsert(abMemberDetailList);
    }
}
