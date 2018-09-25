package com.csxx.service.webContact;

import com.csxx.vo.common.TableDTO;
import com.csxx.dto.contact.WebContactDetailDTO;
import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.vo.webContact.CategoryVO;
import com.csxx.vo.webContact.DeleteList;
import com.csxx.vo.webContact.MergeResult;
import com.csxx.vo.webContact.SearchTreeVO;

import java.util.List;

public interface WebContactService {

    TableDTO findListByOwner(String owner, Integer page, Integer perPage, String sort, String filter);

    WebContactDetailDTO findOne(String owner, Integer userId);

    void save(String owner, Integer userId, WebContactDetailDTO dto);

    CategoryVO getCategoryVO();

    List<SearchTreeVO> getSearchTree(String owner, String filter);

    Integer getNextUserId(String owner, Integer id);

    void markNormal(String owner, List<Integer> ids);

    void markDelete(String owner, List<Integer> ids);

    void delete(String owner, List<Integer> ids);

    void deleteAll(String owner);

    TableDTO<DeleteList> getDelList(String owner, Integer page, Integer perPage, String filter);

    MergeResult getMerge(String owner);

    void manualMerge(String owner, List<Integer> idList, WebContactDetailDTO dto);

    MergeResult getMergeByIds(String owner, List<Integer> ids);

}
