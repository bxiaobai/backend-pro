package com.backend.pro.model.entity.appts;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import lombok.Data;

/**
 * 号源库
 * @TableName appts_source
 */
@TableName(value ="appts_source")
@Data
public class Source implements Serializable {
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
     * 正常号源已用数量
     */
    private Integer normalUsedNum;

    /**
     * 临时号源总量
     */
    private Integer tempNum;

    /**
     * 临时号源已用数量
     */
    private Integer tempUsedNum;

    /**
     * 已约数
     */
    private Integer appointNum;

    /**
     * 修改人id
     */
    private Long editUserId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 号源状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //通过模板构建一个号源对象
    public static Source buildSource(TemplateVO template, Date date) {
        Source source = new Source();
        source.setIrId(template.getIrId());
        source.setDate(date);
        source.setStartTime(template.getStartTime());
        source.setEndTime(template.getEndTime());
        source.setNormalNum(template.getNormalNum());
        source.setTempNum(template.getTempNum());
        return source;
    }

}