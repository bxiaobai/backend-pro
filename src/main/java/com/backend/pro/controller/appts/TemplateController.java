package com.backend.pro.controller.appts;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.model.dto.appts.template.TemplateAddRequest;
import com.backend.pro.model.dto.appts.template.TemplateQueryRequest;
import com.backend.pro.model.dto.appts.template.TemplateUpdateRequest;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.service.appts.template.TemplateService;
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

@Api("号源模板操作")
@Slf4j
@RestController
@RequestMapping("/template")
@Validated
public class TemplateController {

    @Resource
    private TemplateService templateService;


    @PostMapping("/add")
    @ApiOperation("添加号源模板")
    public BaseResponse<Long> addTemplate(@Valid @RequestBody TemplateAddRequest TemplateAddRequest, HttpServletRequest request) {
        return ResultUtils.success(templateService.createTemplate(TemplateAddRequest, request));
    }

    @PostMapping("/update")
    @ApiOperation("修改号源模板")
    public BaseResponse<Boolean> updateTemplate(@Valid @RequestBody TemplateUpdateRequest TemplateUpdateRequest, HttpServletRequest request) {
        templateService.updateTemplate(TemplateUpdateRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除号源模板")
    public BaseResponse<Boolean> deleteTemplate(@PathVariable Long id, HttpServletRequest request) {
        templateService.deleteTemplate(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询号源模板详情")
    public BaseResponse<TemplateVO> getTemplateById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(templateService.getTemplateById(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询号源模板分页")
    public BaseResponse<PageResult<TemplateVO>> listTemplatePage(@RequestBody TemplateQueryRequest TemplateQueryRequest) {
        return ResultUtils.success(templateService.listTemplatePage(TemplateQueryRequest));
    }



}
