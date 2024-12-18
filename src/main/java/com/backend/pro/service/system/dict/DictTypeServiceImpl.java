package com.backend.pro.service.system.dict;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.sql.QueryRequest;
import com.backend.pro.exception.ThrowUtils;
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
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.mapper.system.dict.DictTypeMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【sys_dict_type(字典值表)】的数据库操作Service实现
 * @createDate 2024-12-04 11:26:08
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType>
        implements DictTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private UserService userService;

    @Override
    public Long createDictData(DictTypeAddRequest createReqVO, HttpServletRequest request) {
        //检验类型是否唯一
        validateDictTypeUnique(null, createReqVO.getDictType());
        //检验名称是否唯一
        validateDictNameUnique(null, createReqVO.getDictName());
        //插入数据
        DictType dictType = BeanUtil.toBean(createReqVO, DictType.class);
        //获取登录用户
        Long id = userService.getLoginUser(request).getId();
        //创建用户id
        dictType.setUserId(id);
        //更新用户id一起放入
        dictType.setEditUserId(id);
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictData(DictTypeUpdateRequest updateReqVO, HttpServletRequest request) {
        //检验数据是否存在
        validateDictTypeExists(updateReqVO.getId());
        //检验类型是否唯一
        validateDictTypeUnique(updateReqVO.getId(), updateReqVO.getDictType());
        //检验名称是否唯一
        validateDictNameUnique(updateReqVO.getId(), updateReqVO.getDictName());
        //更新
        DictType updateObj = BeanUtil.toBean(updateReqVO, DictType.class);
        //更新者
        updateObj.setEditUserId(userService.getLoginUser(request).getId());
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id, HttpServletRequest request) {
        //检验数据是否存在
        DictType dictType = validateDictTypeExists(id);
        // 校验是否有字典数据
        long dictDataCountByDictType = dictDataService.getDictDataCountByDictType(dictType.getDictType());
        ThrowUtils.throwIf(dictDataCountByDictType > 0, SYSTEM_DICT_HAS_CHILDREN);
        // 删除字典类型
        dictTypeMapper.deleteById(id);
    }

    @Override
    public DictTypeVO getDictData(Long id) {
        return BeanUtil.toBean(dictTypeMapper.selectById(id), DictTypeVO.class);
    }

    @Override
    public PageResult<DictTypeVO> getDictDataPage(DictTypeQueryRequest pageReqVO) {
        return dictTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DictTypeVO> getDictDataListByDictType(String dictType) {
        List<DictType> dictTypes = dictTypeMapper.selectListByType(dictType);
       return BeanUtils.toBean(dictTypes, DictTypeVO.class);
    }

    /**
     * 校验类型是否唯一
     */
    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictType dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_DICT_EXISTS);
        ThrowUtils.throwIf(!dictType.getId().equals(id), SYSTEM_DICT_EXISTS);
    }

    /**
     * 校验名称是否唯一
     */
    @VisibleForTesting
    void validateDictNameUnique(Long id, String name) {
        if (StrUtil.isEmpty(name)) {
            return;
        }
        DictType dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_DICT_EXISTS);
        ThrowUtils.throwIf(!dictType.getId().equals(id), SYSTEM_DICT_EXISTS);
    }

    /**
     * 检验字典类型是否存在
     */
    @VisibleForTesting
    DictType validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictType dictData = dictTypeMapper.selectById(id);
        ThrowUtils.throwIf(dictData == null, NOT_FOUND_ERROR);
        return dictData;
    }


    @Override
    public DictType getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }
}




