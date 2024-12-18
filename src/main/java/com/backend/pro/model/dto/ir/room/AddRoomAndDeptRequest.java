package com.backend.pro.model.dto.ir.room;

import lombok.Data;

import javax.annotation.Resource;
import java.util.List;

@Data
public class AddRoomAndDeptRequest {

    private List<Long> roomId;

    private Long deptId;
}
