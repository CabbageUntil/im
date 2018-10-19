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
     */
    @Override
    @Transactional
    public void createGroup(UserInfo userInfo, String groupName) {
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

            String userName = userInfo.getName();
            //群组信息保存
            AbGroup abGroup = new AbGroup();
            if(userName!=null){
                abGroup.setGroupCreator(userName);
            }else {
                abGroup.setGroupCreator(userInfo.getUsername());
            }
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


            //通过session获取nickName
            String userName = userInfo.getName();
            //群组信息保存
            AbGroup abGroup = new AbGroup();
            if(userName!=null){
                abGroup.setGroupCreator(userName);
            }else {
                abGroup.setGroupCreator(userInfo.getUsername());
            }
            abGroup.setName(groupName);
            abGroup.setGroupId(groupId);
            abGroup.setStatus((byte)1);
            abGroup.setCreateDate(new Date());
            abGroupMapper.insertSelective(abGroup);

            //成员信息保存
            AbGroupMember abGroupMember  = new AbGroupMember();
            abGroupMember.setGroupMemberId(groupMemberId);
            if(userName!=null){
                abGroupMember.setName(userName);
            }else {
                abGroupMember.setName(userInfo.getUsername());
            }

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
    @Transactional
    public void joinGroup(UserInfo userInfo, String groupId) {
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

            //获取session中Name
            String userName = userInfo.getName();
            //成员信息保存
            AbGroupMember abGroupMember  = new AbGroupMember();
            abGroupMember.setGroupMemberId(groupMemberId);
            if(userName!=null){
                abGroupMember.setName(userName);
            }else{
                abGroupMember.setName(userInfo.getUsername());
            }
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
    @Transactional
    public ResponseEntity createGroupList(UserInfo userInfo) {
        TableDTO<AbGroup> tableDTO;
        List<AbGroup> allDept  = abGroupMapper.createGroupList(userInfo.getUsername());
        PageInfo<AbGroup> pageInfo = new PageInfo<>(allDept);
        tableDTO = PageInfo2TableDTO.convert(pageInfo,null);
        return ResponseEntityUtil.success(tableDTO);
    }

    /**
     * 审核群员信息
     * @param userInfo
     * @param groupMemberId
     * @return
     */
    @Override
    @Transactional
    public int verifyGroupMember(UserInfo userInfo, String groupMemberId) {
        AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
        abGroupAndMember.setGroupId(userInfo.getGroupId());
        abGroupAndMember.setGroupMemberId(groupMemberId);
        abGroupAndMember.setMemberRole((byte)1);
        return abGroupAndMemberMapper.verifyGroupMember(abGroupAndMember);
    }
    /**
     * 删除群组成员信息(群组长)
     * @param userInfo
     * @param groupMemberId
     * @return
     */
    @Override
    @Transactional
    public int deleteGroupMember(UserInfo userInfo, String groupMemberId) {
        //参数(成员编号,群编号)
        return abGroupAndMemberMapper.deleteByMemberIdAndGroupId(groupMemberId,userInfo.getGroupId());
    }

    /**
     * 群成员退群
     * @param userInfo
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public int leaveGroup(UserInfo userInfo,String groupId) {
        //参数(成员编号,群编号)
        return abGroupAndMemberMapper.deleteGroupMember(userInfo.getUsername(),groupId);
    }

    /**
     * 解散群组
     * 需要删除寻的信息
     * 和群员和群的对应关系
     * @param userInfo
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public int removeGroup(UserInfo userInfo, String groupId) {
        abGroupMapper.deleteByPrimaryKey(groupId);
        return abGroupAndMemberMapper.deleteByGroupId(groupId);
    }

    /**
     * 获取加入的群组的数量
     * @param userInfo
     * @return
     */
    @Override
    public int getJionGroupCount(UserInfo userInfo) {

        return abGroupMapper.getJionGroupCount(userInfo.getUsername());
    }

    /**
     * 获取创建数组数量
     * @param userInfo
     * @return
     */
    @Override
    public int getCreateGroupCount(UserInfo userInfo) {
        return abGroupMapper.getCreateGroupCount(userInfo.getUsername());
    }

    /**
     * 群组添加成员 参数说明 手机号码和姓名
     * @param userInfo
     * @param name
     * @param mobile
     */
    @Override
    public void addGroupMember(UserInfo userInfo, String name, String mobile) {
        //为成员分配账号
        String groupMemberId = NumberUtil.newNumberID();

        //建立群和成员的关系信息
        AbGroupAndMember abGroupAndMember = new AbGroupAndMember();
        //获取群的编号
        abGroupAndMember.setGroupId(userInfo.getGroupId());
        abGroupAndMember.setGroupMemberId(groupMemberId);
        //添加成员默认已经通过审核，1：通过审核  0：未通过审核
        abGroupAndMember.setMemberStatus((byte)1);
        //添加成员是默认成员的角色为群成员：1：群成员 2：群创始人
        abGroupAndMember.setMemberRole((byte)1);
        abGroupAndMemberMapper.insert(abGroupAndMember);

        //获取session中Name
        //成员信息保存
        AbGroupMember abGroupMember  = new AbGroupMember();
        abGroupMember.setGroupMemberId(groupMemberId);
        abGroupMember.setName(name);//群成员姓名
        abGroupMember.setApplicateDate(new Date() );//申请时间
        abGroupMember.setMebile(mobile);//手机后为当前手机号
        abMemberGroupMapper.insertSelective(abGroupMember);

    }

    /**
     * 根据手机号判断该成员是否已经加入群组
     * @param userInfo
     * @param mobile
     */
    @Override
    public boolean checkGroupMember(UserInfo userInfo, String mobile) {
        String groupMemberId = abMemberGroupMapper.getMemberIdByMobileAndGroupId(mobile,userInfo.getGroupId());
        if(groupMemberId!=null){
            return true;
        }else{
            return false;
        }
    }
}
