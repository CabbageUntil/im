package com.csxx.service.app.impl;

import com.csxx.dao.app.DianAppList;
import com.csxx.dao.app.DianUserApp;
import com.csxx.mapper.app.DianAppListMapper;
import com.csxx.vo.app.AppDTO;
import com.csxx.enums.common.ResultEnum;
import com.csxx.enums.app.AppOpEnum;
import com.csxx.exception.BizException;
import com.csxx.dto.app.form.AppForm;
import com.csxx.dto.app.form.AppQueryForm;
import com.csxx.repository.app.DianAppListRepository;
import com.csxx.repository.app.DianUserAppRepository;
import com.csxx.service.app.AppService;
import com.csxx.utils.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private DianAppListRepository dianAppListRepository;

    @Autowired
    private DianUserAppRepository dianUserAppRepository;

    @Autowired
    private DianAppListMapper dianAppListMapper;

    @Value("${web.upload-path}")
    private String location;

    @Override
    @Transactional(readOnly = true)
    public List<AppDTO> query(AppQueryForm appQueryForm) {
        List<AppDTO> appDTOList;
        if (StringUtils.isNotEmpty(appQueryForm.getUserName())) {
            appDTOList = dianAppListMapper.findAppByUserName(appQueryForm);
        } else {
            if (StringUtils.isNotEmpty(appQueryForm.getAppName()) ||
                    StringUtils.isNotEmpty(appQueryForm.getAppType())) {
                appQueryForm.setAppName("%" + appQueryForm.getAppName() + "%");
                appDTOList = dianAppListMapper.findAppByAttr(appQueryForm);
            } else {
                appDTOList = dianAppListMapper.findAll();
            }
        }

        for (AppDTO appDTO : appDTOList) {
            appDTO.setImg(ImageUtil.img2Base64(location + appDTO.getImg()));
        }

        return appDTOList;
    }

    @Override
    @Transactional
    public void operate(AppForm appForm) {
        // 新增
        if (appForm.getOp().equals(AppOpEnum.ADD.getCode())) {
            if (dianUserAppRepository.existsByUserNameAndAppName(appForm.getUserName(), appForm.getAppName())) {
                throw new BizException(ResultEnum.DUP_APP_ERR);
            }
            DianAppList dianAppList = dianAppListRepository.findByAppName(appForm.getAppName());
            if (dianAppList == null) {
                throw new BizException(ResultEnum.APP_NOT_EXIST);
            }
            DianUserApp dianUserApp = new DianUserApp();
            dianUserApp.setDianAppList(dianAppListRepository.findByAppName(appForm.getAppName()));
            dianUserApp.setAppName(dianAppList.getAppName());
            dianUserApp.setUserName(appForm.getUserName());
            dianUserAppRepository.save(dianUserApp);
        } else {
            if (!dianUserAppRepository.existsByUserNameAndAppName(appForm.getUserName(), appForm.getAppName())) {
                throw new BizException(ResultEnum.NO_RECORD);
            }
            dianUserAppRepository.deleteByUserNameAndAppName(appForm.getUserName(), appForm.getAppName());
        }
    }
}
