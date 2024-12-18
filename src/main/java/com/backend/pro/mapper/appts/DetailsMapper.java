package com.backend.pro.mapper.appts;

import cn.hutool.core.util.ObjectUtil;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.appts.Details.DetailsQueryRequest;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.vo.appts.DetailsVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.LambdaQueryWrapperX;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author l
 * @description 针对表【appts_details(号源库)】的数据库操作Mapper
 * @createDate 2024-12-09 17:37:24
 * @Entity com.backend.pro.model.entity.appts.Details
 */
public interface DetailsMapper extends BaseMapper<Details> {


    default Long selectBySourceId(Long id) {
        return selectCount(new LambdaQueryWrapperX<Details>().like(Details::getSourceId, id));
    }

    default PageResult<DetailsVO> selectPage(DetailsQueryRequest pageReqVO, Long irId) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();

        Page<Details> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<Details>()
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getCard()), "card", pageReqVO.getCard())
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getPatName()), "patName", pageReqVO.getPatName())
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getSeatNum()), "seatNum", pageReqVO.getSeatNum())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getDate()), "date", pageReqVO.getDate())
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getTime()), "DetailsName", pageReqVO.getTime())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .eq("irId", irId)
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), DetailsVO.class);
    }
}




