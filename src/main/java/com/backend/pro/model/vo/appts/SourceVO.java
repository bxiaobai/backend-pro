package com.backend.pro.model.vo.appts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 号源库
 * @TableName appts_source
 */
@Data
public class SourceVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 正常号源总量
     */
    private Integer normalNum;

    /**
     * 临时号源总量
     */
    private Integer tempNum;

    /**
     * 已约数
     */
    private Integer appointNum;

    /**
     * 号源类型
     */
    private Integer type;

}