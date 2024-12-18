package com.backend.pro.service.apt;

import com.backend.pro.model.entity.med.apt.Scale;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author l
* @description 针对表【appts_scale(评估结果表)】的数据库操作Service
* @createDate 2024-12-17 11:58:49
*/
public interface ScaleService extends IService<Scale> {

    boolean addApppointmentScale(Scale appointmentScale);

}
