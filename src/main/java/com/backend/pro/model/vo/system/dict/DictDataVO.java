package com.backend.pro.model.vo.system.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class DictDataVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标签
     */
    private String label;

    /**
     * 键值
     */
    private String value;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 字典类型
     */
    private String dictType;


    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
