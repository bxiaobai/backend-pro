package com.backend.pro.model.dto.system.dict.dictData;

import com.backend.pro.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataQueryRequest extends PageRequest implements Serializable {

    /**
     * 标签
     */
    private String label;

    /**
     * 状态
     */
    private Integer status;

}