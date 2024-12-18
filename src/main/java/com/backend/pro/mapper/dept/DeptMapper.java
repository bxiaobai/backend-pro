package com.backend.pro.mapper.dept;

import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.system.dept.DeptAddRequest;
import com.backend.pro.model.dto.system.dept.DeptQueryRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.entity.system.dept.Dept;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.vo.system.dept.DeptVO;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author l
 * @description 针对表【sys_dept(科室表)】的数据库操作Mapper
 * @createDate 2024-12-04 19:17:01
 * @Entity com.backend.pro.model.entity.system.dept.Dept
 */
public interface DeptMapper extends BaseMapper<Dept> {

    default Dept getDeptByName(String name) {
        return selectOne(new QueryWrapper<Dept>().eq("deptName", name));
    }

    default PageResult<DeptVO> selectPage(DeptQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();

        Page<Dept> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<Dept>()
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getDeptName()), "deptName", pageReqVO.getDeptName())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .eq("flag", 1)
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getParentId()) && pageReqVO.getParentId() != 0, "parentId", pageReqVO.getParentId())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), DeptVO.class);
    }

    default Long selectByParentIdLong(Long id   ){
        return selectCount(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId ,id));
    }
}




