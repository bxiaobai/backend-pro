package com.backend.pro.service.med.pat;

import com.backend.pro.model.dto.Med.pat.PatAddRequest;
import com.backend.pro.model.dto.Med.pat.PatUpdateRequest;
import com.backend.pro.model.entity.med.pat.Pat;
import com.backend.pro.model.vo.Med.Pat.PatVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author l
* @description 针对表【med_pat】的数据库操作Service
* @createDate 2024-12-13 17:09:35
*/
public interface PatService extends IService<Pat> {
    /**
     * 新建病人
     */
    Long createPat(PatAddRequest patAddRequest , HttpServletRequest request);

    /**
     * 根据卡号查询患者
     */
    PatVO getPatByCard(String card);

    /**
     * 更新患者信息
     */
    void updatePat(PatUpdateRequest patUpdateRequest , HttpServletRequest request);



}
