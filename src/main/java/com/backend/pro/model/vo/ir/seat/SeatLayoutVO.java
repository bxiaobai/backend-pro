package com.backend.pro.model.vo.ir.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatLayoutVO {

    private String area;

    private List<SeatVO> children;

}
