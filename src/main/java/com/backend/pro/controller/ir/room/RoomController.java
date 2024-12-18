package com.backend.pro.controller.ir.room;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ResultUtils;

import com.backend.pro.model.dto.ir.room.RoomAddRequest;
import com.backend.pro.model.dto.ir.room.RoomQueryRequest;
import com.backend.pro.model.dto.ir.room.RoomUpdateRequest;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.service.ir.room.RoomService;
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

@Api("输液室操作")
@Slf4j
@RestController
@RequestMapping("/room")
@Validated
public class RoomController {

    @Resource
    private RoomService roomService;


    @PostMapping("/add")
    @ApiOperation("添加输液室")
    public BaseResponse<Long> addRoom(@Valid @RequestBody RoomAddRequest roomAddRequest, HttpServletRequest request) {
        return ResultUtils.success(roomService.createRoom(roomAddRequest, request));
    }

    @PostMapping("/upRoom")
    @ApiOperation("修改输液室")
    public BaseResponse<Boolean> updateRoom(@Valid @RequestBody RoomUpdateRequest roomUpdateRequest, HttpServletRequest request) {
        roomService.updateRoom(roomUpdateRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除输液室")
    public BaseResponse<Boolean> deleteRoom(@PathVariable Long id, HttpServletRequest request) {
        roomService.deleteRoom(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询输液室详情")
    public BaseResponse<RoomVO> getRoomById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(roomService.getRoom(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询输液室分页")
    public BaseResponse<PageResult<RoomVO>> listRoomPage(@RequestBody RoomQueryRequest RoomQueryRequest) {
        return ResultUtils.success(roomService.listRoomPage(RoomQueryRequest));
    }


    @GetMapping("/list/all")
    @ApiOperation("获取全部输液室")
    public BaseResponse<List<RoomVO>> listRoomAll() {
        return ResultUtils.success(BeanUtils.toBean(roomService.list(), RoomVO.class));
    }


    @GetMapping("/list/docker/{deptId}")
    @ApiOperation("根据科室id获取可用输液室")
    public BaseResponse<List<RoomVO>> listDeptDocker(@PathVariable Long deptId) {
        return ResultUtils.success(roomService.listByDeptId(deptId));
    }

    @ApiOperation("根据科室id获取可添加输液室")
    @GetMapping("/addByDeptId-usable-room/{deptId}")
    public BaseResponse<List<RoomVO>> listByDeptIdUsable(@PathVariable Long deptId) {
        return ResultUtils.success(roomService.getByDeptIdUsable(deptId));
    }

}
