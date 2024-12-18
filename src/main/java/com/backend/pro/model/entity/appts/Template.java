package com.backend.pro.model.entity.appts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import lombok.Data;

/**
 * 号源配置表
 * @TableName appts_template
 */
@TableName(value ="appts_template")
@Data
public class Template implements Serializable {
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
     * 开始时间
     */
    private Time startTime;

    /**
     * 结束时间
     */
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