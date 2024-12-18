package com.backend.pro.mapper.system.medical;

import com.backend.pro.model.entity.system.medical.Medical;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.query;

/**
* @author l
* @description 针对表【sys_medical(医护表)】的数据库操作Mapper
* @createDate 2024-12-10 11:10:25
* @Entity com.backend.pro.model.entity.system.medical.Medical
*/
public interface MedicalMapper extends BaseMapper<Medical> {

    default Medical getByCode(String code){
        return selectOne(new QueryWrapper<Medical>().eq("code" , code));
    }

}




