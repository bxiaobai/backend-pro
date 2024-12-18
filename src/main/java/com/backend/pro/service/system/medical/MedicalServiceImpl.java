package com.backend.pro.service.system.medical;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.system.medical.Medical;
import com.backend.pro.service.system.medical.MedicalService;
import com.backend.pro.mapper.system.medical.MedicalMapper;
import org.springframework.stereotype.Service;

/**
* @author l
* @description 针对表【sys_medical(医护表)】的数据库操作Service实现
* @createDate 2024-12-10 11:10:25
*/
@Service
public class MedicalServiceImpl extends ServiceImpl<MedicalMapper, Medical>
    implements MedicalService{

}




