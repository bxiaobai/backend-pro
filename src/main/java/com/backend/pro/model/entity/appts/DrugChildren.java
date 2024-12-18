package com.backend.pro.model.entity.appts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预约药品子表
 * @TableName appts_drug_children
 */
@TableName(value ="appts_drug_children")
@Data
public class DrugChildren implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String sjly;

    /**
     * 
     */
    private String cfsbh;

    /**
     * 
     */
    private String ds;

    /**
     * 
     */
    private String gg;

    /**
     * 
     */
    private String jl;

    /**
     * 
     */
    private String pd;

    /**
     * 
     */
    private Integer psxx;

    /**
     * 
     */
    private String ypmc;

    /**
     * 
     */
    private Integer z_id;

    /**
     * 
     */
    private String cfls;

    /**
     * 
     */
    private Integer kkcbz;

    /**
     * 
     */
    private Integer fysycs;

    /**
     * 
     */
    private Integer dsybz;

    /**
     * 
     */
    private Integer sl;

    /**
     * 
     */
    private Integer psjg;

    /**
     * 
     */
    private Integer yf;

    /**
     * 
     */
    private String ypid;

    /**
     * 
     */
    private Integer ypcs;

    /**
     * 
     */
    private String sydh;

    /**
     * 
     */
    private Integer sycs;

    /**
     * 
     */
    private String bzjl;

    /**
     * 
     */
    private String yfms;

    /**
     * 
     */
    private Integer kssbz;

    /**
     * 
     */
    private String ylts;

    /**
     * 
     */
    private Integer pcdm;

    /**
     * 
     */
    private String yytj;

    /**
     * 
     */
    private String kbbz;

    /**
     * 
     */
    private Integer jpid;

    /**
     * 
     */
    private String ps;

    /**
     * 
     */
    private String bz;

    /**
     * 
     */
    private Integer zby;

    /**
     * 
     */
    private Integer sfzt;

    /**
     * 
     */
    private String dose;

    /**
     * 
     */
    private String unit;

    /**
     * 
     */
    private Integer sysc;

    /**
     * 主表id
     */
    private Long drugId;

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