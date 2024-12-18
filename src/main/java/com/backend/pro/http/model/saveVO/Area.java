package com.backend.pro.http.model.saveVO;

import lombok.Data;

@Data
public class Area {
    private String AreaName;
    private int Id;

    public static Area build(String areaName, Long id){
        Area area = new Area();
        area.setAreaName(areaName);
        return area;
    }
}
