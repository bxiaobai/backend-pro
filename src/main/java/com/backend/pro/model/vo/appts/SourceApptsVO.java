package com.backend.pro.model.vo.appts;

import lombok.Data;

@Data
public class SourceApptsVO {
    private Long id;
    private String time;
    //临时号源
    private Integer tempNum;
    //正常号源
    private Integer normalNum;
    //正常号源用量
    private Integer normalUsedNum;
    //临时号源用量
    private Integer tempUsedNum;
    //输液室
    private String irId;

}
