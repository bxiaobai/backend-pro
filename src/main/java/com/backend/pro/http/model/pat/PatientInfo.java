package com.backend.pro.http.model.pat;

import lombok.Data;

import java.util.List;

/**
 * 患者信息类
 */
@Data
public class PatientInfo {
    /**
     * 患者唯一标识ID
     */
    private String id;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 联系地址
     */
    private String contactAddress;


    /**
     * 身高（单位：厘米）
     */
    private Integer height;

    /**
     * 婚姻状态代码
     */
    private Integer marriageCode;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 患者姓名拼音
     */
    private String nameSpell;

    /**
     * 户籍地址
     */
    private String registedResidenceAddress;

    /**
     * 性别代码
     */
    private Integer sexCode;

    /**
     * 体重（单位：公斤）
     */
    private Integer weight;

    /**
     * 显示年龄
     */
    private String displayAge;
    private List<PatientMedicalVoucher> patientMedicalVoucherList;

}
