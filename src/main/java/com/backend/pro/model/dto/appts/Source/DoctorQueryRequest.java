package com.backend.pro.model.dto.appts.Source;

import lombok.Data;

@Data
public class DoctorQueryRequest {

    private String irId;

    private String date;

    private String patCard;

    private Long userId;
}
