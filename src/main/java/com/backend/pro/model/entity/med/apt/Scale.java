package com.backend.pro.model.entity.med.apt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 评估结果表
 * @TableName appts_scale
 */
@TableName(value ="appts_scale")
@Data
public class Scale implements Serializable {
    /**
     * 评估id
     */
    @TableId(type = IdType.AUTO)
    private Long scaleId;

    /**
     * 记录id
     */
    private Long detailsId;

    /**
     * 
     */
    private String result;

    /**
     * 
     */
    private String scaleSuggest;

    /**
     * 
     */
    private String scaleJson;

    /**
     * 量表key
     */
    private Integer tag;

    //创建日期
    @TableField(exist = false)
    private String createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}