package com.backend.pro.mapper.ir.room;

import cn.hutool.core.util.ObjectUtil;
import com.backend.pro.constant.CommonConstant;
import com.backend.pro.model.dto.ir.room.RoomQueryRequest;
import com.backend.pro.model.entity.ir.room.Room;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author l
 * @description 针对表【ir_room(输液室表)】的数据库操作Mapper
 * @createDate 2024-12-05 11:44:41
 * @Entity com.backend.pro.model.entity.ir.room.Room
 */
public interface RoomMapper extends BaseMapper<Room> {

    default Room selectByName(String name) {
        return selectOne(new LambdaQueryWrapper<Room>().eq(Room::getIrName, name));
    }


    default PageResult<RoomVO> selectPage(RoomQueryRequest pageReqVO) {
        //排序字段
        String sortOrder = pageReqVO.getSortOrder();
        String sortField = pageReqVO.getSortField();
        Page<Room> dictTypePage = selectPage(
                new Page<>(pageReqVO.getCurrent(), pageReqVO.getPageSize()),
                new QueryWrapper<Room>()
                        .like(ObjectUtil.isNotEmpty(pageReqVO.getIrName()), "irName", pageReqVO.getIrName())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getStatus()), "status", pageReqVO.getStatus())
                        .eq(ObjectUtil.isNotEmpty(pageReqVO.getPatType()), "patType", pageReqVO.getPatType())
                        .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField)
        );
        return BeanUtils.toBean(new PageResult<>(dictTypePage.getRecords(), dictTypePage.getTotal()), RoomVO.class);
    }

   default List<RoomVO> selectByIds(List<Long> roomIdByDeptId){
       List<Room> rooms = selectList(new LambdaQueryWrapper<Room>().in(Room::getId, roomIdByDeptId));
       return BeanUtils.toBean(rooms, RoomVO.class);
   }
}




