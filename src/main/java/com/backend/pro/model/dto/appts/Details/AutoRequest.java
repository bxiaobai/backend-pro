package com.backend.pro.model.dto.appts.Details;

import lombok.Data;

@Data
public class AutoRequest {
    /**
     * 输液室id
     */
    private Long irId;
    /**
     * 日期
     */
    private String date;
    /**
     * 输液总时长
     */
    private Integer totalTime;
    /**
     * 开始时间
     */
    private String startTime;
}
