package com.csxx.mapper.webOrg;

import com.csxx.dao.webOrg.AbMemberDetail;
import com.csxx.vo.webOrg.MemberListDetail;

import java.util.List;

public interface AbMemberDetailMapper {
    int deleteByPrimaryKey(Integer detailId);

    int insert(AbMemberDetail record);

    int insertSelective(AbMemberDetail record);

    AbMemberDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(AbMemberDetail record);

    int updateByPrimaryKey(AbMemberDetail record);

    int batchInsert(List<AbMemberDetail> list);

    MemberListDetail findMemberDetailInfoById(Integer memberId);
}