package com.backend.pro.model.vo.ir.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSelectVO {

    /**
     * id
     */
    private Long value;

    /**
     * 输液室名称
     */
    private String label;


}
