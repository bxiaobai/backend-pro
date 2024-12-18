package com.backend.pro.service.appts.drug;

import com.backend.pro.model.entity.appts.DrugChildren;
import com.backend.pro.service.system.user.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.appts.Drug;
import com.backend.pro.mapper.appts.DrugMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author l
 * @description 针对表【appts_drug(预约药品表)】的数据库操作Service实现
 * @createDate 2024-12-09 18:03:34
 */
@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug>
        implements DrugService {
    @Resource
    private DrugMapper drugMapper;
    @Resource
    private DrugChildrenService drugChildrenService;

    @Override
    @Transactional
    public Long createDrug(Drug drug, HttpServletRequest request) {
        //获取添加的子药品
        List<DrugChildren> drugChildren = drug.getDrugChildren();

        //添加主表
        drugMapper.insert(drug);
        //添加子表
        addrDrugChildren(drugChildren, drug.getId());
        return drug.getId();
    }


    void addrDrugChildren(List<DrugChildren> drugChildren, Long id) {
        List<DrugChildren> list = drugChildren.stream()
                .peek(children -> children.setDrugId(id))
                .collect(Collectors.toList());
        drugChildrenService.saveBatch(list);
    }


    @Override
    public Drug getDrugById(Long id) {
        //查询主表
        Drug drug = drugMapper.selectById(id);
        if (drug != null){
            List<DrugChildren> childrenList = drugChildrenService.list(new LambdaQueryWrapper<DrugChildren>().eq(DrugChildren::getDrugId, id));
            drug.setDrugChildren(childrenList);
        }
        return drug;
    }

    @Override
    public Drug getDrugByDetailsId(Long id) {
        Drug drug = drugMapper.selectOne(new QueryWrapper<Drug>().eq("detailsId", id));
        if (drug == null){
            return null;
        }
        //根据drug查询子表
        List<DrugChildren> drugId = drugChildrenService.list(new QueryWrapper<DrugChildren>().eq("drugId", drug.getId()));
        drug.setDrugChildren(drugId);
        return drug;
    }

}




