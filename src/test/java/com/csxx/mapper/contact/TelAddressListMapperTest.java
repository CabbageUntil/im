package com.csxx.mapper.contact;

import com.csxx.bo.contact.WebContactCatAndAddress;
import com.csxx.Application;
import com.csxx.dao.contact.mybatisModel.TelAddressList;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelAddressListMapperTest.class)
@Import(Application.class)
public class TelAddressListMapperTest {

    @Autowired
    private TelAddressListMapper mapper;

    @Test
    @Transactional
    public void selectByAddressId() throws Exception {

        List<Integer> idList = Arrays.asList(60, 61, 62, 63);
        List<WebContactCatAndAddress> result = mapper.selectByAddressId(idList);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void updateDataToUrlEncode() {
        List<TelAddressList> telAddressListList = mapper.selectAll();
        for (TelAddressList telAddressList : telAddressListList) {
            try {
                telAddressList.setCity(URLEncoder.encode(StringUtils.defaultString(telAddressList.getCity()), "utf-8"));
                telAddressList.setIso_country_code(URLEncoder.encode(StringUtils.defaultString(telAddressList.getIso_country_code()), "utf-8"));
                telAddressList.setCountry(URLEncoder.encode(StringUtils.defaultString(telAddressList.getCountry()), "utf-8"));
                telAddressList.setLabel(URLEncoder.encode(StringUtils.defaultString(telAddressList.getLabel()), "utf-8"));
                telAddressList.setPostal_code(URLEncoder.encode(StringUtils.defaultString(telAddressList.getPostal_code()), "utf-8"));
                telAddressList.setState(URLEncoder.encode(StringUtils.defaultString(telAddressList.getState()), "utf-8"));
                telAddressList.setStreet(URLEncoder.encode(StringUtils.defaultString(telAddressList.getStreet()), "utf-8"));
                telAddressList.setSub_administrative_area(URLEncoder.encode(StringUtils.defaultString(telAddressList.getSub_administrative_area()), "utf-8"));
                telAddressList.setSub_locality(URLEncoder.encode(StringUtils.defaultString(telAddressList.getSub_locality()), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
        }
        for (TelAddressList telAddressList : telAddressListList) {
            mapper.updateDataToUrlEncode(telAddressList.getIso_country_code(),
                    telAddressList.getCity(),
                    telAddressList.getCountry(),
                    telAddressList.getLabel(),
                    telAddressList.getPostal_code(),
                    telAddressList.getState(),
                    telAddressList.getStreet(),
                    telAddressList.getSub_administrative_area(),
                    telAddressList.getSub_locality(),
                    telAddressList.getId());
        }
    }

    @Test
//    @Transactional
    public void batchUpdate() {
        List<TelAddressList> telAddressListList = mapper.selectAll();
        for (TelAddressList telAddressList : telAddressListList) {
            try {
                telAddressList.setCity(URLEncoder.encode(StringUtils.defaultString(telAddressList.getCity()), "utf-8"));
                telAddressList.setCountry(URLEncoder.encode(StringUtils.defaultString(telAddressList.getCountry()), "utf-8"));
                telAddressList.setPostal_code(URLEncoder.encode(StringUtils.defaultString(telAddressList.getPostal_code()), "utf-8"));
                telAddressList.setState(URLEncoder.encode(StringUtils.defaultString(telAddressList.getState()), "utf-8"));
                telAddressList.setStreet(URLEncoder.encode(StringUtils.defaultString(telAddressList.getStreet()), "utf-8"));
                telAddressList.setSub_administrative_area(URLEncoder.encode(StringUtils.defaultString(telAddressList.getSub_administrative_area()), "utf-8"));
                telAddressList.setSub_locality(URLEncoder.encode(StringUtils.defaultString(telAddressList.getSub_locality()), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
        }
        for (int i = 0; i < telAddressListList.size(); i += 200) {
            if ((i + 200) >= telAddressListList.size()) {
                mapper.batchUpdate(telAddressListList.subList(i, telAddressListList.size() - 1));
            } else {
                mapper.batchUpdate(telAddressListList.subList(i, i + 200 - 1));
            }
        }
    }

}