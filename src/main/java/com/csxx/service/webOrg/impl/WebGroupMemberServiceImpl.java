package com.csxx.service.webOrg.impl;

import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.dao.webOrg.AbGroupMember;
import com.csxx.mapper.webOrg.AbMemberGroupMapper;
import com.csxx.service.webOrg.WebGroupMemberService;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WebGroupMemberServiceImpl implements WebGroupMemberService {
    @Autowired
    private AbMemberGroupMapper abMemberGroupMapper;

    /**
     * 查询未审核通过的群组成员
     * @param userInfo
     * @return
     */
    @Override
    public ResponseEntity selectNotViewGroupList(UserInfo userInfo) {
        TableDTO<AbGroupMember> tableDTO;
        List<AbGroupMember> allDept  = abMemberGroupMapper.selectNotViewGroupList(userInfo.getGroupId());
        PageInfo<AbGroupMember> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }
    /**
     * 查询审核通过的群组成员
     * @param userInfo
     * @param page
     * @param perPage
     * @param name
     * @return
     */
    @Override
    public ResponseEntity selectViewGroupList(UserInfo userInfo,Integer page,Integer perPage,String name) {
        PageHelper.startPage(page,perPage," a.group_member_id asc");
        String dataParam  = "&page=".concat(page.toString()).concat("&per_page=").concat(perPage.toString());
        TableDTO<AbGroupMember> tableDTO;
        List<AbGroupMember> allDept  = abMemberGroupMapper.selectViewGroupList(userInfo.getGroupId(),name);
        PageInfo<AbGroupMember> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,dataParam);
        return ResponseEntityUtil.success(tableDTO);
    }
}
