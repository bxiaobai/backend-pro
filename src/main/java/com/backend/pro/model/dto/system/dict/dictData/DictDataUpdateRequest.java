package com.backend.pro.model.dto.system.dict.dictData;

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
public class DictDataUpdateRequest implements Serializable {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;


    /**
     * 标签
     */
    @NotNull(message = "标签不能为空")
    private String label;

    /**
     * 键值
     */
    @NotNull(message = "键值不能为空")
    private String value;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 字典类型
     */
    @NotNull(message = "字典类型不能为空")
    private String dictType;

}