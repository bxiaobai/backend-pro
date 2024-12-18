package com.backend.pro.model.dto.appts.template;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

/**
 * 创建模板类型请求
 */
@Data
public class TemplateAddRequest {


    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Time startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Time endTime;

    /**
     * 可接待患者类型
     */
    private Integer patType;

    /**
     * 正常号源总量
     */
    private Integer normalNum;

    /**
     * 临时号源总量
     */
    private Integer tempNum;


}