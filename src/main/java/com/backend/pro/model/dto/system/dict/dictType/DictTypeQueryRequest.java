package com.backend.pro.model.dto.system.dict.dictType;

import com.backend.pro.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeQueryRequest extends PageRequest implements Serializable {


    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典状态
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}