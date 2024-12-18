package com.backend.pro.model.dto.ir.room;

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
public class RoomQueryRequest extends PageRequest implements Serializable {

    /**
     * 输液室名称
     */
    private String irName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 可接待患者类型
     */
    private Integer patType;

}