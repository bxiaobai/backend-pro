package com.backend.pro.model.dto.system.dict.dictData;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
public class DictDataAddRequest  {

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