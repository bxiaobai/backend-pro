package com.backend.pro.service.system.dict;

import cn.hutool.core.util.StrUtil;
import com.backend.pro.common.CommonStatusEnum;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.mapper.system.dict.DictTypeMapper;
import com.backend.pro.model.dto.system.dict.dictData.DictDataAddRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataQueryRequest;
import com.backend.pro.model.dto.system.dict.dictData.DictDataUpdateRequest;
import com.backend.pro.model.entity.system.dict.DictData;
import com.backend.pro.model.entity.system.dict.DictType;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.system.dict.DictDataVO;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.mapper.system.dict.DictDataMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【sys_dict_data(字典表)】的数据库操作Service实现
 * @createDate 2024-12-04 11:26:08
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
        implements DictDataService {

    @Resource
    private DictDataMapper dictDataMapper;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private UserService userService;

    /**
     * 创建字典
     */
    @Override
    public Long createDictData(DictDataAddRequest createReqVO, HttpServletRequest request) {
        // 校验字典类型有效
        validateDictTypeExists(createReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(null, createReqVO.getDictType(), createReqVO.getValue());
        //检验名称是否唯一
        // 插入字典类型
        DictData dictData = BeanUtils.toBean(createReqVO, DictData.class);
        User loginUser = userService.getLoginUser(request);
        dictData.setUserId(loginUser.getId());
        dictData.setEditUserId(loginUser.getId());
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateRequest updateReqVO, HttpServletRequest request) {
        // 校验自己存在
        validateDictDataExists(updateReqVO.getId());
        // 校验字典类型有效
        validateDictTypeExists(updateReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(updateReqVO.getId(), updateReqVO.getDictType(), updateReqVO.getValue());
        // 更新字典类型
        DictData updateObj = BeanUtils.toBean(updateReqVO, DictData.class);
        User loginUser = userService.getLoginUser(request);
        updateObj.setEditUserId(loginUser.getId());
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id, HttpServletRequest request) {
        validateDictDataExists(id);
        dictDataMapper.deleteById(id);
    }

    @Override
    public DictDataVO getDictData(Long id) {
        return BeanUtils.toBean(dictDataMapper.selectById(id), DictDataVO.class);
    }


    @Override
    public PageResult<DictDataVO> getDictDataPage(DictDataQueryRequest pageReqVO) {
        return dictDataMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DictDataVO> getDictDataListByDictType(String dictType) {
        return dictDataMapper.selectListByTypeList(dictType);
    }

    @Override
    public long getDictDataCountByDictType(String dictType) {
        return dictDataMapper.selectByTypeCount(dictType);
    }

    /**
     * 校验字典类型是否重复
     */
    @VisibleForTesting
    void validateDictDataValueUnique(Long id, String type, String value) {
        DictData dictData = dictDataMapper.selectTypeAndValue(type, value);
        if (dictData == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_DICT_EXISTS);
        ThrowUtils.throwIf(!dictData.getId().equals(id), SYSTEM_DICT_EXISTS);
    }

    /**
     * 检验字典类型是否存在
     */
    @VisibleForTesting
    void validateDictDataExists(Long id) {
        DictData dictData = dictDataMapper.selectById(id);
        ThrowUtils.throwIf(dictData == null, NOT_FOUND_ERROR);
    }

    @VisibleForTesting
    public void validateDictTypeExists(String type) {
        DictType dictType = dictTypeMapper.selectByType(type);
        ThrowUtils.throwIf(dictType == null, DICT_TYPE_NOT_EXISTS);
        ThrowUtils.throwIf(!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus()), DICT_TYPE_NOT_EXISTS);

    }

}




