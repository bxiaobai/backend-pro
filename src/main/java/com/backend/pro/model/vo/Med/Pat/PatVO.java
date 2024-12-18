package com.backend.pro.model.vo.Med.Pat;

import lombok.Data;

import java.util.Date;

@Data
public class PatVO {

    private Long id;
    /**
     * 出生日期
     */
    private Date birthday;

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

    private String phone;

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



    private Date createTime;

}
