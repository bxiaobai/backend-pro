package com.backend.pro.model.vo.system.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeptVO implements Serializable {

    /**
     * id
     */
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



    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;


}
