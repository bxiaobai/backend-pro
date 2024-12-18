package com.backend.pro.model.dto.Med.pat;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 更新字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class PatUpdateRequest implements Serializable {

    private Long id;
    /**
     * 出生日期
     */
    private Date birthday;

    private String phone;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 通讯地址
     */
    private String contactAddress;

    /**
     * 患者卡号
     */
    private String card;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 婚姻代码
     */
    private Integer marriageCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String nameSpell;

    /**
     * 户籍所在地
     */
    private String registedResidenceAddress;

    /**
     * 性别代码
     */
    private Integer sexCode;

    /**
     * 体重
     */
    private Integer weight;

    /**
     * 年龄
     */
    private String displayAge;

    /**
     * 创建用户 id
     */
    private Long userId;
}