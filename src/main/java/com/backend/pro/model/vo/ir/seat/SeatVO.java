package com.backend.pro.model.vo.ir.seat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SeatVO {

    /**
     * id
     */
    private Long id;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 可接待患者类型
     */
    private Integer patType;

    /**
     * 座位状态
     */
    private Integer status;

    /**
     * 区域
     */
    private String area;

    /**
     * 行号
     */
    private Integer seatRow;

    /**
     * 列号
     */
    private Integer seatCol;

    /**
     * 标签(vip(vip),common(普通),bed(床为))
     */
    private String flag;

    /**
     * 创建时间
     */
    private Date createTime;


}