package com.backend.pro.model.vo.system.dict;

import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 字典类型视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class SysDictDataVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

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
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 创建用户信息
     */
    private UserVO user;

    /**
     * 封装类转对象
     *
     * @param sysDictDataVO
     * @return
     */
    public static DictData voToObj(SysDictDataVO sysDictDataVO) {
        if (sysDictDataVO == null) {
            return null;
        }
        DictData sysDictData = new DictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        List<String> tagList = sysDictDataVO.getTagList();
        return sysDictData;
    }

    /**
     * 对象转封装类
     *
     * @param sysDictData
     * @return
     */
    public static SysDictDataVO objToVo(DictData sysDictData) {
        if (sysDictData == null) {
            return null;
        }
        SysDictDataVO sysDictDataVO = new SysDictDataVO();
        BeanUtils.copyProperties(sysDictData, sysDictDataVO);
        return sysDictDataVO;
    }
}
