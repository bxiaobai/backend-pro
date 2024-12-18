package com.backend.pro.model.vo.ir.room;

import lombok.Data;

import java.util.Date;

@Data
public class RoomVO {

    /**
     * id
     */
    private Long id;

    /**
     * 输液室名称
     */
    private String irName;

    /**
     * 描述
     */
    private String irDesc;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 短信模板id
     */
    private String smsTemplateId;

    /**
     * 电话
     */
    private String phone;

    /**
     * 取号点
     */
    private String numberPoint;

    /**
     * 输液地点
     */
    private String irPlace;

    /**
     * 可接待患者类型
     */
    private Integer patType;

    /**
     * 创建时间
     */
    private Date createTime;

}
