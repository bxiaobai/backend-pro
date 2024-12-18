package com.backend.pro.controller.system.dept;

import com.backend.pro.common.BaseResponse;
import com.backend.pro.common.ErrorCode;
import com.backend.pro.common.ResultUtils;

import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.model.dto.ir.room.AddRoomAndDeptRequest;
import com.backend.pro.model.dto.system.dept.DeptAddRequest;
import com.backend.pro.model.dto.system.dept.DeptQueryRequest;
import com.backend.pro.model.dto.system.dept.DeptUpdateRequest;
import com.backend.pro.model.entity.ir.room.IrDeptRoom;
import com.backend.pro.model.entity.system.dept.Dept;
import com.backend.pro.model.vo.system.dept.DeptTreeVO;
import com.backend.pro.model.vo.system.dept.DeptVO;
import com.backend.pro.service.ir.room.IrDeptRoomService;
import com.backend.pro.service.system.dept.DeptService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.lang.model.type.DeclaredType;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api("科室操作")
@Slf4j
@RestController
@RequestMapping("/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @Resource
    private IrDeptRoomService irDeptRoomService;


    @PostMapping("/add")
    @ApiOperation("添加科室")
    public BaseResponse<Long> addDept(@Valid @RequestBody DeptAddRequest DeptAddRequest, HttpServletRequest request) {
        return ResultUtils.success(deptService.createDept(DeptAddRequest, request));
    }

    @PostMapping("/update")
    @ApiOperation("修改科室")
    public BaseResponse<Boolean> updateDept(@Valid @RequestBody DeptUpdateRequest DeptUpDeptRequest, HttpServletRequest request) {
        deptService.updateDept(DeptUpDeptRequest, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除科室")
    public BaseResponse<Boolean> deleteDept(@PathVariable Long id, HttpServletRequest request) {
        deptService.deleteDept(id, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/get/vo/{id}")
    @ApiOperation("查询科室详情")
    public BaseResponse<DeptVO> getDeptById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return ResultUtils.success(deptService.getDept(id));
    }

    @PostMapping("/list/page")
    @ApiOperation("查询科室分页")
    public BaseResponse<PageResult<DeptVO>> listDeptPage(@RequestBody DeptQueryRequest DeptQueryRequest) {
        return ResultUtils.success(deptService.listDeptPage(DeptQueryRequest));
    }


    @GetMapping("/list/all")
    @ApiOperation("获取全部类型集合")
    public BaseResponse<List<DeptVO>> listDeptAll() {
        return ResultUtils.success(BeanUtils.toBean(deptService.list(new QueryWrapper<Dept>().eq("flag", 1)), DeptVO.class));
    }

    @GetMapping("/list/tree")
    @ApiOperation("获取科室树形结构")
    public BaseResponse<List<DeptTreeVO>> listDeptTree() {
        return ResultUtils.success(deptService.listDeptTree());
    }

    @GetMapping("/list/tree-courtyard")
    @ApiOperation("获取院区树形结构")
    public BaseResponse<List<DeptTreeVO>> listDeptTreeCourtyard() {
        return ResultUtils.success(deptService.listDeptAllOne2());
    }

    @ApiOperation("给科室分配输液室")
    @PostMapping("/add/room")
    public BaseResponse<Boolean> addDeptRoom(@Valid @RequestBody AddRoomAndDeptRequest addRoomAndDeptRequest) {
        return ResultUtils.success(irDeptRoomService.updateRoomAndDeptId(addRoomAndDeptRequest));
    }

    @ApiOperation("删除科室的输液室")
    @GetMapping("/delete/room/{deptId}/{roomId}")
    public BaseResponse<Boolean> deleteDeptRoom(@PathVariable Long deptId, @PathVariable Long roomId) {
        IrDeptRoom one = irDeptRoomService.getOne(new QueryWrapper<IrDeptRoom>().eq("deptId", deptId).eq("roomId", roomId));
        ThrowUtils.throwIf(one == null, new ErrorCode(500001, "数据不存在，刷新重试！"));
       irDeptRoomService.remove(new QueryWrapper<IrDeptRoom>().eq("deptId", deptId).eq("roomId", roomId));
        return ResultUtils.success(true);
    }
}
