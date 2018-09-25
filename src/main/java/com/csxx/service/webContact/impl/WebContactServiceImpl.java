package com.csxx.service.webContact.impl;

import com.csxx.bo.contact.*;
import com.csxx.config.contact.WebContactConfig;
import com.csxx.converter.common.PageInfo2TableDTO;
import com.csxx.converter.contact.*;
import com.csxx.dao.contact.*;
import com.csxx.mapper.contact.TelAddressListMapper;
import com.csxx.mapper.contact.TelDetailListMapper;
import com.csxx.mapper.contact.TelUserListMapper;
import com.csxx.repository.contact.*;
import com.csxx.vo.common.TableDTO;
import com.csxx.dto.contact.*;
import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.exception.BizException;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.service.webContact.WebContactService;
import com.csxx.utils.ObjectMatcher;
import com.csxx.utils.ObjectMixer;
import com.csxx.utils.ObjectUtil;
import com.csxx.utils.StrUtil;
import com.csxx.vo.webContact.CategoryVO;
import com.csxx.vo.webContact.DeleteList;
import com.csxx.vo.webContact.MergeResult;
import com.csxx.vo.webContact.SearchTreeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WebContactServiceImpl implements WebContactService {

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;
    @Autowired
    private TelUserListRepository telUserListRepository;
    @Autowired
    private TelDetailListRepository telDetailListRepository;
    @Autowired
    private TelAddressListRepository telAddressListRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private TelLabelListRepository telLabelListRepository;

    @Autowired
    private TelUserListMapper telUserListMapper;
    @Autowired
    private TelDetailListMapper telDetailListMapper;
    @Autowired
    private TelAddressListMapper telAddressListMapper;

    @Autowired
    private WebContactConfig webContactConfig;

    /**
     * 通讯人一览表
     * @param owner
     * @param page
     * @param perPage
     * @param sort
     * @param filter
     * @return
     */
    @Override
    @Transactional
    public TableDTO findListByOwner(String owner,
                                    Integer page,
                                    Integer perPage,
                                    String sort,
                                    String filter) {
        TableDTO tableDTO;
        List<Integer> ids = new ArrayList<>();
        PageInfo<?> pageInfo;
        List<WebContactLine> webContactLineList = new ArrayList<>();
        List<WebContactAllInOne> webContactAllInOneList = new ArrayList<>();

        if (StringUtils.isEmpty(filter)) {
            if (StringUtils.isNotEmpty(sort)) {
                PageHelper.startPage(page, perPage,
                        sort.split("\\|")[0].concat(" ").concat(sort.split("\\|")[1]));
            } else {
                PageHelper.startPage(page, perPage, "id asc");
            }
            webContactLineList = telUserListMapper.findContactLineByOwner(owner, null);
            pageInfo = new PageInfo<>(webContactLineList);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webContactConfig.getWebContactPage().concat("owner=" + owner + "&sort=" + sort));
            if (webContactLineList.size() > 0) {
                ids = webContactLineList.stream().map(e -> e.getId()).collect(Collectors.toList());
                webContactAllInOneList = telDetailListMapper.findAllInOne(StrUtil.convertIntListToStr(ids, ","));
            }
        } else {
            if (StringUtils.isNotEmpty(sort)) {
                PageHelper.startPage(page, perPage,
                        sort.split("\\|")[0].concat(" ").concat(sort.split("\\|")[1]));
            } else {
                PageHelper.startPage(page, perPage, "id asc");
            }
            List<IdAndName> findResult = telUserListMapper.findUserIdByOwnerAndFilterAndStatus(owner, filter, null);
            if (findResult != null) {
                ids = findResult
                        .stream().map(e -> e.getId()).collect(Collectors.toList());
            }
            pageInfo = new PageInfo<>(findResult);
            tableDTO = PageInfo2TableDTO.convert(pageInfo,
                    webContactConfig.getWebContactPage().concat("owner=" + owner + "&sort=" + sort + "&filter=" + filter));
            if (ids.size() > 0) {
                webContactLineList = telUserListMapper.findContactLineByOwner(owner, StrUtil.convertIntListToStr(ids, ","));
                webContactAllInOneList = telDetailListMapper.findAllInOne(StrUtil.convertIntListToStr(ids, ","));
            }
        }

        for (WebContactLine webContactLine : webContactLineList) {
            List<WebContactThreeAttr<String>> mobileList = new ArrayList<>();
            List<WebContactThreeAttr<String>> emailList = new ArrayList<>();
            List<WebContactThreeAttr<String>> urlList = new ArrayList<>();
            List<WebContactThreeAttr<String>> addressList = new ArrayList<>();

            Integer id = webContactLine.getId();
            Optional<WebContactAllInOne> findResult = webContactAllInOneList.stream().filter(e -> Objects.equals(e.getUserId(), id)).findFirst();
            if (findResult.isPresent()) {
                WebContactAllInOne allInOne = findResult.get();

                // 处理电话
                if (StringUtils.isNotEmpty(allInOne.getMobiles())) {
                    List<String> list = Arrays.asList(allInOne.getMobiles().split(",", -1));
                    List2Object.convertToThreeAttr(list, mobileList);
                    if (mobileList.size() > 0) {
                        String phoneCode = mobileList.get(0).getRemark();
                        if (StringUtils.isNotEmpty(phoneCode)) {
                            webContactLine.setPhone(phoneCode + mobileList.get(0).getContent());
                        } else {
                            webContactLine.setPhone(mobileList.get(0).getContent());
                        }
                    }
                    webContactLine.setPhoneList(mobileList);
                }

                // 处理电子邮件
                if (StringUtils.isNotEmpty(allInOne.getEmails())) {
                    List<String> list = Arrays.asList(allInOne.getEmails().split(",", -1));
                    List2Object.convertToThreeAttr(list, emailList);
                    if (emailList.size() > 0) {
                            webContactLine.setMail(emailList.get(0).getContent());
                    }
                    webContactLine.setMailList(emailList);
                }

                // 处理URL
                if (StringUtils.isNotEmpty(allInOne.getUrls())) {
                    List<String> list = Arrays.asList(allInOne.getUrls().split(",", -1));
                    List2Object.convertToThreeAttr(list, urlList);
                    if (urlList.size() > 0) {
                        webContactLine.setUrl(urlList.get(0).getContent());
                    }
                    webContactLine.setUrlList(urlList);
                }

                // 处理地址
                if (StringUtils.isNotEmpty(allInOne.getAddresses())) {
                    List<String> list = Arrays.asList(allInOne.getAddresses().split(",", -1));
                    List2Object.convertToThreeAttr(list, addressList);
                    if (addressList.size() > 0) {
                        webContactLine.setAddress(addressList.get(0).getContent());
                    }
                    webContactLine.setAddressList(addressList);
                }
            }
        }
        tableDTO.setData(webContactLineList);
        return tableDTO;
    }

    /**
     * 通讯人明细
     * @param owner
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public WebContactDetailDTO findOne(String owner, Integer userId) {

        // 检查通讯人是否存在
        Integer exist = telUserListMapper.existsByOwnerAndUserId(owner, userId);
        if (exist == null) {
            throw new BizException(ResultEnum.USER_NOT_EXIST);
        }
        // 获取用户基本信息
        WebContactDetailDTO contactDetailDTO = telUserListMapper.findByUserId(userId);

        // 获取用户复杂字段信息
        List<WebContactCategory> webContactCategoryList = telDetailListMapper.findDetailByUserId(userId);

        // 获取手机列表
        List<WebContactThreeAttr<String>> phoneList = CategoryList2ThreeAttrList.convertToStrList(
                webContactCategoryList, "mobile"
        );
        if (phoneList != null && phoneList.size() > 0) {
            contactDetailDTO.setPhoneList(phoneList);
        }

        // 获取电子邮箱列表
        List<WebContactThreeAttr<String>> emailList = CategoryList2ThreeAttrList.convertToStrList(
                webContactCategoryList, "email"
        );
        if (emailList != null && emailList.size() > 0) {
            contactDetailDTO.setEmailList(emailList);
        }

        // 获取URL列表
        List<WebContactThreeAttr<String>> urlList = CategoryList2ThreeAttrList.convertToStrList(
                webContactCategoryList, "urlAddress"
        );
        if (urlList != null && urlList.size() > 0) {
            contactDetailDTO.setUrlList(urlList);
        }

        // 获取地址列表
        // 获取地址id列表
        List<Integer> addressIdList = webContactCategoryList.stream().filter(e ->
            e.getCategory().equals("address")
        ).map(e -> e.getAddressId()).collect(Collectors.toList());
        if (addressIdList.size() > 0) {
            List<WebContactCatAndAddress> result = telAddressListMapper.selectByAddressId(addressIdList);
            List<WebContactThreeAttr<WebContactAddress>> addressList =
                    CategoryList2ThreeAttrList.convertToWebContactAddressList(result);
            if (addressList != null && addressList.size() > 0) {
                contactDetailDTO.setAddressList(addressList);
            }
        }

        return contactDetailDTO;
    }

    public void baseSaveMethod(TelDetailList telDetailList,
                               TelUserList telUserList) {
        telDetailList.setId(null);
        telUserList.addDetail(telDetailList);
    }

    /**
     * 转换已知的中文标签为对应分类的英文标签
     * @param labelSet
     * @param category
     * @param cnLabel
     * @return
     */
    /*private String getEnLabel(Map<String, List<TelLabelList>> labelSet, String category, String cnLabel) {
        String enLabel;
        Optional<TelLabelList> findLabel = labelSet.get(category).stream()
                .filter(e -> e.getCn().equals(cnLabel))
                .findFirst();
        if (findLabel.isPresent()) {
            enLabel = findLabel.get().getEn();
        } else {
            enLabel = cnLabel;
        }

        return enLabel;
    }*/

    /**
     * 保存通讯人
     * @param owner
     * @param userId
     * @param dto
     */
    @Override
    @Transactional
    public void save(String owner, Integer userId, WebContactDetailDTO dto) {

        TelUserList telUserList;

        boolean isNew;
        if (userId != null) {
            isNew = false;
            // 检查通讯人是否存在
            Integer exist = telUserListMapper.existsByOwnerAndUserId(owner, userId);
            if (exist == null) {
                throw new BizException(ResultEnum.USER_NOT_EXIST);
            }
        } else {
            isNew = true;
        }


        if (!isNew) {
//            telUserList = telUserListRepository.findAllById(userId);
            // 删掉已有的电话、电子邮件、URL数据
            telDetailListRepository.deleteByUserIdAndCategoryIn(
                    userId, Arrays.asList("mobile", "email", "urlAddress")
            );
            telDetailListRepository.flush();
        }/* else {
            telUserList = new TelUserList();
            telUserList.setTelOwnerList(telOwnerListRepository.findByUserName(owner));
        }*/
        telUserList = new TelUserList();
        telUserList.setTelOwnerList(telOwnerListRepository.findByUserName(owner));

        BeanUtils.copyProperties(dto, telUserList);
        telUserList.setOrganizationName(dto.getCompany());
        telUserList.setDepartmentName(dto.getDepartment());
        telUserList.setJobTitle(dto.getJob());

        // 获取分类信息
        /*Map<String, List<TelLabelList>> labelSet = telLabelListRepository.findAll().stream()
                .collect(Collectors.groupingBy(TelLabelList::getCategory));*/

        // 保存电话
        for (WebContactThreeAttr<String> item : dto.getPhoneList()) {
            TelDetailList telDetailList = WebContactThreeAttr2TelDetailList.convertStr(item, "mobile");
//            telDetailList.setLabel(getEnLabel(labelSet, "mobile", telDetailList.getLabel()));
            try {
                ObjectUtil.convertObjectStrAttrToUrlEncode(telDetailList, "utf-8");
            } catch (Exception e) {
                log.info("【电话明细URL编码失败】：{}", ReflectionToStringBuilder.toString(telDetailList, ToStringStyle.MULTI_LINE_STYLE));
            }
            baseSaveMethod(telDetailList, telUserList);
        }

        // 保存电子邮件
        for (WebContactThreeAttr<String> item : dto.getEmailList()) {
            TelDetailList telDetailList = WebContactThreeAttr2TelDetailList.convertStr(item, "email");
//            telDetailList.setLabel(getEnLabel(labelSet, "email", telDetailList.getLabel()));
            try {
                ObjectUtil.convertObjectStrAttrToUrlEncode(telDetailList, "utf-8");
            } catch (Exception e) {
                log.info("【电子邮件明细URL编码失败】：{}", ReflectionToStringBuilder.toString(telDetailList, ToStringStyle.MULTI_LINE_STYLE));
            }
            baseSaveMethod(telDetailList, telUserList);
        }

        // 保存URL
        for (WebContactThreeAttr<String> item : dto.getUrlList()) {
            TelDetailList telDetailList = WebContactThreeAttr2TelDetailList.convertStr(item, "urlAddress");
//            telDetailList.setLabel(getEnLabel(labelSet, "urlAddress", telDetailList.getLabel()));
            try {
                ObjectUtil.convertObjectStrAttrToUrlEncode(telDetailList, "utf-8");
            } catch (Exception e) {
                log.info("【网址明细URL编码失败】：{}", ReflectionToStringBuilder.toString(telDetailList, ToStringStyle.MULTI_LINE_STYLE));
            }
            baseSaveMethod(telDetailList, telUserList);
        }

        // 保存地址
        if (!isNew) {
            // 删除已有的地址
            List<Integer> ids = telDetailListMapper.findByCategoryAndUserId("address", telUserList.getId());
            if (ids.size() > 0) {
                telAddressListRepository.deleteByIdIn(ids);
            }
            telDetailListRepository.deleteByUserIdAndCategoryIn(telUserList.getId(), Arrays.asList("address"));
        }
        for (WebContactThreeAttr<WebContactAddress> item : dto.getAddressList()) {
            TelDetailList telDetailList = new TelDetailList();
            TelAddressList telAddressList = new TelAddressList();
            telDetailList.setCategory("address");
            if (StringUtils.isNotEmpty(item.getCustomLabel())) {
                telDetailList.setLabel(item.getCustomLabel());
            } else {
                telDetailList.setLabel(item.getLabel());
            }
//            telDetailList.setLabel(getEnLabel(labelSet, "address", telDetailList.getLabel()));
            try {
                ObjectUtil.convertObjectStrAttrToUrlEncode(telDetailList, "utf-8");
            } catch (Exception e) {
                log.info("【地址明细URL编码失败】：{}", ReflectionToStringBuilder.toString(telDetailList, ToStringStyle.MULTI_LINE_STYLE));
            }

            // 保存地址
            BeanUtils.copyProperties(item.getContent(), telAddressList);
            telAddressList.setIsoCountryCode(item.getContent().getCountryCode());
            telAddressList.setSubAdministrativeArea(item.getContent().getAdmin());
            telAddressList.setSubLocality(item.getContent().getLocality());
            telAddressList.setPostalCode(item.getContent().getPostcode());
            try {
                ObjectUtil.convertObjectStrAttrToUrlEncode(telAddressList, "utf-8");
            } catch (Exception e) {
                log.info("【地址URL编码失败】：{}", ReflectionToStringBuilder.toString(telAddressList, ToStringStyle.MULTI_LINE_STYLE));
            }
            telDetailList.setTelAddressList(telAddressList);

            telUserList.addDetail(telDetailList);
        }
        telUserListRepository.save(telUserList);
    }

    /**
     * 获取国家列表，手机、电子邮件、网址、地址标签列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryVO getCategoryVO() {
        CategoryVO categoryVO = new CategoryVO();

        // 获取国家信息
        categoryVO.setRegionList(regionRepository.findAll());
        // 获取标签信息
        List<TelLabelList> telLabelLists = telLabelListRepository.findAll();
        categoryVO.setPhoneLabelList(telLabelLists.stream().filter(e -> e.getCategory().equals("mobile")).collect(Collectors.toList()));
        categoryVO.setEmailLabelList(telLabelLists.stream().filter(e -> e.getCategory().equals("email")).collect(Collectors.toList()));
        categoryVO.setUrlLabelList(telLabelLists.stream().filter(e -> e.getCategory().equals("urlAddress")).collect(Collectors.toList()));
        categoryVO.setAddressLabelList(telLabelLists.stream().filter(e -> e.getCategory().equals("address")).collect(Collectors.toList()));

        return categoryVO;
    }

    /**
     * 获取某个用户用于展示在搜索树上的通讯人列表
     * @param owner
     * @param filter 如果传入了filter参数则根据姓名、邮箱、电话过滤指定的通讯人
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SearchTreeVO> getSearchTree(String owner, String filter) {

        List<SearchTreeVO> searchTreeVOList;

        // 获取全部
        if (StringUtils.isEmpty(filter)) {
            searchTreeVOList = telUserListMapper.findAllByOwner(owner);
        }
        // 根据搜索条件找到姓名、电话、邮箱包含指定关键字的数据
        else {
            searchTreeVOList = telUserListMapper.findAllByOwnerAndFilter(owner, "%" + filter + "%");
        }

        return searchTreeVOList;

    }

    /**
     * 获取某个用户的下一个通讯人
     * @param owner
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getNextUserId(String owner, Integer id) {
        return telUserListMapper.findNextIdByOwnerAndId(owner, id);
    }

    /**
     * 恢复删除
     * @param owner
     * @param ids
     */
    @Override
    @Transactional
    public void markNormal(String owner, List<Integer> ids) {
        if (ids.size() > 0) {
            telUserListRepository.updateStatusByIdIn(ids, 0, null, owner);
        }
    }

    /**
     * 移到回收站，标记删除
     * @param owner
     * @param ids
     */
    @Override
    @Transactional
    public void markDelete(String owner, List<Integer> ids) {
        if (ids.size() > 0) {
            telUserListRepository.updateStatusByIdIn(ids, 1, new Date(), owner);
        }
    }

    /**
     * 永久删除
     * @param owner
     * @param ids
     */
    @Override
    @Transactional
    public void delete(String owner, List<Integer> ids) {
        if (!(ids.size() > 0)) {
            return;
        }

        // 判断是否有不属于当前用户的通讯人
        if (telUserListMapper.findNotBelongOwner(owner, StrUtil.convertIntListToStr(ids, ",")) > 0) {
            throw new BizException(ResultEnum.ILLEGAL_OPERATION);
        }

        // 必须在回收站中且找到的结果数大于0才可进行删除操作，否则判为非法操作
        Integer result = telUserListMapper.
                getUserStatus(StrUtil.convertIntListToStr(ids, ","));
        if ((Optional.ofNullable(result).orElse(new Integer(0))) > 0) {
            log.error("【删除通讯人】非法操作，涉及的通讯人id：{}",
                    StrUtil.convertIntListToStr(ids, ","));
            throw new BizException(ResultEnum.ILLEGAL_OPERATION);
        }

        // 地址ID
        List<Integer> addressIds = new ArrayList<>();
        // 明细ID
        List<Integer> detailIds = new ArrayList<>();

        // 根据通讯人ID找出相关的明细和地址ID
        List<Object[]> objects = telUserListRepository.findRelatedIdByUserIdIn(ids);

        if (objects.size() > 0) {
            // 找出地址
            List<Object[]> complexList = objects.stream().filter(e -> e[1] != null).collect(Collectors.toList());
            if (complexList.size() > 0) {
                addressIds.addAll(complexList.stream().map(e -> (Integer)(e[1])).collect(Collectors.toList()));
                // 执行删除
                if (addressIds.size() > 0) {
                    telAddressListRepository.deleteByIdIn(addressIds);
                }
            }

            // 找出明细
            detailIds.addAll(objects.stream().map(e -> (Integer)(e[0])).collect(Collectors.toList()));
            // 执行删除
            if (detailIds.size() > 0) {
                telDetailListRepository.nativeDeleteById(detailIds);
            }
        }

        // 删除通讯人
        telUserListRepository.nativeDeleteById(ids);
    }

    /**
     * 删除所有回收站中的记录
     * @param owner
     */
    @Override
    @Transactional
    public void deleteAll(String owner) {
        List<Integer> ids = telUserListMapper.findAllDelIdByOwner(owner);
        if (ids != null) {
            delete(owner, ids);
        }
    }

    /**
     * 获取用户已删除通讯人的列表
     * @param owner
     * @param page
     * @param perPage
     * @param filter
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TableDTO<DeleteList> getDelList(String owner, Integer page, Integer perPage, String filter) {

        TableDTO tableDTO = new TableDTO();
        List<DeleteList> deleteLists = new ArrayList<>();
        PageInfo<DeleteList> pageInfo;
        List<Integer> ids = new ArrayList<>();

        if (StringUtils.isEmpty(filter)) {
            PageHelper.startPage(page, perPage, "id asc");
            deleteLists = telUserListMapper.findDelListByOwner(owner, null);
            pageInfo = new PageInfo<>(deleteLists);
            tableDTO = PageInfo2TableDTO.convert(pageInfo, webContactConfig.getWebContactDelPage().concat("owner=" + owner));
        } else {
            PageHelper.startPage(page, perPage, "id asc");
            List<IdAndName> findResult = telUserListMapper.findUserIdByOwnerAndFilterAndStatus(owner, filter, 1);
            if (findResult != null) {
                ids = findResult
                        .stream().map(e -> e.getId()).collect(Collectors.toList());
            }
            if (ids.size() > 0) {
                deleteLists = telUserListMapper.findDelListByOwner(owner,
                        StrUtil.convertIntListToStr(ids, ","));
                pageInfo = new PageInfo<>(deleteLists);
                tableDTO = PageInfo2TableDTO.convert(pageInfo, webContactConfig.getWebContactDelPage().concat("owner=" + owner + "&filter=" + filter));
            }
        }

        tableDTO.setData(deleteLists);

        return tableDTO;

    }

    /**
     * 合并通讯人
     * @param owner
     */
    @Override
    @Transactional
    public MergeResult getMerge(String owner) {

        MergeResult mergeResult = new MergeResult();

        // 1、自动合并
        int autoMergeCounter = 0;
        List<AutoMergePO> autoMergeResult = new ArrayList<>();
        List<AutoMergePO> autoMergePOList = telUserListMapper.getAutoMergeByOwner(owner);
        if (autoMergePOList.size() > 0) {
            List<String> nameList = autoMergePOList.stream().map(e ->
                    e.getLastName() + e.getFirstName())
                    .collect(Collectors.toList())
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());

            for (String name: nameList) {
                List<AutoMergePO> group = autoMergePOList.stream().filter(e ->
                        (e.getLastName() + e.getFirstName()).equals(name))
                        .collect(Collectors.toList());
                AutoMergePO firstPO = new AutoMergePO();
                int repeatCounter = 0;
                boolean canBeMerged = true;
                for (int i = 0; i < group.size(); i++) {
                    AutoMergePO autoMergePO = group.get(i);
                    if (i == 0) {
                        firstPO = autoMergePO;
                    } else {
                        if (ObjectMatcher.matchAutoMergePO(firstPO, autoMergePO)) {
                            repeatCounter++;
                            ObjectMixer.mixAutoMergePO(firstPO, autoMergePO);
                        } else {
                            canBeMerged = false;
                            break;
                        }
                    }
                }
                if (canBeMerged && repeatCounter > 0) {
                    // 删除被自动合并的记录
                    String ids = StrUtil.convertIntListToStr(group.stream()
                            .map(e -> e.getUserId()).collect(Collectors.toList()), ",");
                    telAddressListMapper.deleteByAutoMergeIds(ids);
                    telDetailListMapper.deleteByAutoMergeIds(ids);
                    telUserListMapper.deleteByIds(ids);

                    // 记录已经合并了多少个联系人
                    autoMergeCounter += group.size();

                    // 新增自动合并记录到数据库中
                    TelOwnerList telOwnerList = telOwnerListRepository.findByUserName(owner);
                    TelUserList telUserList = AutoMergePO2TelUserList.convert(firstPO);
                    telUserList.setTelOwnerList(telOwnerList);
                    telUserListRepository.save(telUserList);

                    // 将当前组合并完毕后的联系人追加到返回到前端的集合中
                    autoMergeResult.add(firstPO);
                }
            }
        }

        // 2、手工合并
        List<AutoMergePO> manualMergeList = telUserListMapper.getManualMergeByOwnerAndIds(owner, null);
        List<List<AutoMergePO>> finalMergeList = new ArrayList<>();

        Iterator<AutoMergePO> iterator;

        while (true) {
            List<AutoMergePO> tempPOList = new ArrayList<>();
            iterator = manualMergeList.iterator();

            AutoMergePO autoMergePO = iterator.next();
            String name = autoMergePO.getLastName() + autoMergePO.getFirstName();
            List<String> mobileGrp;
            if (StringUtils.isEmpty(autoMergePO.getMobiles())) {
                mobileGrp = new ArrayList<>();
            } else {
                mobileGrp = Arrays.stream(autoMergePO.getMobiles().split(",", -1))
                        .map(e -> e.split(";", -1)[1])
                        .collect(Collectors.toList());
            }
            tempPOList.add(autoMergePO);
            iterator.remove();
            while (iterator.hasNext()) {
                AutoMergePO innerPO = iterator.next();
                String innerName = innerPO.getLastName() + innerPO.getFirstName();
                List<String> innerMobileGrp;
                if (StringUtils.isEmpty(innerPO.getMobiles())) {
                    innerMobileGrp = new ArrayList<>();
                } else {
                    innerMobileGrp = Arrays.stream(innerPO.getMobiles().split(",", -1))
                            .map(e -> e.split(";", -1)[1])
                            .collect(Collectors.toList());
                }
                if (innerName.equals(name) ||
                        ((StringUtils.isNotEmpty(autoMergePO.getMobiles()) && StringUtils.isNotEmpty(autoMergePO.getMobiles())) &&
                        !Collections.disjoint(mobileGrp, innerMobileGrp))) {
                    tempPOList.add(innerPO);
                    iterator.remove();
                }
            }
            if (tempPOList.size() > 1) {
                finalMergeList.add(tempPOList);
            }
            if (manualMergeList.size() == 0) {
                break;
            }
        }

        // 返回结果
        mergeResult.setAutoMergeNumber(autoMergeCounter);

        List<WebContactDetailDTO> autoMergeList;
        autoMergeList = AutoMergePO2WebContactDetailDTO.convertToList(autoMergeResult);
        mergeResult.setAutoMergeList(autoMergeList);

        List<List<WebContactDetailDTO>> manualMergeListVO = new ArrayList<>();
        finalMergeList.stream().forEach(e ->
                manualMergeListVO.add(AutoMergePO2WebContactDetailDTO.convertToList(e))
        );
        mergeResult.setManualMergeList(manualMergeListVO);

        return mergeResult;
    }

    /**
     * 手工合并通讯人
     */
    @Override
    @Transactional
    public void manualMerge(String owner, List<Integer> idList, WebContactDetailDTO dto) {
        // 删除被合并的通讯人
        String ids = StrUtil.convertIntListToStr(idList, ",");
        telAddressListMapper.deleteByAutoMergeIds(ids);
        telDetailListMapper.deleteByAutoMergeIds(ids);
        telUserListMapper.deleteByIds(ids);

        // 保存手工合并的通讯人
        save(owner, null, dto);
    }

    /**
     * 通过ID获取合并数据
     * @param owner
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MergeResult getMergeByIds(String owner, List<Integer> ids) {
        MergeResult mergeResult = new MergeResult();

        List<AutoMergePO> manualMergeList = telUserListMapper.
                getManualMergeByOwnerAndIds(owner, StrUtil.convertIntListToStr(ids, ","));
        List<List<AutoMergePO>> finalMergeList = new ArrayList<>();
        finalMergeList.add(manualMergeList);

        mergeResult.setAutoMergeNumber(0);
        mergeResult.setAutoMergeList(null);
        List<List<WebContactDetailDTO>> manualMergeListVO = new ArrayList<>();
        finalMergeList.stream().forEach(e ->
                manualMergeListVO.add(AutoMergePO2WebContactDetailDTO.convertToList(e))
        );
        mergeResult.setManualMergeList(manualMergeListVO);

        return mergeResult;
    }

}
