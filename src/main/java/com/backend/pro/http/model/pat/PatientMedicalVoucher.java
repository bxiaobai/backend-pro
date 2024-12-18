package com.backend.pro.http.model.pat;

import lombok.Data;

import java.util.Date;

/**
 * 医疗凭证类
 */
@Data
public class PatientMedicalVoucher {
    /**
     * 唯一标识ID
     */
    private Long id;

    /**
     * 患者ID
     */
    private Long patientID;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 医疗类型代码
     */
    private Integer medicalTypeCode;

    /**
     * 医疗编号
     */
    private String medicalNumber;

    /**
     * 发卡机构代码
     */
    private String cardIssuerCode;

    /**
     * 是否有效
     */
    private Boolean isValid;

    /**
     * 操作员ID
     */
    private Long operatorID;

    /**
     * 特殊标签
     */
    private String specialTag;

    /**
     * 操作时间
     */
    private Date operationTime;

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
    private Boolean isDelete;
}
