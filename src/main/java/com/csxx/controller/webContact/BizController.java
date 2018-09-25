package com.csxx.controller.webContact;

import com.auth0.jwt.interfaces.Claim;
import com.csxx.config.contact.WebContactConfig;
import com.csxx.vo.common.TableDTO;
import com.csxx.dto.contact.WebContactDetailDTO;
import com.csxx.vo.common.ResponseEntity;
import com.csxx.enums.common.ResultEnum;
import com.csxx.service.webContact.WebContactService;
import com.csxx.utils.CookieUtil;
import com.csxx.utils.JWTUtil;
import com.csxx.utils.ResponseEntityUtil;
import com.csxx.vo.webContact.DeleteList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/im/webContacts")
@Slf4j
public class BizController {

    @Autowired
    private WebContactConfig webContactConfig;

    @Autowired
    private WebContactService webContactService;

    /**
     * 登出
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie != null) {
            response.addCookie(CookieUtil.buildCookie("token", null, 0,
                    webContactConfig.domain, "/"));
        }
        return ResponseEntityUtil.success();
    }

    // 根据令牌里的claim信息获取用户名
    private String getUsername(String token) {
        Map<String, Claim> claimMap = JWTUtil.getClaim(token);
        if (claimMap != null) {
            return claimMap.get("username").asString();
        } else {
            return null;
        }
    }

    /**
     * 通讯人一览表
     * @param page
     * @param per_page
     * @param field
     * @param filter
     * @return
     */
    @GetMapping("/api/list")
    public TableDTO list(HttpServletRequest request,
                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "per_page", defaultValue = "10") Integer per_page,
                         @RequestParam(value = "sort", defaultValue = "id|asc") String field,
                         @RequestParam(value = "filter", required = false) String filter) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return webContactService.findListByOwner(owner, page, per_page, field, filter);
        } else {
            return null;
        }
    }

    /**
     * 通讯人明细（编辑页）
     * @param id
     * @return
     */
    @GetMapping("/api/detail")
    public ResponseEntity edit(HttpServletRequest request,
                       @RequestParam("id") Integer id) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return ResponseEntityUtil.success(webContactService.findOne(owner, id));
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 保存、更新通讯人资料
     * @param userId
     * @param data
     * @return
     */
    @PostMapping("/api/save")
    public ResponseEntity save(HttpServletRequest request,
                               @RequestParam(value = "userId", required = false) Integer userId,
                               @RequestParam("data") String data) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            Gson gson = new Gson();
            WebContactDetailDTO result = gson.fromJson(data, new TypeToken<WebContactDetailDTO>(){}.getType());
            webContactService.save(owner, userId, result);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }

    }

    /**
     * 通讯人搜索树
     * @param filter
     * @return
     */
    @GetMapping("/api/tree")
    public ResponseEntity tree(HttpServletRequest request,
                               @RequestParam(value = "filter", required = false) String filter) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return ResponseEntityUtil.success(webContactService.getSearchTree(owner, filter));
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 获取国家、标签通用信息
     * @return
     */
    @GetMapping("/api/get_category")
    public ResponseEntity getCategory() {
        return ResponseEntityUtil.success(webContactService.getCategoryVO());
    }

    /**
     * 获取下一个通讯人的ID（用作编辑页面头部的【保存并编辑下一个】）
     * @param id
     * @return
     */
    @GetMapping("/api/nextUserId")
    public ResponseEntity nextUserId(HttpServletRequest request,
                                     @RequestParam("id") Integer id) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return ResponseEntityUtil.success(webContactService.getNextUserId(owner, id));
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 从回收站恢复
     * @param ids
     * @return
     */
    @PostMapping("/api/mark_normal")
    public Object markNormal(HttpServletRequest request,
                             @RequestParam("recoverIds[]") List<Integer> ids) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            webContactService.markNormal(owner, ids);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 移至回收站
     * @param ids
     * @return
     */
    @DeleteMapping("/api/mark_delete/{deleteIds[]}")
    public Object markDelete(HttpServletRequest request,
                             @PathVariable("deleteIds[]") List<Integer> ids) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            webContactService.markDelete(owner, ids);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 从回收站中删除（永久删除）
     * @param ids
     * @return
     */
    @DeleteMapping("/api/delete/{deleteIds[]}")
    public Object delete(HttpServletRequest request,
                         @PathVariable("deleteIds[]") List<Integer> ids) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            webContactService.delete(owner, ids);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 删除回收站中的全部内容
     * @param request
     * @return
     */
    @DeleteMapping("/api/delete_all")
    public ResponseEntity deleteAll(HttpServletRequest request) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            webContactService.deleteAll(owner);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 回收站一览表
     * @param page
     * @param per_page
     * @param filter
     * @return
     */
    @GetMapping("/api/get_delete_list")
    public TableDTO<DeleteList> getDeleteList(HttpServletRequest request,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "per_page", defaultValue = "10") Integer per_page,
                                              @RequestParam(value = "filter", required = false) String filter) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return webContactService.getDelList(owner, page, per_page, filter);
        } else {
            return null;
        }
    }

    /**
     * 合并通讯人（请求合并时执行）
     * @return
     */
    @GetMapping("/api/merge")
    public ResponseEntity merge(HttpServletRequest request) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return ResponseEntityUtil.success(webContactService.getMerge(owner));
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 手工合并通讯人（手工合并时点击确认合并执行）
     * @param ids
     * @param dto
     * @return
     */
    @PostMapping("/api/manual_merge")
    public ResponseEntity manualMerge(HttpServletRequest request,
                                      @RequestParam("mergeIds[]") List<Integer> ids,
                                      @RequestParam("data") String dto) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            Gson gson = new Gson();
            WebContactDetailDTO result = gson.fromJson(dto, new TypeToken<WebContactDetailDTO>() {
            }.getType());
            webContactService.manualMerge(owner, ids, result);
            return ResponseEntityUtil.success();
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

    /**
     * 在预览表格中手工选择合并
     * @param ids
     * @return
     */
    @GetMapping("/api/select_merge/{owner}/{selectedIds[]}")
    public ResponseEntity selectMerge(HttpServletRequest request,
                                      @PathVariable("selectedIds[]") List<Integer> ids) {
        String owner = getUsername(request.getHeader("token"));
        if (owner != null) {
            return ResponseEntityUtil.success(webContactService.getMergeByIds(owner, ids));
        } else {
            return ResponseEntityUtil.error(ResultEnum.AUTH_FAILED.getCode(), ResultEnum.AUTH_FAILED.getTransMsg());
        }
    }

}
