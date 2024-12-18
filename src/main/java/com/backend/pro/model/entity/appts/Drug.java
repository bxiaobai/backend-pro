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
 * 预约药品表
 * @TableName appts_drug
 */
@TableName(value ="appts_drug")
@Data
public class Drug implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 预约id
     */
    private Long detailsId;

    /**
     * 流水号
     */
    private String registerId;

    /**
     * 输液单号拼接
     */
    private String irNos;

    /**
     * 药品创建时间
     */
    private String drugTime;

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


    @TableField(exist = false)
    private List<DrugChildren> drugChildren;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}