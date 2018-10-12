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

    /**
     * 创建分组的信息
     * @param userInfo
     * @param groupName
     * @param userName
     */
    @Override
    @Transactional
    public void createGroup(UserInfo userInfo, String groupName,String userName) {
        //查看之前是否已经注册用户了
        AbGroupMember abGroupMember1 = abMemberGroupMapper.getMemberId(userInfo.getUsername());
        if(abGroupMember1!=null&&!"".equals(abGroupMember1)){
            String groupId  = NumberUtil.newNumberID();
            //群组和成员关系
            AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
            abGroupAndMember.setGroupId(groupId);
            abGroupAndMember.setGroupMemberId(abGroupMember1.getGroupMemberId());
            //创建群不需要审核
            abGroupAndMember.setMemberStatus((byte)1);
            //添加成员角色
            abGroupAndMember.setMemberRole((byte)2);
            abGroupAndMemberMapper.insert(abGroupAndMember);

            //群组信息保存
            AbGroup abGroup = new AbGroup();
            abGroup.setName(groupName);
            abGroup.setGroupId(groupId);
            abGroup.setStatus((byte)1);
            abGroup.setCreateDate(new Date());
            abGroupMapper.insertSelective(abGroup);
        }else{
            String groupMemberId = NumberUtil.newNumberID();
            String groupId  = NumberUtil.newNumberID();
            //群组和成员关系
            AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
            abGroupAndMember.setGroupId(groupId);
            abGroupAndMember.setGroupMemberId(groupMemberId);
            //1表示群成员需要审核
            abGroupAndMember.setMemberStatus((byte)1);
            //添加成员角色
            abGroupAndMember.setMemberRole((byte)2);
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
            abGroupMember.setApplicateDate(new Date() );
            abGroupMember.setMebile(userInfo.getUsername());
            abMemberGroupMapper.insertSelective(abGroupMember);
        }
    }

    /**
     * 查询加入的群组的信息
     * @param userInfo
     * @return
     */
    @Override
    public ResponseEntity selectGroupList(UserInfo userInfo) {
        TableDTO<groupList> tableDTO;
        List<groupList> allDept  = abGroupMapper.selectGroupList(userInfo.getUsername());
        PageInfo<groupList> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     * 加入分组的功能
     *
     * @param userInfo
     * @param groupId
     */
    @Override
    public void joinGroup(UserInfo userInfo, String groupId,String userName) {
        //查询是否已经拥有群成员的信息
        AbGroupMember abGroupMember1 = abMemberGroupMapper.getMemberId(userInfo.getUsername());
        if(abGroupMember1!=null&&!"".equals(abGroupMember1)){
            //如果已经存在记录
            AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
            abGroupAndMember.setGroupId(groupId);
            abGroupAndMember.setGroupMemberId(abGroupMember1.getGroupMemberId());
            //添加的群员需要审核
            abGroupAndMember.setMemberStatus((byte)0);
            //添加成员角色
            abGroupAndMember.setMemberRole((byte)0);
            abGroupAndMemberMapper.insert(abGroupAndMember);
        }else{
            //获取随机成员编号
            String groupMemberId = NumberUtil.newNumberID();
            //保存关系信息
            AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
            abGroupAndMember.setGroupId(groupId);
            abGroupAndMember.setGroupMemberId(groupMemberId);
            //添加的群员需要审核
            abGroupAndMember.setMemberStatus((byte)0);
            //添加成员角色
            abGroupAndMember.setMemberRole((byte)0);
            abGroupAndMemberMapper.insert(abGroupAndMember);

            //成员信息保存
            AbGroupMember abGroupMember  = new AbGroupMember();
            abGroupMember.setGroupMemberId(groupMemberId);
            abGroupMember.setName(userName);
            abGroupMember.setApplicateDate(new Date() );
            abGroupMember.setMebile(userInfo.getUsername());
            abMemberGroupMapper.insertSelective(abGroupMember);
        }
    }

    /**
     * 查询已经有群组信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public ResponseEntity joinGroupList(UserInfo userInfo) {
        TableDTO<AbGroup> tableDTO;
        List<AbGroup> allDept  = abGroupMapper.joinGroup(userInfo.getUsername());
        PageInfo<AbGroup> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }
    /**
     * 查询创建群组
     * @param userInfo
     * @return
     */
    @Override
    public ResponseEntity createGroupList(UserInfo userInfo) {
        TableDTO<AbGroup> tableDTO;
        List<AbGroup> allDept  = abGroupMapper.cresatGroupList(userInfo.getUsername());
        PageInfo<AbGroup> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }
}
