package com.backend.pro.service.ir.room;

import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.model.dto.ir.room.RoomAddRequest;
import com.backend.pro.model.dto.ir.room.RoomQueryRequest;
import com.backend.pro.model.dto.ir.room.RoomUpdateRequest;
import com.backend.pro.model.entity.ir.room.IrDeptRoom;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.ir.room.RoomSelectVO;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.service.ir.seat.SeatService;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.ir.room.Room;
import com.backend.pro.mapper.ir.room.RoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【ir_room(输液室表)】的数据库操作Service实现
 * @createDate 2024-12-05 11:44:41
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
        implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private UserService userService;

    @Resource
    private SeatService seatService;

    @Resource
    private IrDeptRoomService irDeptRoomService;

    @Override
    public Long createRoom(RoomAddRequest createReqVO, HttpServletRequest request) {
        //检验名称是否重复
        validateRoomName(null, createReqVO.getIrName());
        //插入
        Room room = BeanUtils.toBean(createReqVO, Room.class);
        User loginUser = userService.getLoginUser(request);
        room.setUserId(loginUser.getId());
        room.setEditUserId(loginUser.getId());
        roomMapper.insert(room);
        return room.getId();
    }

    @Override
    public void deleteRoom(Long id, HttpServletRequest request) {
        //检验数据是否存在
        validateRoomExists(id);
        //查询输液室是否有座位
        validateRoomChildren(id);
        roomMapper.deleteById(id);
    }

    @Override
    public RoomVO getRoom(Long id) {
        return BeanUtils.toBean(roomMapper.selectById(id), RoomVO.class);
    }

    @Override
    public void updateRoom(RoomUpdateRequest updateReqVO, HttpServletRequest request) {
        //检验数据是否存在
        validateRoomExists(updateReqVO.getId());
        //检验名称是否重复
        validateRoomName(updateReqVO.getId(), updateReqVO.getIrName());
        //插入
        Room room = BeanUtils.toBean(updateReqVO, Room.class);
        User loginUser = userService.getLoginUser(request);
        room.setEditUserId(loginUser.getId());
        roomMapper.updateById(room);

    }

    @Override
    public PageResult<RoomVO> listRoomPage(RoomQueryRequest pageReqVO) {
        return roomMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RoomVO> listRoom() {
        return BeanUtils.toBean(roomMapper.selectList(null), RoomVO.class);
    }


    //检验输液室名称是否重复
    void validateRoomName(Long id, String irName) {
        Room roomByName = roomMapper.selectByName(irName);
        if (roomByName == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_ROOM_EXISTS);
        ThrowUtils.throwIf(!roomByName.getId().equals(id), SYSTEM_ROOM_EXISTS);
    }

    //检验是否存在
    void validateRoomExists(Long id) {
        Room room = roomMapper.selectById(id);
        ThrowUtils.throwIf(room == null, NOT_FOUND_ERROR);
    }

    void validateRoomChildren(Long id) {
        Integer seatByIrId = seatService.getSeatByIrId(id);
        ThrowUtils.throwIf(seatByIrId > 0, SYSTEM_ROOM_HAS_CHILDREN);
    }


    @Override
    public List<RoomVO> listByDeptId(Long deptId) {
        List<Long> roomIdByDeptId = irDeptRoomService.getRoomIdByDeptId(deptId);
        if (roomIdByDeptId.isEmpty()){
            return new ArrayList<>();
        }
        //获取id集合
        return roomMapper.selectByIds(roomIdByDeptId).stream()
                .map(item -> BeanUtils.toBean(item, RoomVO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<RoomVO> getByDeptIdUsable(Long deptId) {
        List<Long> roomIdByDeptId = irDeptRoomService.getRoomIdByDeptId(deptId);
        List<RoomVO> roomVOS = this.listRoom();
        // 过滤出可选用的房间
        return roomVOS.stream()
                .filter(room -> !roomIdByDeptId.contains(room.getId()))
                .collect(Collectors.toList());
    }
}




