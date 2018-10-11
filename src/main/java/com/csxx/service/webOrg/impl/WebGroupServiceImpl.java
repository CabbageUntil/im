package com.csxx.service.webOrg.impl;

import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.dao.webOrg.AbGroup;
import com.csxx.dao.webOrg.AbGroupAndMember;
import com.csxx.dao.webOrg.AbGroupMember;
import com.csxx.mapper.webOrg.AbGroupAndMemberMapper;
import com.csxx.mapper.webOrg.AbGroupMapper;
import com.csxx.mapper.webOrg.AbMemberGroupMapper;
import com.csxx.service.webOrg.WebGroupService;
import com.csxx.utils.NumberUtil;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.utils.deptList;
import com.csxx.utils.groupList;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.UserInfo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WebGroupServiceImpl implements WebGroupService {
    @Autowired
    private AbGroupAndMemberMapper abGroupAndMemberMapper;
    @Autowired
    private AbGroupMapper abGroupMapper;
    @Autowired
    private AbMemberGroupMapper abMemberGroupMapper;

    @Override
    @Transactional
    public void createGroup(UserInfo userInfo, String groupName,String userName) {
        String groupMemberId = NumberUtil.newNumberID();
        String groupId  = NumberUtil.newNumberID();
        //群组和成员关系
        AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
        abGroupAndMember.setGroupId(groupId);
        abGroupAndMember.setGroupMemberId(groupMemberId);
        abGroupAndMemberMapper.insert(abGroupAndMember);

        //群组信息保存
        AbGroup abGroup = new AbGroup();
        abGroup.setName(groupName);
        abGroup.setGroupId(groupId);
        abGroup.setStatus((byte)1);
        abGroup.setCreateDate(new Date());
        abGroupMapper.insertSelective(abGroup);

        //成员信息保存
        AbGroupMember abGroupMember  = new AbGroupMember();
        abGroupMember.setGroupMemberId(groupMemberId);
        abGroupMember.setName(userName);
        //状态0：已经退出 1：审核中  2：通过审核
        abGroupMember.setStatus((byte) 1);
        abGroupMember.setApplicateDate(new Date() );
        abGroupMember.setGroupRole((byte)2);
        abGroupMember.setMebile(userInfo.getUsername());
        abMemberGroupMapper.insertSelective(abGroupMember);
    }

    /**
     * 查询加入的群组的信息
     * @param userInfo
     * @return
     */
    @Override
    public ResponseEntity selectGroupList(UserInfo userInfo) {
        System.out.println("电话号码信息："+userInfo.getUsername());
        TableDTO<groupList> tableDTO;
        List<groupList> allDept  = abGroupMapper.selectGroupList(userInfo.getUsername());
        PageInfo<groupList> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }
}
