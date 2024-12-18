package com.backend.pro.model.dto.appts.Source;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.backend.pro.common.PageRequest;
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
public class SourceQueryRequest extends PageRequest implements Serializable {

    /**
     * 输液室名称
     */
    private Long irId;

    private Date date;

    private Integer type;

}