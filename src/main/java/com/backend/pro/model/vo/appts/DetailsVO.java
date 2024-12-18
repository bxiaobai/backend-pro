package com.backend.pro.model.vo.appts;

import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.model.entity.appts.Drug;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 号源库
 */
@Data
public class DetailsVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 卡号
     */
    private String card;

    private String phone;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private String date;

    /**
     * 开始时间
     */
    private String time;


    /**
     * 座位类型(VIP,common,Bed)
     */
    private String seatType;

    /**
     * 座位号
     */
    private String seatNum;

    /**
     * 号源id
     */
    private String sourceId;

    /**
     * 号源类型(0正常号源,1临时号源)
     */
    private Integer type;

    /**
     * 状态(0未分配座位,1已分配座位,2已完成,3进行中)
     */
    private Integer status;

    /**
     * 创建用户 id
     */
    private Long userId;


    private Drug drugList;

    private List<IrStrListVO> irStrListVO;
    /**
     * 创建时间
     */
    private Date createTime;

    private Long seatId;



}