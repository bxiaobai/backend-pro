package com.backend.pro.mapper.ir.seat;

import cn.hutool.core.util.ObjectUtil;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.ir.room.RoomQueryRequest;
import com.backend.pro.model.dto.ir.seat.SeatQueryRequest;
import com.backend.pro.model.entity.ir.room.Room;
import com.backend.pro.model.entity.ir.seat.Seat;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.model.vo.ir.seat.SeatVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.LambdaQueryWrapperX;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author l
 * @description 针对表【ir_seat(输液室表)】的数据库操作Mapper
 * @createDate 2024-12-05 15:51:15
 * @Entity com.backend.pro.model.entity.ir.seat.Seat
 */
public interface SeatMapper extends BaseMapper<Seat> {

    default Seat selectByNumber(String number) {
        return selectOne(new QueryWrapper<Seat>().eq("seatNumber", number));
    }

    default Seat selectBySeatRowAndSeatCol(Long irId, Integer seatRow, Integer seatCol, String area) {
        return selectOne(new QueryWrapper<Seat>()
                .eq("irId", irId)
                .eq("seatRow", seatRow)
                .eq("seatCol", seatCol)
                .eq("area", area));
    }

    default List<Seat> selectIrByCount(Long irId) {
        return selectList(new QueryWrapper<Seat>().eq("irId", irId));
    }


    default List<SeatVO> selectSeatList(SeatQueryRequest seatQueryRequest) {
        List<Seat> seats = selectList(new LambdaQueryWrapperX<Seat>()
                .eqIfPresent(Seat::getIrId, seatQueryRequest.getIrId())
                .eqIfPresent(Seat::getPatType, seatQueryRequest.getPatType())
                .eqIfPresent(Seat::getStatus, seatQueryRequest.getStatus())
                .eqIfPresent(Seat::getArea, seatQueryRequest.getArea())
                .eqIfPresent(Seat::getFlag, seatQueryRequest.getFlag())
                .likeIfPresent(Seat::getSeatNumber, seatQueryRequest.getSeatNumber())
        );
        return BeanUtils.toBean(seats, SeatVO.class);
    }

    default PageResult<SeatVO> selectPage(SeatQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();
        Page<Seat> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<Seat>()
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getIrId()), "irId", pageReqVO.getIrId())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getPatType()), "patType", pageReqVO.getPatType())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getArea()), "area", pageReqVO.getArea())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getFlag()), "flag", pageReqVO.getFlag())
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getSeatNumber()), "seatNumber", pageReqVO.getSeatNumber())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), SeatVO.class);
    }


}




