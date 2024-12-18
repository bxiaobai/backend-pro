package com.backend.pro.http.model;

import lombok.Data;

/**
 * 取消预约对象
 */
@Data
public class RemoveApptsDTO {

    private String card;
    private String yuyuehm;
    private String irId;
    private String userId;
}
