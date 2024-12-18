package com.backend.pro.model.dto.appts.Source;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 更新字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class SourceUpdateRequest implements Serializable {
    private Long id;

    private Long irId;

    private Date date;

    private List<String> times;

}