package com.backend.pro.service.ir.seat;

import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.mapper.appts.DetailsMapper;
import com.backend.pro.model.dto.ir.seat.SeatAddRequest;
import com.backend.pro.model.dto.ir.seat.SeatQueryRequest;
import com.backend.pro.model.dto.ir.seat.SeatUpdateRequest;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.vo.ir.seat.SeatLayoutVO;
import com.backend.pro.model.vo.ir.seat.SeatVO;
import com.backend.pro.service.appts.details.DetailsService;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.ir.seat.Seat;
import com.backend.pro.mapper.ir.seat.SeatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.*;
import static com.backend.pro.constant.AptConstant.APT_STATUS_DELETE;

/**
 * @author l
 * @description 针对表【ir_seat(输液室表)】的数据库操作Service实现
 * @createDate 2024-12-05 15:51:15
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
        implements SeatService {

    @Resource
    private SeatMapper seatMapper;

    @Resource
    private UserService userService;

    @Resource
    private DetailsMapper detailsMapper;

    @Override
    public Long createSeat(SeatAddRequest createReqVO, HttpServletRequest request) {
        //检测是否有相同名称
        validateSeatNumber(null, createReqVO.getSeatNumber());
        //检测当前位置是否已经有座位
        validateSeatExist(createReqVO.getIrId(), createReqVO.getSeatRow(), createReqVO.getSeatCol(), createReqVO.getArea(), null);
        //添加
        Seat seat = BeanUtils.toBean(createReqVO, Seat.class);
        seat.setUserId(userService.getLoginUser(request).getId());
        seat.setEditUserId(userService.getLoginUser(request).getId());
        seatMapper.insert(seat);
        return seat.getId();
    }

    @Override
    public void updateSeat(SeatUpdateRequest updateReqVO, HttpServletRequest request) {
        //检测值是否存在
        validateSeatExist(updateReqVO.getIrId());
        validateSeatNumber(updateReqVO.getIrId(), updateReqVO.getSeatNumber());
        //检测当前位置是否已经有座位
        validateSeatExist(updateReqVO.getIrId(), updateReqVO.getSeatRow(), updateReqVO.getSeatCol(), updateReqVO.getArea(), updateReqVO.getId());
        //更新
        Seat seat = BeanUtils.toBean(updateReqVO, Seat.class);
        seat.setEditUserId(userService.getLoginUser(request).getId());
        seatMapper.updateById(seat);
    }

    @Override
    public void deleteSeat(Long id, HttpServletRequest request) {
        //检测是否存在
        validateSeatExist(id);
        seatMapper.deleteById(id);
    }

    @Override
    public SeatVO getSeatById(Long id) {
        return BeanUtils.toBean(seatMapper.selectById(id), SeatVO.class);
    }

    @Override
    public PageResult<SeatVO> listSeatPage(SeatQueryRequest pageReqVO) {
        return seatMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SeatLayoutVO> listSeatMap(Long irId ,String date) {
        List<Seat> seats = seatMapper.selectIrByCount(irId);
        //根据区域分组
        Map<String, List<SeatVO>> listMap = seats.stream()
                .map(seat -> BeanUtils.toBean(seat, SeatVO.class))
                .collect(Collectors.groupingBy(SeatVO::getArea));

        List<Details> list = new ArrayList<>();

        List<SeatLayoutVO> seatLayoutVOS = listMap.entrySet().stream()
                .map(entry -> new SeatLayoutVO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

//        if (date != null) {
//            // 查询输液室今日预约的座位信息
//            list = detailsMapper.selectList(new QueryWrapper<Details>()
//                    .eq("date", date)
//                    .eq("irId", irId)
//                    .ne("status", APT_STATUS_DELETE));
//        }
//
//        if (!list.isEmpty()) {
//            Set<Long> bookedSeatIds = list.stream()
//                    .map(Details::getSeatId)
//                    .collect(Collectors.toSet());
//
//            seatLayoutVOS.forEach(seatLayoutVO -> seatLayoutVO.getChildren().forEach(seat -> {
//                if (bookedSeatIds.contains(seat.getId())) {
//                    seat.setStatus(1);
//                }
//            }));
//        }
        //将分组的结果转为集合返回
        return seatLayoutVOS;
    }

    @Override
    public List<SeatVO> listAll(SeatQueryRequest seatQueryRequest) {
        return seatMapper.selectSeatList(seatQueryRequest);
    }

    @Override
    public Integer getSeatByIrId(Long irId) {
        return seatMapper.selectIrByCount(irId).size();
    }


    //检测是否有相同座位号
    void validateSeatNumber(Long id, String seatNumber) {
        Seat seatBySeatNumber = seatMapper.selectByNumber(seatNumber);
        if (seatBySeatNumber == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_SEAT_EXISTS);
        ThrowUtils.throwIf(!seatBySeatNumber.getId().equals(id), SYSTEM_SEAT_EXISTS);

    }

    //检测座位是否存在
    void validateSeatExist(Long seatId) {
        Seat seat = seatMapper.selectById(seatId);
        ThrowUtils.throwIf(seat == null, NOT_FOUND_ERROR);
    }

    //检测当前科室当前行号列号区域是否有座位
    void validateSeatExist(Long irId, Integer seatRow, Integer seatCol, String area, Long id) {
        Seat seat = seatMapper.selectBySeatRowAndSeatCol(irId, seatRow, seatCol, area);
        if (seat == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SEAT_COL_ROW_EXISTS);
        ThrowUtils.throwIf(!seat.getId().equals(id), SEAT_COL_ROW_EXISTS);
    }


    @Override
    public List<SeatVO> getSeatByRoomId(Long roomId) {
        return BeanUtils.toBean(seatMapper.selectIrByCount(roomId), SeatVO.class);
    }
}




