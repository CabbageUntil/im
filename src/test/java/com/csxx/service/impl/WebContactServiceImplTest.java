package com.csxx.service.impl;

import com.csxx.vo.common.TableDTO;
import com.csxx.dto.contact.WebContactDetailDTO;
import com.csxx.Application;
import com.csxx.service.webContact.WebContactService;
import com.csxx.vo.webContact.MergeResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebContactServiceImplTest.class)
@Import(Application.class)
public class WebContactServiceImplTest {

    @Autowired
    private WebContactService webContactService;

    @Test
    public void findListByOwner() throws Exception {
        TableDTO tableDTO = webContactService.findListByOwner("98882289", 1, 88, null, null);
        Assert.assertNotNull(tableDTO);
    }

    @Test
    public void findOne() throws Exception {
        String owner = "13147593333";
        Integer id = 1093;
        WebContactDetailDTO result = webContactService.findOne(owner, id);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void save() throws Exception {
        String owner = "98882289";
        Integer userId = 1195;
        String data = "{\"id\":1195,\"avatar\":null,\"firstName\":\"小明\",\"lastName\":\"张\",\"birthday\":\"2018-06-19T16:00:00.000Z\",\"company\":\"\",\"department\":\"\",\"job\":\"\",\"note\":\"123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123123  张小明的备注 123  张小明的备注 123\",\"phoneList\":[{\"id\":7367,\"label\":\"company\",\"customLabel\":null,\"content\":\"8888888855\",\"remark\":\"\"},{\"id\":7371,\"label\":\"home\",\"customLabel\":null,\"content\":\"123456\",\"remark\":\"\"},{\"id\":7372,\"label\":\"home\",\"customLabel\":null,\"content\":\"5555555555\",\"remark\":\"\"}],\"emailList\":[{\"id\":7368,\"label\":\"自定义邮箱\",\"customLabel\":null,\"content\":\"qq.com\",\"remark\":\"\"}],\"addressList\":[{\"id\":7366,\"label\":\"home\",\"customLabel\":null,\"content\":{\"countryCode\":\"CN\",\"country\":\"中国\",\"state\":\"1\",\"city\":\"2\",\"admin\":\"3\",\"locality\":\"4\",\"street\":\"5\",\"postcode\":\"6\",\"data\":\"中国12345\"},\"remark\":\"\"},{\"id\":7369,\"label\":\"%E8%87%AA%E5%AE%9A%E4%B9%89%E5%9C%B0%E5%9D%80\",\"customLabel\":null,\"content\":{\"countryCode\":\"AF\",\"country\":\"阿富汗\",\"state\":\"1\",\"city\":\"2\",\"admin\":\"3\",\"locality\":\"4\",\"street\":\"5\",\"postcode\":\"6\",\"data\":\"阿富汗12345\"},\"remark\":\"\"}],\"urlList\":[{\"id\":7370,\"label\":\"company\",\"customLabel\":null,\"content\":\"url.com\",\"remark\":\"\"}]}";
        Gson gson = new Gson();
        WebContactDetailDTO result = gson.fromJson(data, new TypeToken<WebContactDetailDTO>(){}.getType());
        webContactService.save(owner, userId, result);
    }

    @Test
//    @Transactional
    public void delete() {
    }

    @Test
    @Transactional
    public void findListByOwnerNew() {
        TableDTO tableDTO = webContactService.findListByOwner("98882289", 1, 10, "id|asc", null);
        Assert.assertNotNull(tableDTO);
    }

    @Test
    @Transactional
    public void getMerge() throws Throwable {
        MergeResult mergeResult = webContactService.getMerge("98882289");
        Assert.assertNotNull(mergeResult);
    }

    @Test
    public void login() {
//        webContactService.login(null);
    }

    @Test
    public void getMergeByIds() {
        MergeResult result = webContactService.getMergeByIds("xxx", Arrays.asList(1220, 1221, 1224));
        Assert.assertNotEquals(0, result.getManualMergeList().size());
    }

    @Test
    @Transactional
    public void saveUserArchiveTest() {
    }

}