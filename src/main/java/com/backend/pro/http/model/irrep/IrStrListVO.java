package com.backend.pro.http.model.irrep;

import com.backend.pro.http.model.ir.Yzxxs;
import lombok.Data;

import java.util.List;

@Data
public class IrStrListVO {

    /**
     * 创建时间
     */
    private String creationTime;

    /**
     * 需要输液天数
     */
    private Integer infusionTotal;

    /**
     * 已输液天数
     */
    private Integer infusionNum;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 输液信息列表
     */
    private List<Yzxxs> yzxxsList;

    /**
     * 输液时间
     */
    private Integer infusionTime;

    /**
     * 创建医生
     */
    private String createDoctor;

    /**
     * 是否收费
     */
    private Boolean isCharge;

    /**
     * 输液天数
     */
    private String infusionDays;

    /**
     * 科室科室名称
     */
    private String kfksmc;

}
