package com.csxx.service.webOrg;

import com.csxx.dto.webOrg.form.JoinComForm;
import com.csxx.vo.common.TableDTO;
import com.csxx.dto.webOrg.form.CreateComForm;

public interface WebCompanyService {

    /**
     * 获取已加入公司数
     * @param username 用户名
     * @return
     */
    int getJoinComCount(String username);

    /**
     * 查询已加入的公司
     * @param user 用户名
     * @param sort 排序
     * @param page 页码
     * @param perPage 每页数量
     * @param filter 过滤条件
     * @return
     */
    TableDTO getJoinComList(String user, String sort, Integer page, Integer perPage, String filter);

    /**
     * 查询未加入的公司的名称
     * @param user
     * @param sort
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    TableDTO getOtherComList(String user, String sort, Integer page, Integer perPage, String filter);
    /**
     * 获取已拥有公司数
     * @param username 用户名
     * @return
     */
    int getOwnComCount(String username);

    /**
     * 查询已拥有的公司
     * @param user 用户名
     * @param sort 排序
     * @param page 页码
     * @param perPage 每页数量
     * @param filter 过滤条件
     * @return
     */
    TableDTO getOwnComList(String user, String sort, Integer page, Integer perPage, String filter);

    /**
     * 校验重复
     * @param value 校验值
     * @param type 校验类型
     * @return
     */
    boolean validRepeat(String value, String type);

    /**
     * 创建公司
     * @param user
     * @param createComForm
     */
    void createCom(String user, CreateComForm createComForm);

    /**
     * 加入公司
     * @param user
     * @param jionComForm
     */
    void joinCom(String user, JoinComForm jionComForm);

    /**
     *  添加公司
     * @param userInfo
     * @param orgInfo
     */
    void createCom(String user,String userInfo,String orgInfo);
}
