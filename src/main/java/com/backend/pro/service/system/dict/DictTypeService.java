package com.backend.pro.service.system.dict;

import com.backend.pro.model.dto.system.dict.dictData.DictDataAddRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataUpdateRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeAddRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeQueryRequest;
import com.backend.pro.model.dto.system.dict.dictType.DictTypeUpdateRequest;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.model.vo.system.dict.DictTypeVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author l
* @description 针对表【sys_dict_type(字典值表)】的数据库操作Service
* @createDate 2024-12-04 11:26:08
*/
public interface DictTypeService extends IService<DictType> {

    /**
     * 创建字典数据
     */
    Long createDictData(DictTypeAddRequest createReqVO , HttpServletRequest request);

    /**
     * 更新字典数据
     */
    void updateDictData(DictTypeUpdateRequest updateReqVO , HttpServletRequest request);

    /**
     * 删除字典数据
     * @param id 字典数据编号
     */
    void deleteDictData(Long id ,HttpServletRequest request);

    /**
     * 获得字典数据详情
     */
    DictTypeVO getDictData(Long id);


    /**
     * 获得字典数据分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典数据分页列表
     */
    PageResult<DictTypeVO> getDictDataPage(DictTypeQueryRequest pageReqVO);

    /**
     * 获得指定数据类型的字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictTypeVO> getDictDataListByDictType(String dictType);


    /**
     * 根据类型查询详情
     * @param type
     * @return
     */
    DictType getDictType(String type);
}
