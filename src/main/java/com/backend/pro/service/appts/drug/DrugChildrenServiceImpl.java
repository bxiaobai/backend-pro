package com.backend.pro.service.appts.drug;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.appts.DrugChildren;
import com.backend.pro.mapper.appts.DrugChildrenMapper;
import org.springframework.stereotype.Service;

/**
* @author l
* @description 针对表【appts_drug_children(预约药品子表)】的数据库操作Service实现
* @createDate 2024-12-09 18:03:34
*/
@Service
public class DrugChildrenServiceImpl extends ServiceImpl<DrugChildrenMapper, DrugChildren>
    implements DrugChildrenService{

}




