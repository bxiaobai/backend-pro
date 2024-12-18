package com.backend.pro.model.vo.system.dict;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Data
public class DictTypeVO {



    /**
     * id
     */
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

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  createTime;


}
