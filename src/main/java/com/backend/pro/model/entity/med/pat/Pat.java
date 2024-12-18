package com.backend.pro.model.entity.med.pat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName med_pat
 */
@TableName(value ="med_pat")
@Data
public class Pat implements Serializable {
    /**
     * 主键
     */
    @TableId
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private String phone;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}