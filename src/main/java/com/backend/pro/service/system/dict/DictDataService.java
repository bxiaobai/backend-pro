package com.backend.pro.service.system.dict;

import com.backend.pro.model.dto.system.dict.dictData.DictDataAddRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataUpdateRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author l
* @description 针对表【sys_dict_data(字典表)】的数据库操作Service
* @createDate 2024-12-04 11:26:08
*/
public interface DictDataService extends IService<DictData> {

    /**
     * 创建字典数据
     */
    Long createDictData(DictDataAddRequest createReqVO, HttpServletRequest request);

    /**
     * 更新字典数据
     */
    void updateDictData(DictDataUpdateRequest updateReqVO, HttpServletRequest request);

    /**
     * 删除字典数据
     *
     * @param id 字典数据编号
     */
    void deleteDictData(Long id, HttpServletRequest request);

    /**
     * 获得字典数据详情
     */
    DictDataVO getDictData(Long id);



    /**
     * 获得字典数据分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典数据分页列表
     */
    PageResult<DictDataVO> getDictDataPage(DictDataQueryRequest pageReqVO);

    /**
     * 获得指定数据类型的字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataVO> getDictDataListByDictType(String dictType);


    /**
     * 获得指定数据类型的字典数据数量
     * @param dictType
     * @return
     */
    long getDictDataCountByDictType(String dictType);

}
