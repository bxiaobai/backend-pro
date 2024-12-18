package com.backend.pro.model.entity.system.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 科室表
 * @TableName sys_dept
 */
@TableName(value ="sys_dept")
@Data
public class Dept implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 科室描述
     */
    private String deptDesc;

    /**
     * 状态
     */
    private Integer status;

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

    private Long editUserId;

    private Integer type;

    private Integer flag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}