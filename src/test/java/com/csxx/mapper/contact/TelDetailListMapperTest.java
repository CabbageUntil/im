package com.csxx.mapper.contact;

import com.csxx.bo.contact.WebContactAllInOne;
import com.csxx.bo.contact.WebContactThreeAttr;
import com.csxx.Application;
import com.csxx.dao.contact.mybatisModel.TelDetailList;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelDetailListMapperTest.class)
@Import(Application.class)
public class TelDetailListMapperTest {

    @Autowired
    private TelDetailListMapper mapper;

    @Test
    public void testCombine() {
        List<WebContactThreeAttr<String>> result = mapper.testCombine();

        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void updateData() {
        List<TelDetailList> telDetailLists = mapper.selectAll();
        for (TelDetailList telDetailList : telDetailLists) {
            try {
                telDetailList.setCategory(URLEncoder.encode(StringUtils.defaultString(telDetailList.getCategory()), "utf-8"));
                telDetailList.setLabel(URLEncoder.encode(StringUtils.defaultString(telDetailList.getLabel()), "utf-8"));
                telDetailList.setContent(URLEncoder.encode(StringUtils.defaultString(telDetailList.getContent()), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        for (TelDetailList telDetailList : telDetailLists) {
            mapper.updateDataToUrlEncode(telDetailList.getCategory(), telDetailList.getLabel(), telDetailList.getContent(), telDetailList.getId());
        }

        Assert.assertNotEquals(0, telDetailLists.size());
    }

    @Test
    public void updateDataToDecode() {
        List<TelDetailList> telDetailLists = mapper.selectAll();
        for (TelDetailList telDetailList : telDetailLists) {
            try {
                telDetailList.setCategory(URLDecoder.decode(StringUtils.defaultString(telDetailList.getCategory()), "utf-8"));
                telDetailList.setLabel(URLDecoder.decode(StringUtils.defaultString(telDetailList.getLabel()), "utf-8"));
                telDetailList.setContent(URLDecoder.decode(StringUtils.defaultString(telDetailList.getContent()), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        for (TelDetailList telDetailList : telDetailLists) {
            mapper.updateDataToUrlEncode(telDetailList.getCategory(), telDetailList.getLabel(), telDetailList.getContent(), telDetailList.getId());
        }

        Assert.assertNotEquals(0, telDetailLists.size());
    }

    @Test
    public void findAllInOne() {
        List<WebContactAllInOne> webContactAllInOneList = mapper.findAllInOne("611,612,613,614");
        Assert.assertNotEquals(0, webContactAllInOneList.size());
    }

    @Test
//    @Transactional
    public void batchUpdate() {
        List<TelDetailList> telDetailLists = mapper.selectAll();
        for (TelDetailList telDetailList : telDetailLists) {
            try {
                telDetailList.setLabel(URLEncoder.encode(StringUtils.defaultString(telDetailList.getLabel()), "utf-8"));
                telDetailList.setContent(URLEncoder.encode(StringUtils.defaultString(telDetailList.getContent()), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        for (int i = 0; i < telDetailLists.size(); i += 400) {
            if ((i + 400) >= telDetailLists.size()) {
                mapper.batchUpdate(telDetailLists.subList(i, telDetailLists.size() - 1));
            } else {
                mapper.batchUpdate(telDetailLists.subList(i, i + 400 - 1));
            }
        }
    }

}