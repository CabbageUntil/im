package com.csxx.converter.common;

import com.csxx.vo.common.TableDTO;
import com.github.pagehelper.PageInfo;

public class PageInfo2TableDTO {

    public static TableDTO convert(PageInfo pageInfo, String url) {

        TableDTO tableDTO = new TableDTO();
        tableDTO.setTotal(pageInfo.getTotal());
        tableDTO.setPer_page(pageInfo.getPageSize());
        tableDTO.setCurrent_page(pageInfo.getPageNum());
        tableDTO.setLast_page(pageInfo.getPages());
        if (pageInfo.isHasPreviousPage()) {
            tableDTO.setPrev_page_url(url + "&page=" + pageInfo.getPrePage());
        }
        if (pageInfo.isHasNextPage()) {
            tableDTO.setNext_page_url(url + "&page=" + pageInfo.getNextPage());
        }
        tableDTO.setFrom(new Integer(pageInfo.getStartRow()).longValue());
        tableDTO.setTo(new Integer(pageInfo.getEndRow()).longValue());
        //2018年7月27日12:34:30
        tableDTO.setData(pageInfo.getList());
        return tableDTO;
    }

}
