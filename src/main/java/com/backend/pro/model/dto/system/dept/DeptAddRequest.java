package com.backend.pro.model.dto.system.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 创建字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class DeptAddRequest {

    /**
     * 科室名称
     */
    @NotNull(message = "科室名称不能为空")
    private String deptName;

    /**
     * 父级id
     */
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    /**
     * 科室描述
     */
    private String deptDesc;

    private Integer flag;

    private Integer type;
    private Integer status;


}