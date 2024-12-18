package com.backend.pro.model.entity.system.medical;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 医护表
 * @TableName sys_medical
 */
@TableName(value ="sys_medical")
@Data
public class Medical implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医护代码
     */
    private String code;

    /**
     * 医护姓名
     */
    private String name;

    /**
     * 医护类型
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}