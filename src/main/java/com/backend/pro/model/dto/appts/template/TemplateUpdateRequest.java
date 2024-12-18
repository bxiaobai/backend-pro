package com.backend.pro.model.dto.appts.template;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 更新字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class TemplateUpdateRequest implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 输液室id
     */
    @NotNull(message = "输液室不能为空")
    private Long irId;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Time startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Time endTime;

    /**
     * 可接待患者类型
     */
    @NotNull(message = "可接待患者类型不能为空")
    private Integer patType;

    /**
     * 正常号源总量
     */
    @NotNull(message = "正常号源总量不能为空")
    private Integer normalNum;

    /**
     * 临时号源总量
     */
    @NotNull(message = "临时号源总量不能为空")
    private Integer tempNum;

}