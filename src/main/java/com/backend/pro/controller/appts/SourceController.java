package com.backend.pro.controller.appts;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.model.dto.appts.Source.DoctorQueryRequest;
import com.backend.pro.model.dto.appts.Source.SourceAddRequest;
import com.backend.pro.model.dto.appts.Source.SourceQueryRequest;
import com.backend.pro.model.vo.appts.SourceApptsVO;
import com.backend.pro.service.appts.source.SourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api("号源池操作")
@Slf4j
@RestController
@RequestMapping("/source")
@Validated
public class SourceController {

    @Resource
    private SourceService sourceService;


    @PostMapping("/add")
    @ApiOperation("生成号源池")
    public BaseResponse<Boolean> addSource(@Valid @RequestBody SourceAddRequest sourceAddRequest, HttpServletRequest request) {
        sourceService.createSource(sourceAddRequest.getIrId(), sourceAddRequest.getDates(), request);
        return ResultUtils.success(true);
    }

    @PostMapping("/list")
    @ApiOperation("获取当日号源池列表")
    public BaseResponse<List<SourceApptsVO>> listSource(@Valid @RequestBody SourceQueryRequest sourceQueryRequest, HttpServletRequest request) {
        return ResultUtils.success(sourceService.listDateAndIrId(sourceQueryRequest, request));
    }


}
