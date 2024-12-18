package com.backend.pro.controller.system.dict;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.model.dto.system.dict.dictData.DictDataAddRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataUpdateRequest;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.service.system.dict.DictDataService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api("字典数据操作")
@Slf4j
@RestController
@RequestMapping("/dict-data")
@Validated
public class DictDataController {

    @Resource
    DictDataService dictDataService;

    @PostMapping("/add")
    @ApiOperation("添加字典")
    public BaseResponse<Long> addDictData(@Valid @RequestBody DictDataAddRequest DictDataAddRequest, HttpServletRequest request) {
        return ResultUtils.success(dictDataService.createDictData(DictDataAddRequest, request));
    }

    @PostMapping("/update")
    @ApiOperation("修改字典")
    public BaseResponse<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateRequest DictDataUpdateRequest, HttpServletRequest request) {
        dictDataService.updateDictData(DictDataUpdateRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除字典")
    public BaseResponse<Boolean> deleteDictData(@PathVariable Long id, HttpServletRequest request) {
        dictDataService.deleteDictData(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询字典详情")
    public BaseResponse<DictDataVO> getDictDataById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(dictDataService.getDictData(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询字典分页")
    public BaseResponse<PageResult<DictDataVO>> listDictDataPage(@RequestBody DictDataQueryRequest DictDataQueryRequest) {
        return ResultUtils.success(dictDataService.getDictDataPage(DictDataQueryRequest));
    }

    @GetMapping("/list/type")
    @ApiOperation("获取指定类型数据的列表")
    public BaseResponse<List<DictDataVO>> listDictDataByType(@NotNull(message = "id不能为空") String type) {
        return ResultUtils.success(dictDataService.getDictDataListByDictType(type));
    }

    @GetMapping("/list/all")
    @ApiOperation("获取全部类型集合")
    public BaseResponse<List<DictDataVO>> listDictDataAll() {
        return ResultUtils.success(BeanUtils.toBean(dictDataService.list(), DictDataVO.class));
    }

}
