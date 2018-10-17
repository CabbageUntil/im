package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbGroup;
import com.csxx.utils.groupList;
import com.csxx.vo.webOrg.GroupMemberInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbGroupMapper {
    /**
     * 根据群编号删除群的信息
     * @param groupId
     * @return
     */
    int deleteByPrimaryKey(String groupId);

    /**
     * 添加所有字段的数据(没有添加则保存null)
     * @param abGroup
     * @return
     */
    int insert(AbGroup abGroup);

    /**
     * 选择字段添加数据
     * @param abGroup
     * @return
     */
    int insertSelective(AbGroup abGroup);
    /**
     * 根据群编号查询群信息
     * @param groupId
     * @return
     */
    AbGroup selectByPrimaryKey(String groupId);
    /**
     * 根据群组编号更新群组信息
     * @param abGroup
     * @return
     */
    int updateByPrimaryKeySelective(AbGroup abGroup);

    /**
     * 选择新更新群组信息
     * @param abGroup
     * @return
     */
    int updateByPrimaryKey(AbGroup abGroup);


    /**
     * 实现群组的下拉查询
     * @param mebile
     * @return
     */
    List<groupList> selectGroupList(@Param(value = "aa") String mebile);
    /**
     * 查询已经添加的群组的信息
     */
    List<AbGroup> joinGroup(@Param(value = "aa") String mebile);

    /**
     * 查询已经添加的群组的信息
     */
    List<AbGroup> cresatGroupList(@Param(value = "aa") String mebile);

    /**
     * 登录时查找成员信息(姓名，群名，角色编号)
     * 参数：手机号和群号
     * @return
     */
    GroupMemberInfo selectGroupMemberByMebileAndGroupId(@Param(value="groupId") String groupId,
                                                        @Param(value="mebile") String mebile);

    /**
     * 查询已进入的群组数
     * @param mebile
     * @return
     */
    int getJionGroupCount(@Param(value="num") String mebile);
    /**
     * 查询创建的群组数
     * @param mebile
     * @return
     */
    int getCreateGroupCount(@Param(value="num") String mebile);
}