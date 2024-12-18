package com.backend.pro.model.entity.ir.seat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 输液室表
 * @TableName ir_seat
 */
@TableName(value ="ir_seat")
@Data
public class Seat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 修改人id
     */
    private Long editUserId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}