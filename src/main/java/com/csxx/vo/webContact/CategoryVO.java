package com.csxx.vo.webContact;

import com.csxx.dao.contact.Region;
import com.csxx.dao.contact.TelLabelList;
import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    private List<Region> regionList;

    private List<TelLabelList> phoneLabelList;

    private List<TelLabelList> emailLabelList;

    private List<TelLabelList> urlLabelList;

    private List<TelLabelList> addressLabelList;

}
