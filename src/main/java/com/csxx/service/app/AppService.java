package com.csxx.service.app;

import com.csxx.vo.app.AppDTO;
import com.csxx.dto.app.form.AppForm;
import com.csxx.dto.app.form.AppQueryForm;

import java.util.List;

public interface AppService {

    void operate(AppForm appForm);

    List<AppDTO> query(AppQueryForm appQueryForm);

}
