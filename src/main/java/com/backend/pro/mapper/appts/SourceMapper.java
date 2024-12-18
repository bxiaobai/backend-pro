package com.backend.pro.mapper.appts;

import cn.hutool.core.util.ObjectUtil;
import com.backend.pro.model.dto.appts.Source.SourceQueryRequest;
import com.backend.pro.model.entity.appts.Source;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.utils.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * @author l
 * @description 针对表【appts_source(号源库)】的数据库操作Mapper
 * @createDate 2024-12-09 11:42:31
 * @Entity com.backend.pro.model.entity.appts.Source
 */
public interface SourceMapper extends BaseMapper<Source> {

    /**
     * 检验当天是否有相同号源
     */
    default List<Source> selectByDateAndIrId(Long irId, String date) {
        LambdaQueryWrapperX<Source> lambdaQueryWrapper = new LambdaQueryWrapperX<>();
        lambdaQueryWrapper.eq(Source::getIrId, irId);
        lambdaQueryWrapper.eq(Source::getDate, date);
        return selectList(lambdaQueryWrapper);
    }

    default List<Source> listDateAndIrId(String date, Long irId) {
        return selectList(new LambdaQueryWrapper<Source>()
                .eq(Source::getIrId, irId)
                .eq(Source::getDate, date)
                .eq(Source::getStatus, 0)
        );
    }
}




