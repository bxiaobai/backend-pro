package com.backend.pro.mapper.appts;

import cn.hutool.core.util.ObjectUtil;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.appts.template.TemplateQueryRequest;
import com.backend.pro.model.dto.ir.seat.SeatQueryRequest;
import com.backend.pro.model.entity.appts.Template;
import com.backend.pro.model.entity.ir.seat.Seat;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.model.vo.ir.seat.SeatVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.LambdaQueryWrapperX;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author l
 * @description 针对表【appts_template(号源配置表)】的数据库操作Mapper
 * @createDate 2024-12-09 11:42:31
 * @Entity com.backend.pro.model.entity.appts.Template
 */
public interface TemplateMapper extends BaseMapper<Template> {

    default Template getTimeAndIrId(Template templateVO) {
        return selectOne(new LambdaQueryWrapperX<Template>()
                .eq(Template::getIrId, templateVO.getIrId())
                .between(Template::getStartTime, templateVO.getStartTime(), templateVO.getEndTime())
        );
    }

    default List<TemplateVO> listTemplateListByIrId(Long irId) {
        List<Template> templates = selectList(new LambdaQueryWrapperX<Template>()
                .eq(Template::getIrId, irId)
                .orderByAsc(Template::getStartTime)
        );
        return BeanUtils.toBean(templates, TemplateVO.class);
    }

    default PageResult<TemplateVO> selectPage(TemplateQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();
        Page<Template> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<Template>()
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getIrId()), "irId", pageReqVO.getIrId())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), TemplateVO.class);
    }
}




