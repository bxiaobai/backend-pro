package com.backend.pro.http.model.pat;

import lombok.Data;

import java.util.List;

@Data
public class OrderData {
    private String RegisterID;
    private String PatientSourceID;
    private String OrderBatchNo;
    private String OrderName;
    private int OrderBusinessType;
    private String OrderRemark;
    private String InputSort;
    private int OrderType;
    private int InputType;
    private int GenerationSource;
    private String CreateDeptID;
    private String CreaterID;
    private String CreateTime;
    private List<DrugRoutineApply> drugRoutineApplyRespDtos;
    // getters and setters
}
