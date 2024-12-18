package com.backend.pro.service.ir.room;

import com.backend.pro.model.dto.ir.room.AddRoomAndDeptRequest;
import com.backend.pro.model.entity.ir.room.IrDeptRoom;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author l
* @description 针对表【ir_drug_pool(预约药品表)】的数据库操作Service
* @createDate 2024-12-12 18:57:12
*/
public interface IrDeptRoomService extends IService<IrDeptRoom> {

    boolean updateRoomAndDeptId(AddRoomAndDeptRequest addRoomAndDeptRequest);

    //根据科室id获取可用输液室id
    List<Long> getRoomIdByDeptId(Long deptId);

}
