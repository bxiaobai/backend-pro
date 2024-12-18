package com.backend.pro.http.model.pat;

import lombok.Data;

@Data
public class OrderResponse {
    private int result;
    private PatientInfo body;
    private String msgInfo;
    private boolean IsSucceed;

    // getters and setters
}
