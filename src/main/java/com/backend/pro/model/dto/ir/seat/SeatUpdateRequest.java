package com.backend.pro.model.dto.ir.seat;

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
public class SeatUpdateRequest implements Serializable {

    private Long id;
    /**
     * 输液室id
     */
    @NotNull(message = "必须选择输液室")
    private Long irId;

    /**
     * 座位号
     */
    @NotNull(message = "座位号必填")
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
    @NotNull(message = "区域必填")
    private String area;

    /**
     * 行号
     */
    @NotNull(message = "座位所在行号必填")
    private Integer seatRow;

    /**
     * 列号
     */
    @NotNull(message = "座位所在列号必填")
    private Integer seatCol;

    /**
     * 标签(vip(vip),common(普通),bed(床为))
     */
    @NotNull(message = "必须选择标签")
    private String flag;
}