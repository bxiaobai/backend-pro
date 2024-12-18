package com.backend.pro.service.appts.drug;

import com.backend.pro.model.entity.appts.Drug;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author l
* @description 针对表【appts_drug(预约药品表)】的数据库操作Service
* @createDate 2024-12-09 18:03:34
*/
public interface DrugService extends IService<Drug> {

    Long createDrug(Drug drug, HttpServletRequest request);

    Drug getDrugById(Long id);

    //根据预约id获取药品详情
    Drug getDrugByDetailsId(Long id);

}
