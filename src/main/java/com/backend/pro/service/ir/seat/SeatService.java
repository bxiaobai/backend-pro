package com.backend.pro.service.ir.seat;

import com.backend.pro.model.dto.ir.seat.SeatAddRequest;
import com.backend.pro.model.dto.ir.seat.SeatQueryRequest;
import com.backend.pro.model.dto.ir.seat.SeatUpdateRequest;
import com.backend.pro.model.entity.ir.seat.Seat;
import com.backend.pro.model.vo.ir.seat.SeatLayoutVO;
import com.backend.pro.model.vo.ir.seat.SeatVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author l
 * @description 针对表【ir_seat(输液室表)】的数据库操作Service
 * @createDate 2024-12-05 15:51:15
 */
public interface SeatService extends IService<Seat> {

    /**
     * 创建一个新的座位。
     *
     * @param createReqVO 包含座位信息的请求对象
     * @param request     HTTP请求对象
     * @return 创建的座位ID
     */
    Long createSeat(SeatAddRequest createReqVO, HttpServletRequest request);

    /**
     * 更新指定座位的信息。
     *
     * @param updateReqVO 包含更新后的座位信息的请求对象
     * @param request     HTTP请求对象
     */
    void updateSeat(SeatUpdateRequest updateReqVO, HttpServletRequest request);

    /**
     * 根据座位ID删除指定的座位。
     *
     * @param id      座位ID
     * @param request HTTP请求对象
     */
    void deleteSeat(Long id, HttpServletRequest request);

    /**
     * 根据座位ID获取座位信息。
     *
     * @param id 座位ID
     * @return 包含座位信息的SeatVO对象
     */
    SeatVO getSeatById(Long id);

    /**
     * 分页查询座位列表。
     *
     * @param pageReqVO 分页查询请求对象
     * @return 分页结果，包含座位信息的列表
     */
    PageResult<SeatVO> listSeatPage(SeatQueryRequest pageReqVO);

    /**
     * 查询所有座位并按某种规则分组。
     *
     * @return 按某种规则分组的座位信息的Map
     */
    List<SeatLayoutVO> listSeatMap(Long irId ,String date);

    /**
     * 根据查询条件查询所有座位列表。
     *
     * @param seatQueryRequest 查询条件请求对象
     * @return 符合查询条件的座位信息的列表
     */
    List<SeatVO> listAll(SeatQueryRequest seatQueryRequest);


    /**
     * 根据输液室ID查询是否存在座位。
     *
     * @param irId 输液室ID
     * @return 存在座位的座位ID，如果不存在则返回null
     */
    Integer getSeatByIrId(Long irId);

    /**
     * 根据房间ID查询座位列表。
     *
     * @param roomId 房间ID
     * @return 包含座位信息的SeatVO对象列表
     */
    List<SeatVO> getSeatByRoomId(Long roomId);



}
