package com.backend.pro.http.model.saveVO;

import lombok.Data;

@Data
public class Bed {
    private String BedName;
    private int Id;

    public static Bed build(String seatNum) {
        Bed bed = new Bed();
        bed.setBedName(seatNum);
        return bed;
    }
}