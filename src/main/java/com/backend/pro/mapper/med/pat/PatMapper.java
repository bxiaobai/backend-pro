package com.backend.pro.mapper.med.pat;

import com.backend.pro.model.entity.med.pat.Pat;
import com.backend.pro.model.vo.Med.Pat.PatVO;
import com.backend.pro.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author l
 * @description 针对表【med_pat】的数据库操作Mapper
 * @createDate 2024-12-13 17:09:35
 * @Entity com.backend.pro.model.entity.med.pat.Pat
 */
public interface PatMapper extends BaseMapper<Pat> {

    default PatVO getPatByCard(String card) {
        return BeanUtils.toBean(selectOne(new QueryWrapper<Pat>().eq("card", card)) , PatVO.class);
    }

}




