package com.csxx.service.webOrg.impl;

import com.csxx.config.webOrg.WebOrgConfig;
import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.dao.webOrg.AbDept;
import com.csxx.dao.webOrg.AbMember;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.webOrg.MemberStatusEnum;
import com.csxx.enums.webOrg.RoleEnum;
import com.csxx.mapper.webOrg.AbDeptMapper;
import com.csxx.mapper.webOrg.AbMemberMapper;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.utils.deptList;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.service.webOrg.WebDeptmentServic;
import com.csxx.vo.webOrg.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WebDeptServiceImpl implements WebDeptmentServic {
    @Autowired
    private AbDeptMapper abDeptMapper;
    @Autowired
    private WebOrgConfig webOrgConfig;
    @Autowired
    private AbMemberMapper abMemberMapper;
    /**
     *
     * @return
     */
    @Override
    public int RemoveDeptMent(Integer dept_id,UserInfo userInfo) {
        AbDept abDept  =  new AbDept();
        abDept.setDeptId(dept_id);
        //进员工状态改成 0即是离职状态
        abDept.setDeptStatus("0");
        //添加更改时间
        abDept.setDeptRemoveDatetime(new Date());
        return abDeptMapper.updateByPrimaryKeySelective(abDept);
    }

    /**
     * 新建部门
     * @param deptName
     * @param deptId
     * @return
     */
    @Override
    public int addDeptMent(String deptName,Integer deptId,UserInfo userInfo) {
        AbDept abDept = new AbDept();
        /**
         * 部门名称
         */
        abDept.setDeptName(deptName);
        /**
         * 部门状态
         */
        abDept.setDeptStatus("1");
        /**
         * 公司编号
         */
        abDept.setOrgId(userInfo.getOrgId());
        /**
         * 父部门编号
         */
        abDept.setParentId(deptId);
        /**
         * 部门创建时间
         */
        abDept.setDeptCreateDatetime(new Date());

        return abDeptMapper.insertSelective(abDept);
    }

    /**
     * 查询公司内容所有部门的信息
     * @param sort
     * @param page
     * @param per_page
     * @param userInfo
     * @param filter
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity apartmentList(String sort, Integer page, Integer per_page, UserInfo userInfo, String filter,Integer type) {
        // 校验是否有权查询
        AbMember abMember = abMemberMapper.selectByPrimaryKey(userInfo.getMemberId());
        if (!abMember.getRoleId().equals(RoleEnum.CREATOR.getCode())) {
            return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非创建者无权查询");
        } else if (abMember.getMemberStatus().intValue() != MemberStatusEnum.FORMAL.getCode()) {
            return ResponseEntityUtil.error(ResultEnum.NO_PERMISSION.getCode(), "非正式员工无权查询");
        }
        String sortPeridcate;
        TableDTO<AbDept> tableDTO;
        if(StringUtils.isBlank(sort)){
            sortPeridcate = "dept_id asc";
        }else{
            sortPeridcate = sort.split("\\|")[0] + "  " +sort.split("\\|")[1];
        }
        PageHelper.startPage(page,per_page,sortPeridcate);
        String dataParam  = "sort = ".concat(sort).concat("&page=").concat(page.toString()).concat("&per_page=").concat(per_page.toString());

        if(StringUtils.isEmpty(filter)){
            List<AbDept> allDept;
            if(type == 3 ){
                allDept  = abDeptMapper.selectByNameAndOrgId(filter,userInfo.getOrgId());
            }else{
                allDept  = abDeptMapper.selectByNameAndStatus(filter,userInfo.getOrgId(),type);
            }
            PageInfo<AbDept> pageInfo = new PageInfo<>(allDept);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,dataParam);
        }else{
            List<AbDept> allDept;
            if(type == 3 ){
                allDept  = abDeptMapper.selectByNameAndOrgId(filter,userInfo.getOrgId());
            }else{
                allDept  = abDeptMapper.selectByNameAndStatus(filter,userInfo.getOrgId(),type);
            }
            PageInfo<AbDept> pageInfo = new PageInfo<>(allDept);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,dataParam);
          }
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     *
     * @param name
     * @param id
     * @return
     */
    @Override
    public TableDTO getDepMent(String name, Integer id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity selectList(UserInfo userInfo) {
        TableDTO<deptList> tableDTO;
        List<deptList> allDept  = abDeptMapper.selectDeptByOrgId(userInfo.getOrgId());
        PageInfo<deptList> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }
}
