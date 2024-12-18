package com.backend.pro.mapper.system.dict;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeQueryRequest;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.vo.system.dict.DictTypeVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.LambdaQueryWrapperX;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author l
 * @description 针对表【sys_dict_type(字典值表)】的数据库操作Mapper
 * @createDate 2024-12-04 11:26:08
 * @Entity com.backend.pro.model.entity.system.dict.DictType1
 */
public interface DictTypeMapper extends JoinBaseMapper<DictType> {

    default DictType selectByType(String type) {
        return selectOne(new LambdaQueryWrapper<DictType>().eq(DictType::getDictType, type));
    }

    default DictType selectByName(String name) {
        return selectOne(new LambdaQueryWrapper<DictType>().eq(DictType::getDictName, name));
    }

    default List<DictType> selectListByType(String type) {
        return selectList(new LambdaQueryWrapper<DictType>().eq(DictType::getDictType, type));
    }


    default PageResult<DictTypeVO> selectPage(DictTypeQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();
        Page<DictType> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<DictType>()
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getDictType()), "dictType", pageReqVO.getDictType())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getDictName()), "dictName", pageReqVO.getDictName())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), DictTypeVO.class);
    }
}




