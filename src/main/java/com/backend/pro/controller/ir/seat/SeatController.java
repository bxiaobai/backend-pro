package com.backend.pro.controller.ir.seat;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;

import com.backend.pro.model.dto.ir.seat.SeatAddRequest;
import com.backend.pro.model.dto.ir.seat.SeatQueryRequest;
import com.backend.pro.model.dto.ir.seat.SeatUpdateRequest;
import com.backend.pro.model.vo.ir.seat.SeatLayoutVO;
import com.backend.pro.model.vo.ir.seat.SeatVO;
import com.backend.pro.service.ir.seat.SeatService;
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
import java.util.Map;

@Api("座位操作")
@Slf4j
@RestController
@RequestMapping("/seat")
@Validated
public class SeatController {

    @Resource
    private SeatService seatService;


    @PostMapping("/add")
    @ApiOperation("添加座位")
    public BaseResponse<Long> addSeat(@Valid @RequestBody SeatAddRequest SeatAddRequest, HttpServletRequest request) {
        return ResultUtils.success(seatService.createSeat(SeatAddRequest, request));
    }

    @PostMapping("/upSeat")
    @ApiOperation("修改座位")
    public BaseResponse<Boolean> updateSeat(@Valid @RequestBody SeatUpdateRequest SeatUpdateRequest, HttpServletRequest request) {
        seatService.updateSeat(SeatUpdateRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除座位")
    public BaseResponse<Boolean> deleteSeat(@PathVariable Long id, HttpServletRequest request) {
        seatService.deleteSeat(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询座位详情")
    public BaseResponse<SeatVO> getSeatById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(seatService.getSeatById(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询座位分页")
    public BaseResponse<PageResult<SeatVO>> listSeatPage(@RequestBody SeatQueryRequest SeatQueryRequest) {
        return ResultUtils.success(seatService.listSeatPage(SeatQueryRequest));
    }

    @GetMapping("/list/roomId/{id}")
    @ApiOperation("根据roomid查询座位")
    public BaseResponse<List<SeatVO>> listSeatByRoomId(@PathVariable Long id) {
        return ResultUtils.success(seatService.getSeatByRoomId(id));
    }


    @GetMapping("/list/map/{id}/{date}")
    @ApiOperation("获取全部座位")
    public BaseResponse<List<SeatLayoutVO>> listSeatAll(@PathVariable Long id ,  @PathVariable String date) {
        return ResultUtils.success(seatService.listSeatMap(id, date));
    }


}
