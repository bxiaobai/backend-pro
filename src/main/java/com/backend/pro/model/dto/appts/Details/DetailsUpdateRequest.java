package com.backend.pro.model.dto.appts.Details;

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
public class DetailsUpdateRequest implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 输液室名称
     */
    @NotNull(message = "名称不能为空")
    private String irName;

    /**
     * 描述
     */
    private String irDesc;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 短信模板id
     */
    private String smsTemplateId;

    /**
     * 电话
     */
    private String phone;

    /**
     * 取号点
     */
    private String numberPoint;

    /**
     * 输液地点
     */
    private String irPlace;

    /**
     * 可接待患者类型
     */
    private Integer patType;

}