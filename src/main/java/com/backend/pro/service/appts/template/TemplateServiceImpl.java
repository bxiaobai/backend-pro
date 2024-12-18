package com.backend.pro.service.appts.template;

import cn.hutool.core.bean.BeanUtil;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.model.dto.appts.template.TemplateAddRequest;
import com.backend.pro.model.dto.appts.template.TemplateQueryRequest;
import com.backend.pro.model.dto.appts.template.TemplateUpdateRequest;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.appts.Template;
import com.backend.pro.mapper.appts.TemplateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【appts_template(号源配置表)】的数据库操作Service实现
 * @createDate 2024-12-09 11:42:31
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
        implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private UserService userService;


    @Override
    public Long createTemplate(TemplateAddRequest templateAddRequest, HttpServletRequest request) {
        Template template = BeanUtil.toBean(templateAddRequest, Template.class);
        //检测是否有重复
        validateTimeAndIrIdTemplate(null,template);
        User loginUser = userService.getLoginUser(request);
        template.setUserId(loginUser.getId());
        template.setEditUserId(loginUser.getId());
        //添加
        templateMapper.insert(template);
        return template.getId();
    }

    @Override
    public void updateTemplate(TemplateUpdateRequest templateUpdateRequest, HttpServletRequest request) {
        Template template = BeanUtil.toBean(templateUpdateRequest, Template.class);
        //检测是否存在
        validateTemplateExist(template.getId());
        //检测是否有重复
        validateTimeAndIrIdTemplate(template.getId(),template);
        template.setEditUserId(userService.getLoginUser(request).getId());
        templateMapper.updateById(template);
    }

    @Override
    public void deleteTemplate(Long id, HttpServletRequest request) {
        //检测是否存在
        validateTemplateExist(id);
        //删除
        templateMapper.deleteById(id);
    }

    @Override
    public TemplateVO getTemplateById(Long id) {
        return BeanUtil.toBean(templateMapper.selectById(id), TemplateVO.class);
    }

    @Override
    public List<TemplateVO> listTemplateListByIrId(Long irId) {
        return templateMapper.listTemplateListByIrId(irId);
    }

    @Override
    public PageResult<TemplateVO> listTemplatePage(TemplateQueryRequest templateQueryRequest) {
        return templateMapper.selectPage(templateQueryRequest);
    }

    //检验输液室是否有大于开始时间和小于结束时间的数据
    void validateTimeAndIrIdTemplate(Long id, Template templateVO) {
        Template template = templateMapper.getTimeAndIrId(templateVO);
        if (template == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, TEMPLATE_EXISTS);
        ThrowUtils.throwIf(!template.getId().equals(id), TEMPLATE_EXISTS);
    }

    //检验是否存在
    void validateTemplateExist(Long id) {
        Template template = templateMapper.selectById(id);
        ThrowUtils.throwIf(template == null, NOT_FOUND_ERROR);
    }
}




