package com.backend.pro.model.dto.ir.seat;

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
public class SeatQueryRequest extends PageRequest implements Serializable {

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 可接待患者类型
     */
    private Integer patType;

    /**
     * 座位状态
     */
    private Integer status;

    /**
     * 区域
     */
    private String area;

    /**
     * 标签(vip(vip),common(普通),bed(床为))
     */
    private String flag;

}