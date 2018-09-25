package com.csxx.service.webOrg;


import com.csxx.vo.common.ResponseEntity;
import com.csxx.vo.common.TableDTO;
import com.csxx.vo.webOrg.UserInfo;

public interface WebDeptmentServic {
    /**
     * 更改部门的状态
     * @param dept_id
     * @param userInfo
     * @return
     */
    int RemoveDeptMent(Integer dept_id,UserInfo userInfo);

    /**
     * 新建部门
     * @param
     * @return
     */
    int addDeptMent(String deptName,Integer parentId,UserInfo userInfo);

    /**
     * 查询所有部门的信息
     * @param sort
     * @param page
     * @param per_page
     * @param filter 部门名称
     * @return
     */
    ResponseEntity apartmentList(String sort, Integer page, Integer per_page, UserInfo userInfo, String filter,Integer type);

    /**
     * 根据编号查询部门的结构图
     * @param
     * @return
     */
    TableDTO  getDepMent(String name ,Integer id);

    /**
     *
     * @param userInfo
     * @return
     */
    ResponseEntity selectList(UserInfo userInfo);

}
