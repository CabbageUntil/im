package com.csxx.service.webOrg.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.csxx.config.webOrg.WebOrgConfig;
import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.converter.org.LabelValue2AbMemberDetail;
import com.csxx.dao.webOrg.AbMember;
import com.csxx.dao.webOrg.AbMemberDetail;
import com.csxx.dao.webOrg.AbOrg;
import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.dto.webOrg.form.LabelValue;
import com.csxx.dto.webOrg.form.UserArchiveForm;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.MemberStatusEnum;
import com.csxx.enums.webOrg.RoleEnum;
import com.csxx.mapper.webOrg.AbMemberDetailMapper;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.repository.webOrg.MapRepository;
import com.csxx.service.webOrg.WebMemberService;
import com.csxx.utils.AnimalUtils;
import com.csxx.utils.DateUtils;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.utils.memList;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.Archive;
import com.csxx.vo.webOrg.MemberInfoVO;
import com.csxx.vo.webOrg.MemberListDetail;
import com.csxx.vo.webOrg.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebMemberServiceImpl implements WebMemberService{

    private final AbMemberMapper abMemberMapper;
    private final AbMemberDetailMapper abMemberDetailMapper;
    private final MapRepository mapRepository;
    private final WebOrgConfig webOrgConfig;

    @Autowired
    public WebMemberServiceImpl(AbMemberMapper abMemberMapper, AbMemberDetailMapper abMemberDetailMapper, MapRepository mapRepository, WebOrgConfig webOrgConfig) {
        this.abMemberMapper = abMemberMapper;
        this.abMemberDetailMapper = abMemberDetailMapper;
        this.mapRepository = mapRepository;
        this.webOrgConfig = webOrgConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity memberList(String sort, Integer page, Integer perPage, String filter, UserInfo userInfo, Integer type) {
        // 校验是否有权查询
        AbMember abMember = abMemberMapper.selectByPrimaryKey(userInfo.getMemberId());
        if (!abMember.getRoleId().equals(RoleEnum.CREATOR.getCode())) {
            return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非创建者无权查询");
        } else if (abMember.getMemberStatus().intValue() != MemberStatusEnum.FORMAL.getCode()) {
            return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非正式员工无权查询");
        }

        String sortPredicate;
        TableDTO<AbMember> tableDTO;

        if (StringUtils.isBlank(sort)) {
            sortPredicate = "member_id asc";
        } else {
            sortPredicate = sort.split("\\|")[0] + " " + sort.split("\\|")[1];
        }
        PageHelper.startPage(page, perPage, sortPredicate);
        String pageParam = "sort=".concat(sortPredicate).concat("&per_page=").concat(perPage.toString());

        if (StringUtils.isEmpty(filter)) {
            List<MemberInfoVO> memberInfoVOList;
            if( type == 3){
                memberInfoVOList = abMemberMapper.fetchMemberListByFilter(userInfo.getOrgId(), null);
            }else{
                memberInfoVOList = abMemberMapper.fetchMemberListByFilterAndStatus(userInfo.getOrgId(), null,type);
            }
            PageInfo<MemberInfoVO> pageInfo = new PageInfo<>(memberInfoVOList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo, pageParam);
        } else {
            List<MemberInfoVO> memberInfoVOList;
            if( type == 3){
                memberInfoVOList = abMemberMapper.fetchMemberListByFilter(userInfo.getOrgId(), filter);
                PageInfo<MemberInfoVO> pageInfo = new PageInfo<>(memberInfoVOList);
                pageParam.concat("filter=").concat(filter);
                tableDTO = PageInfo2TableDTO.convert(pageInfo, pageParam);
            }else{
                memberInfoVOList = abMemberMapper.fetchMemberListByFilterAndStatus(userInfo.getOrgId(), filter, type);
                PageInfo<MemberInfoVO> pageInfo = new PageInfo<>(memberInfoVOList);
                pageParam.concat("filter=").concat(filter);
                tableDTO = PageInfo2TableDTO.convert(pageInfo, pageParam);
            }
        }
        return ResponseEntityUtil.success(tableDTO);
    }

    @Override
    public ResponseEntity memberListDetail(UserInfo userInfo, Integer memberId) {
        AbMember currentMember = abMemberMapper.selectByPrimaryKey(userInfo.getMemberId());
        // 非正式员工不能查询
        if (currentMember.getMemberStatus().intValue() != MemberStatusEnum.FORMAL.getCode()) {
            return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非正式员工不能查询");
        }
        AbMember findMember = abMemberMapper.selectByPrimaryKey(memberId);
        if (findMember != null) {
            // 不能跨公司查询
            if (!findMember.getOrgId().equals(currentMember.getOrgId())) {
                return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "不能跨公司查询");
                // 非创建者不能查询非正式员工的信息
            } else if (findMember.getMemberStatus().intValue() != MemberStatusEnum.FORMAL.getCode() &&
                    !currentMember.getRoleId().equals(RoleEnum.CREATOR.getCode())) {
                return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非创建者不能查询非正式员工的信息");
            }
            MemberListDetail memberListDetail = abMemberDetailMapper.findMemberDetailInfoById(memberId);
            return ResponseEntityUtil.success(memberListDetail);
        } else {
            return ResponseEntityUtil.success();
        }
    }

    @Override
    public TableDTO findDetailMe(Integer memberId){
        TableDTO<AbMember> tabl;
        List<AbMember> list= new ArrayList<AbMember>();
        list.add(abMemberMapper.selectByMemberId(memberId));
        PageInfo<AbMember> pageInfo = new PageInfo<>(list);
        tabl = PageInfo2TableDTO.convert(pageInfo,null);
        return tabl;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer,String> fetchArchiveGeneral(String username) {
        return mapRepository.findAllCompanyByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Archive fetchSpecArchive(String username, Integer orgId) {
        return abMemberMapper.findArchiveByUsernameAndOrgId(username, orgId);
    }

    /**
     * 改变员工状态
     * 1:在职员工
     * 2:离职员工
     * 0:待审核员工
     * (1)修改员工状态
     * (2)员工离职时间
     * @param memberId 成员编号
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int updateMemberStatus(Integer memberId) {
        AbMember abMember = new AbMember();
        abMember.setMemberId(memberId);
        abMember.setMemberStatus((byte) 2);
        abMember.setLeaveDate(new Date());
        return abMemberMapper.RemoveMember(abMember);
    }

    /**
     * 根据部门编号查询员工的信息
     * @param userInfo
     * @param deptId
     * @return
     */
    @Override
    public ResponseEntity memSelectList(UserInfo userInfo, Integer deptId) {
        AbMember abMember  =  new AbMember();
        /**
         * 获取管理员用户的公司编号
         */
        abMember.setOrgId(userInfo.getOrgId());
        /**
         * 查询的部门编号
         */
        abMember.setDeptId(deptId);
        TableDTO<memList> tableDTO;
        List<memList> allDept  = abMemberMapper.selectByOrgIdAndDeptId(abMember);
        PageInfo<memList> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     * 查询员工详细信息
     * @param info
     * @param memberId 用户编号
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Archive findMemberByMemberId(UserInfo info, Integer memberId) {
        Integer orgId  = info.getOrgId();
        return abMemberMapper.findArchiveByUsernameAndMemberId(memberId,orgId);
    }

    /**
     * 保存信息的实现
     * 1.属性复制得到用户基本信息 abMember
     * 2.获取用户的id编号，同时将对应编号对应的详细信息删除
     * 3.重新添加用户的详细信息
     *
     * @param user
     * @param jionComForm
     */
    @Override
    @Transactional
    public void saveMember(String user, JoinComForm jionComForm) {
        AbMember abMember = new AbMember();
        BeanUtils.copyProperties(jionComForm, abMember);
        /**
         * 转换时间格式化 yyyy-MM-dd
         */
        //添加用户编号
        abMember.setMemberId(jionComForm.getId());
        abMember.setBirthday(DateUtils.forMatter(jionComForm.getBirthday()));
        abMember.setIdCardExp(DateUtils.forMatter(jionComForm.getIdCardExp()));
        /**
         * 通过选择生日获取生肖
         */
        Date a = DateUtils.forMatter(jionComForm.getBirthday());
        String date = AnimalUtils.formateDate(a);
        Byte animal = Byte.valueOf(AnimalUtils.getAnimal(date));
        abMember.setAnimal(animal);

        /**
         * 清空用户对应的详细信息
         */
        abMemberDetailMapper.deleteByMemberId(jionComForm.getId());
        abMember.setUpdateDatetime(new Date());
        //int i = 1;
        abMemberMapper.updateByPrimaryKeySelective(abMember);
        /**
         * 添加用户后添加用户详细信息
         */
        List<AbMemberDetail> abMemberDetailList = new ArrayList<>();
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getAddressList(), "address", jionComForm.getId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getEmailList(), "email", jionComForm.getId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getTelList(), "tel", jionComForm.getId()));
        abMemberDetailList.addAll(LabelValue2AbMemberDetail.convertToList(
                jionComForm.getOtherList(), "other", jionComForm.getId()));
        abMemberDetailMapper.batchInsert(abMemberDetailList);
    }

}
