package com.backend.pro.http.controller;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.http.model.ir.Brxx;
import com.backend.pro.http.model.ir.MainData;
import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.http.service.HisService;
import com.backend.pro.model.vo.Med.Pat.PatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("获取输液数据操作")
@Slf4j
@RestController
@RequestMapping("/his")
@Validated
public class HisHttpController {

    @Resource
    private HisService hisService;

    @GetMapping
    @ApiOperation("获取输液数据")
    public BaseResponse<List<IrStrListVO>> getIrInfo(String medicalNumber, String startTime, String endTime) {
        List<IrStrListVO> irStrListVOS = hisService.buildIrStrListVO(medicalNumber, startTime, endTime);
        return ResultUtils.success(irStrListVOS);
    }

    @GetMapping("/getPat")
    @ApiOperation("获取病人信息")
    public BaseResponse<PatVO> getPatInfo(String medicalNumber) {
        PatVO patInfo = hisService.getPatInfo(medicalNumber);
        return ResultUtils.success(patInfo);
    }

}
