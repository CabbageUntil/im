package com.csxx.service.unifiedLogin.impl;

import com.csxx.config.unifiedLogin.UnifiedLoginConfig;
import com.csxx.bo.unifiedLogin.ValidResponseEntity;
import com.csxx.dao.contact.TelOwnerList;
import com.csxx.repository.contact.TelOwnerListRepository;
import com.csxx.service.unifiedLogin.UnifiedLoginService;
import com.csxx.utils.MD5Util;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UnifiedLoginServiceImpl implements UnifiedLoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UnifiedLoginConfig unifiedLoginConfig;

    @Autowired
    private TelOwnerListRepository telOwnerListRepository;


    /**
     * 调用外部统一登录API
     * @param jwtToken JWT-TOKEN令牌字符串
     * @return 登录请求的返回结果对象
     */
    @Override
    public ValidResponseEntity unifiedLogin(String jwtToken) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("jwt-token", jwtToken);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        postParameters.add("sign", MD5Util.getMD5(unifiedLoginConfig.getRequestTokenSign().concat(dateFormat.format(new Date()))).toLowerCase());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type",
                "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
        String data = restTemplate.postForObject(unifiedLoginConfig.getTokenValidPage(), request, String.class);

        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        ValidResponseEntity responseEntity = gson.fromJson(data, ValidResponseEntity.class);
        if (responseEntity.getIsOk() == 1) {
            //保存用户手机号码
            initUser(responseEntity.getData().getMobile());
        }
        return responseEntity;
    }

    /**
     * 用户数据初始化
     * @param owner
     */
    @Override
    @Transactional
    public void initUser(String owner) {
        if (!telOwnerListRepository.existsByUserName(owner)) {
            TelOwnerList telOwnerList = new TelOwnerList();
            telOwnerList.setUserName(owner);
            telOwnerList.setSyncTime(new Date());
            telOwnerListRepository.save(telOwnerList);
        }
    }


}
