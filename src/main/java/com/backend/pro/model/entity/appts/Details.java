package com.backend.pro.model.entity.appts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 号源库
 * @TableName appts_details
 */
@TableName(value ="appts_details")
@Data
public class Details implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 卡号
     */
    private String card;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 日期
     */
    private Date date;


    private String time;

    /**
     * 座位类型(VIP,common,Bed)
     */
    private String seatType;

    /**
     * 座位号
     */
    private String seatNum;

    private Long seatId;

    /**
     * 号源id
     */
    private String sourceId;

    /**
     * 号源类型(0正常号源,1临时号源)
     */
    private Integer type;

    /**
     * 状态(0已预约,1已分配座位,2已完成,3进行中,5已取消)
     */
    private Integer status;

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
    /**
     * 排队号
     */
    private String queueNum;
    /**
     * 手机号
     */
    private String phone;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}