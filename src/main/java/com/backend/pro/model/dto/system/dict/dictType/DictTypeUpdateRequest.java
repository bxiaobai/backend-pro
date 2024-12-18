package com.backend.pro.model.dto.system.dict.dictType;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class DictTypeUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}