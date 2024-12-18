package com.backend.pro.model.dto.appts.Source;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 创建字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class SourceAddRequest {


    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 开始日期
     */
    private List<String> dates;


}