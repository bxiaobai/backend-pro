package com.backend.pro.model.dto.appts.Details;

import com.backend.pro.common.PageRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DetailsQueryRequest extends PageRequest implements Serializable {


    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 卡号
     */
    private String card;

    /**
     * 日期
     */
    private String date;

    /**
     * 预约时间
     */
    private String time;

    /**
     * 座位号
     */
    private String seatNum;


    /**
     * 号源类型(0正常号源,1临时号源)
     */
    private Integer type;

    /**
     * 状态(0未分配座位,1已分配座位,2已完成,3进行中)
     */
    private Integer status;


    /**
     * 创建时间
     */
    private Date createTime;


}