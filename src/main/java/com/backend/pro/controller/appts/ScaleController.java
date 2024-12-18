package com.backend.pro.controller.appts;

import com.alibaba.excel.util.DateUtils;
import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.entity.med.apt.Scale;
import com.backend.pro.model.vo.appts.DetailsVO;
import com.backend.pro.service.appts.details.DetailsService;
import com.backend.pro.service.apt.ScaleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scale")
public class ScaleController {

    @Resource
    private ScaleService scaleService;

    @Resource
    private DetailsService detailsService;

    /**
     * 添加
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody Scale Scale) {
        boolean save = scaleService.addApppointmentScale(Scale);
        return ResultUtils.success(save);
    }

    /**
     * 查看详情
     *
     * @param scaleId
     * @return
     */
    @GetMapping("/get/{scaleId}")
    public BaseResponse<List<Scale>> getScale(@PathVariable Long scaleId) {
        List<Scale> list = scaleService.list(new LambdaQueryWrapper<Scale>().eq(Scale::getDetailsId, scaleId));
        return ResultUtils.success(list);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody Scale Scale) {
        boolean save = scaleService.updateById(Scale);
        return ResultUtils.success(save);
    }

    /**
     * 根据卡号查询历史筛查结果
     */
    @GetMapping("/get/vo/{scaleId}")
    public BaseResponse<List<Scale>> getScaleByCardId(@PathVariable Long scaleId) {
        DetailsVO detailsById = detailsService.getDetailsById(scaleId);
        List<Details> list1 = detailsService
                .list(new QueryWrapper<Details>().eq("card", detailsById.getCard())
                        .ne("status", 7));
        List<Long> collect = list1.stream().map(Details::getId).collect(Collectors.toList());
        List<Scale> list = scaleService.list(
                new LambdaQueryWrapper<Scale>()
                        .in(Scale::getDetailsId, collect));
        //创建日期取详情里面对应id的创建日期
        List<Scale> collect1 = list.stream()
                .peek(scale -> {
                    list1.stream()
                            .filter(details -> details.getId().equals(scale.getDetailsId()))
                            .findFirst().ifPresent(matchingDetails -> scale.setCreateDate(DateUtils.format(matchingDetails.getCreateTime(), "yyyy-MM-dd")));
                })
                .collect(Collectors.toList());
        return ResultUtils.success(collect1);
    }


}
