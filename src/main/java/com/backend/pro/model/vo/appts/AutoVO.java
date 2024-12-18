package com.backend.pro.model.vo.appts;

import lombok.Data;

import java.util.List;

@Data
public class AutoVO {
    //选择的时间列表
    private List<Long> sourceId;

    //选择的座位id
    private Long seatId;
}
