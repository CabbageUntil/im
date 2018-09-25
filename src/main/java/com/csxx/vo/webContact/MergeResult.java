package com.csxx.vo.webContact;

import com.csxx.dto.contact.WebContactDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class MergeResult {

    /**
     * 合并个数
     */
    private Integer autoMergeNumber;

    /**
     * 自动合并后通讯人列表
     */
    private List<WebContactDetailDTO> autoMergeList;

    /**
     * 手工合并后通讯人组列表
     */
    private List<List<WebContactDetailDTO>> manualMergeList;

}
