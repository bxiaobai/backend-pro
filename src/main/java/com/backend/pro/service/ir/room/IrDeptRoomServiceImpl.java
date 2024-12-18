package com.backend.pro.service.ir.room;

import com.backend.pro.model.dto.ir.room.AddRoomAndDeptRequest;
import com.backend.pro.model.entity.ir.room.IrDeptRoom;
import com.backend.pro.model.vo.ir.room.RoomSelectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.mapper.ir.room.IrDeptRoomMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author l
* @description 针对表【ir_drug_pool(预约药品表)】的数据库操作Service实现
* @createDate 2024-12-12 18:57:12
*/
@Service
public class IrDeptRoomServiceImpl extends ServiceImpl<IrDeptRoomMapper, IrDeptRoom>
    implements IrDeptRoomService{

    @Resource
    private IrDeptRoomMapper IrDeptRoomMapper;

    @Override
    @Transactional
    public boolean updateRoomAndDeptId(AddRoomAndDeptRequest addRoomAndDeptRequest) {
        //给科室添加输液室信息
        //先查询是否有数据
        Long deptId = addRoomAndDeptRequest.getDeptId();
        List<Long> roomIds = addRoomAndDeptRequest.getRoomId();
        List<IrDeptRoom> IrDeptRooms = roomIds.stream().map(item -> {
            IrDeptRoom IrDeptRoom = new IrDeptRoom();
            IrDeptRoom.setDeptId(deptId);
            IrDeptRoom.setRoomId(item);
            return IrDeptRoom;
        }).collect(Collectors.toList());
        //添加新数据
        return saveBatch(IrDeptRooms);
    }


    Long vailDateExist(Long deptId){
        //判断科室是否已经存在
        return IrDeptRoomMapper.selectCount(new QueryWrapper<IrDeptRoom>().eq("deptId", deptId));
    }


    @Override
    public List<Long> getRoomIdByDeptId(Long deptId) {
        return IrDeptRoomMapper.getRoomIdByDeptId(deptId);
    }

}




