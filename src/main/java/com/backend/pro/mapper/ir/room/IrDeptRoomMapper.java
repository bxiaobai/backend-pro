package com.backend.pro.mapper.ir.room;

import com.backend.pro.model.entity.ir.room.IrDeptRoom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author l
* @description 针对表【ir_drug_pool(预约药品表)】的数据库操作Mapper
* @createDate 2024-12-12 18:57:12
* @Entity com.backend.pro.model.entity.ir.room.IrDeptRoom
*/
public interface IrDeptRoomMapper extends BaseMapper<IrDeptRoom> {

    default void deleteByDeptId(Long deptId){
        delete(new QueryWrapper<IrDeptRoom>().eq("deptId",deptId));
    }

    default List<Long> getRoomIdByDeptId(Long deptId){
        return selectList(new QueryWrapper<IrDeptRoom>().eq("deptId",deptId)).stream().map(IrDeptRoom::getRoomId).collect(Collectors.toList());
    }
}




