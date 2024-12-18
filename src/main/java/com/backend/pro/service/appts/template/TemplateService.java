package com.backend.pro.service.appts.template;

import com.backend.pro.model.dto.appts.template.TemplateAddRequest;
import com.backend.pro.model.dto.appts.template.TemplateQueryRequest;
import com.backend.pro.model.dto.appts.template.TemplateUpdateRequest;
import com.backend.pro.model.entity.appts.Template;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author l
 * @description 针对表【appts_template(号源配置表)】的数据库操作Service
 * @createDate 2024-12-09 11:42:31
 */

public interface TemplateService extends IService<Template> {
    /**
     * 创建一个新的模板
     *
     * @param template 添加模板的请求对象，包含模板的基本信息
     * @param request HTTP请求对象，用于获取请求相关的上下文信息
     * @return 返回新创建的模板的ID
     */
    Long createTemplate(TemplateAddRequest template, HttpServletRequest request);

    /**
     * 更新现有模板的信息
     *
     * @param template 更新模板的请求对象，包含需要更新的模板信息
     * @param request HTTP请求对象，用于获取请求相关的上下文信息
     */
    void updateTemplate(TemplateUpdateRequest template, HttpServletRequest request);

    /**
     * 删除指定ID的模板
     *
     * @param id 要删除的模板的ID
     * @param request HTTP请求对象，用于获取请求相关的上下文信息
     */
    void deleteTemplate(Long id, HttpServletRequest request);

    /**
     * 根据模板ID获取模板的详细信息
     *
     * @param id 模板的ID
     * @return 返回模板的详细信息对象
     */
    TemplateVO getTemplateById(Long id);

    /**
     * 根据IR ID列出所有关联的模板信息
     *
     * @param irId IR的ID，用于查询关联的模板
     * @return 返回关联的模板信息列表
     */
    List<TemplateVO> listTemplateListByIrId(Long irId);

    /**
     * 列出模板的分页信息根据查询请求
     *
     * @param templateQueryRequest 模板查询请求对象，包含查询条件和分页信息
     * @return 返回查询结果的分页对象
     */
    PageResult<TemplateVO> listTemplatePage(TemplateQueryRequest templateQueryRequest);
}
