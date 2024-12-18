package com.backend.pro.model.dto.system.dept;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.backend.pro.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
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
public class DeptQueryRequest extends PageRequest implements Serializable {


    private String deptName;

    private Long parentId;

    private Date createTime;

    private Integer status;


}