package com.csxx.utils;

import com.alibaba.fastjson.JSON;
import com.csxx.bo.phoneList.ValidPhoneListData;
import com.csxx.bo.phoneList.ValidPhoneListEntity;
import com.csxx.bo.unifiedLogin.ValidResponseData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class test {
    public static  List<PhoneParentList>  findPhoneList(){
       /* RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("owner","13147593333");
        postParameters.add("secret", "0781E2E61641E0F853126A4C86A9F79C");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);

        String data = restTemplate.postForObject("http://onenet.com.tw:8088/im/contacts/api/get_user",
                request, String.class);
        System.out.println("回传信息"+data);
        //json对象转成
        ValidPhoneListEntity validPhoneListEntity = new ValidPhoneListEntity();
        ValidPhoneListEntity aa = JSONObject.parseObject(data,ValidPhoneListEntity.class);
        System.out.println("对象转成json"+ JSON.toJSONString(aa));
        System.out.println("对象"+aa);
        List<ValidPhoneListData> d = aa.getData();
        List<PhoneParentList> pLists = new ArrayList<>();
        for (ValidPhoneListData val : d){
            //声明一个父类
            PhoneParentList phoneParentList =  new PhoneParentList();
            //获取用户电话手册中姓名
            String name = val.getPrettyName();
            String[] phone = val.getPhoneList();
            //存放成员记录
            List<PhoneChildList> cList = new ArrayList<>();
            //保存说有的电话记录
            for(int i = 0; i <phone.length; i++){
                PhoneChildList phoneChildList = new PhoneChildList();
                phoneChildList.setLabel(phone[i]);
                phoneChildList.setValue(phone[i]);
                cList.add(phoneChildList);
            }
            phoneParentList.setChildren(cList);
            phoneParentList.setLabel(name);
            phoneParentList.setValue(name);
        }*/
        return  null;
    }
    public static void main(String[] args)  {
        System.out.println("Hello Wo");
        findPhoneList();
    }
}
