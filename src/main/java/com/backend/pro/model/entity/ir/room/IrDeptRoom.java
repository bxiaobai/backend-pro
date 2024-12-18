package com.backend.pro.model.entity.ir.room;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预约药品表
 * @TableName ir_drug_pool
 */
@TableName(value ="ir_dept_room")
@Data
public class IrDeptRoom implements Serializable {
    /**
     * 科室id
     */
    @TableId("deptId")
    private Long deptId;

    /**
     * 输液室id
     */
    private Long roomId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}