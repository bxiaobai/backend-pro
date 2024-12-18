package com.backend.pro.model.dto.system.dict.dictType;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 创建字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class DictTypeAddRequest implements Serializable {

    /**
     * 字典名称
     */
    @NotNull(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典类型名称
     */
    @NotNull(message = "字典类型名称不能为空")
    private String dictType;

    /**
     * 字典状态
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}