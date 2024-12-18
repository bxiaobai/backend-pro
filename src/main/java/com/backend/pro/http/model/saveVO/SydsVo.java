package com.backend.pro.http.model.saveVO;

import com.backend.pro.http.model.ir.Yzxxs;
import lombok.Data;

import java.util.List;

@Data
public class SydsVo {
    private List<Yzxxs> Yzxxs;
    private String kbbz;
    private String ps;
}
