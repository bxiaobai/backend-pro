package com.backend.pro.controller.appts.Details;

import co.elastic.clients.elasticsearch.xpack.usage.Base;
import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.http.model.ir.Yzxxs;
import com.backend.pro.http.model.saveVO.AddIrVO;
import com.backend.pro.model.dto.appts.Details.AutoRequest;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import com.backend.pro.model.dto.appts.Details.DetailsQueryRequest;
import com.backend.pro.model.vo.appts.AutoVO;
import com.backend.pro.model.vo.appts.DetailsVO;
import com.backend.pro.service.appts.details.DetailsService;
import com.backend.pro.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("预约操作")
@Slf4j
@RestController
@RequestMapping("/details")
@Validated
public class DetailsController {

    @Resource
    private DetailsService detailsService;

    @PostMapping("/add")
    @ApiOperation("添加预约信息")
    public BaseResponse<Long> createDetails(@Validated @RequestBody DetailsAddRequest detailsAddRequest, HttpServletRequest request) {
        Long details = detailsService.createDetails(detailsAddRequest, request);
        return ResultUtils.success(details);
    }

    @GetMapping("/get/vo")
    @ApiOperation("获取预约信息")
    public BaseResponse<DetailsVO> getDetailsById(Long id) {
        return ResultUtils.success(detailsService.getDetailsById(id));
    }


    @GetMapping("/delete")
    @ApiOperation("取消预约")
    public BaseResponse<Boolean> removeDetails(Long id,HttpServletRequest request) {
        detailsService.cancelDetails(id,request);
        return ResultUtils.success(true);
    }

    @PostMapping("/page")
    @ApiOperation("分页获取预约信息")
    public BaseResponse<PageResult<DetailsVO>> listDetailsPage(@RequestBody DetailsQueryRequest detailsQueryRequest, HttpServletRequest request) {
        return ResultUtils.success(detailsService.listDetailsPage(detailsQueryRequest, request));
    }


    @ApiOperation("分页获取预约信息")
    @PostMapping("/docker/add")
    public BaseResponse<Boolean> dockerAdd(@RequestBody AddIrVO addIrVO) {
        return ResultUtils.success(true);
    }

    /**
     * 计算药品所需时间
     */
    @PostMapping("/count/drug/time")
    @ApiOperation("计算药品所需时间")
    public BaseResponse<Integer> countDrugTime(@RequestBody List<Yzxxs> yzxxs) {
        return ResultUtils.success(detailsService.countDrugTime(yzxxs));
    }

    /**
     * 自动选择医嘱时间和座位
     */
    @PostMapping("/auto/select")
    @ApiOperation("自动选择医嘱时间和座位")
    public BaseResponse<AutoVO> autoSelect(@RequestBody AutoRequest addIrVO) {
        return ResultUtils.success(detailsService.autoSelect(addIrVO));
    }
}
