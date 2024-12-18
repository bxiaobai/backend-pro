package com.backend.pro.http.model.pat;

import lombok.Data;

@Data
public class DrugRoutineApply {
    private String OrderBatchNo;
    private String DrugRoutineApplyNo;
    private String DrugID;
    private int DrugTotalNumType;
    private int DrugType;
    private String FrequencyCode;
    private String HealUsageCode;
    private boolean IsDelivery;
    private String InfusionApplyNo;
    private boolean IsInfusion;
    private boolean IsOutsourcing;
    private String ContactRelationshipPhone;
    private String ContactRelationshipAddress;
    private String HomeZipCode;
    private String RelationshipName;
    private String OrderGroupNo;
    private int OrderGroupSort;
    private int PackageCoefficient;
    private double SignDrugNum;
    private String SignDrugUnit;
    private int SignDrugUnitType;
    private String TakeDrugPosition;
    private String TakeDrugRate;
    private String TakeDrugRateValue;
    private int SkinTestType;
    private double TotalSignDrugNum;
    private String TotalSignDrugUnit;
    private int TotalSignDrugUnitType;
    private String SpecialFlowDeptID;
    private String Remark;
    private int DrugApplyState;
    private int InfusionDays;
    private int InfusionNum;
    private long ChargeItemID;

    // getters and setters
}