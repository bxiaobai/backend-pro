package com.backend.pro.mapper.system.dict;

import cn.hutool.core.util.ObjectUtil;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.model.vo.system.dict.DictTypeVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author l
 * @description 针对表【sys_dict_data(字典表)】的数据库操作Mapper
 * @createDate 2024-12-04 11:26:08
 * @Entity com.backend.pro.model.entity.system.dict.DictData
 */
public interface DictDataMapper extends BaseMapper<DictData> {

    default DictData selectByType(String type) {
        return selectOne(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, type));
    }

    default Long selectByTypeCount(String type) {
        return selectCount(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, type));
    }

    default DictData selectTypeAndValue(String type, String value) {
        return selectOne(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, type).eq(DictData::getValue, value));
    }

    default PageResult<DictDataVO> selectPage(DictDataQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();
        Page<DictData> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<DictData>()
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getLabel()), "label", pageReqVO.getLabel())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), DictDataVO.class);
    }

    default List<DictDataVO> selectListByTypeList(String type) {
        return BeanUtils.toBean(selectList(new LambdaQueryWrapper<DictData>().eq(DictData::getDictType, type)), DictDataVO.class);
    }
}




