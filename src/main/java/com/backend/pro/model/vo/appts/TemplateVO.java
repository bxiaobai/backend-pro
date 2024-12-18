package com.backend.pro.model.vo.appts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class TemplateVO {

    /**
     * id
     */
    private Long id;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 开始时间
     */
    private Time startTime;

    /**
     * 结束时间
     */
    private Time endTime;

    /**
     * 可接待患者类型
     */
    private Integer patType;

    /**
     * 正常号源总量
     */
    private Integer normalNum;

    /**
     * 临时号源总量
     */
    private Integer tempNum;

    /**
     * 创建时间
     */
    private Date createTime;


}
