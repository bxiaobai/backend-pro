package com.backend.pro.model.entity.ir.room;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 输液室表
 * @TableName ir_room
 */
@TableName(value ="ir_room")
@Data
public class Room implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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