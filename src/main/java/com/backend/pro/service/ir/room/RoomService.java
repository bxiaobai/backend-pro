package com.backend.pro.service.ir.room;

import com.backend.pro.model.dto.ir.room.RoomAddRequest;
import com.backend.pro.model.dto.ir.room.RoomQueryRequest;
import com.backend.pro.model.dto.ir.room.RoomUpdateRequest;
import com.backend.pro.model.entity.ir.room.Room;
import com.backend.pro.model.vo.ir.room.RoomSelectVO;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.model.vo.system.dept.DeptVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author l
 * @description 针对表【ir_room(输液室表)】的数据库操作Service
 * @createDate 2024-12-05 11:44:41
 */
public interface RoomService extends IService<Room> {

    /**
     * 创建一个新的输液室。
     *
     * @param createReqVO 包含输液室信息的请求对象
     * @param request     HTTP请求对象
     * @return 创建的输液室ID
     */
    Long createRoom(RoomAddRequest createReqVO, HttpServletRequest request);

    /**
     * 根据输液室ID删除指定的输液室。
     *
     * @param id      输液室ID
     * @param request HTTP请求对象
     */
    void deleteRoom(Long id, HttpServletRequest request);

    /**
     * 根据输液室ID获取输液室信息。
     *
     * @param id 输液室ID
     * @return 包含输液室信息的RoomVO对象
     */
    RoomVO getRoom(Long id);

    /**
     * 更新指定输液室的信息。
     *
     * @param updateReqVO 包含更新后的输液室信息的请求对象
     * @param request     HTTP请求对象
     */
    void updateRoom(RoomUpdateRequest updateReqVO, HttpServletRequest request);

    /**
     * 分页查询输液室列表。
     *
     * @param pageReqVO 分页查询请求对象
     * @return 分页结果，包含输液室信息的列表
     */
    PageResult<RoomVO> listRoomPage(RoomQueryRequest pageReqVO);

    /**
     * 查询所有输液室列表。
     *
     * @return 所有输液室信息的列表
     */
    List<RoomVO> listRoom();

    /**
     * 根据科室id获取输液室下拉框
     * @param deptId
     * @return
     */
    List<RoomVO> listByDeptId(Long deptId);

    List<RoomVO> getByDeptIdUsable(Long deptId);
}
