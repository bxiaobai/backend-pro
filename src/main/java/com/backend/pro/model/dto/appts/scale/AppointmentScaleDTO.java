package com.backend.pro.model.dto.appts.scale;

import lombok.Data;

import java.util.List;

@Data
public class AppointmentScaleDTO {

    private String question;
    private List<AppointmentScaleDetalisDTO> responses;

    //写一个内部的
    @Data
    public static class AppointmentScaleDetalisDTO{
        private String name;
        private String selectedOption;
        private Integer score;
    }

}
