package com.backend.pro.controller.system.dict;

import cn.hutool.http.HttpRequest;
import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeAddRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeQueryRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeUpdateRequest;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.vo.system.dict.DictTypeVO;
import com.backend.pro.service.system.dict.DictTypeService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api("字典操作")
@Slf4j
@RestController
@RequestMapping("/dict-type")
@Validated
public class DictTypeController {

    @Resource
    DictTypeService dictTypeService;

    @PostMapping("/add")
    @ApiOperation("添加字典")
    public BaseResponse<Long> addDictType(@Valid @RequestBody DictTypeAddRequest dictTypeAddRequest, HttpServletRequest request) {
        return ResultUtils.success(dictTypeService.createDictData(dictTypeAddRequest, request));
    }

    @PostMapping("/update")
    @ApiOperation("修改字典")
    public BaseResponse<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateRequest dictTypeUpdateRequest, HttpServletRequest request) {
        dictTypeService.updateDictData(dictTypeUpdateRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除字典")
    public BaseResponse<Boolean> deleteDictType(@PathVariable Long id, HttpServletRequest request) {
        dictTypeService.deleteDictData(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询字典详情")
    public BaseResponse<DictTypeVO> getDictTypeById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(dictTypeService.getDictData(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询字典分页")
    public BaseResponse<PageResult<DictTypeVO>> listDictTypePage(@RequestBody DictTypeQueryRequest dictTypeQueryRequest) {
        return ResultUtils.success(dictTypeService.getDictDataPage(dictTypeQueryRequest));
    }

    @GetMapping("/list/type")
    @ApiOperation("获取指定类型数据的列表")
    public BaseResponse<List<DictTypeVO>> listDictTypeByType(@NotNull(message = "id不能为空") String type) {
        return ResultUtils.success(dictTypeService.getDictDataListByDictType(type));
    }

    @GetMapping("/list/all")
    @ApiOperation("获取全部类型集合")
    public BaseResponse<List<DictTypeVO>> listDictTypeAll() {
        return ResultUtils.success(BeanUtils.toBean(dictTypeService.list(), DictTypeVO.class));
    }

}
