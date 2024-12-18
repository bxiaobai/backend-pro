package com.backend.pro.service.appts.source;

import com.backend.pro.model.dto.appts.Source.DoctorQueryRequest;
import com.backend.pro.model.dto.appts.Source.SourceQueryRequest;
import com.backend.pro.model.entity.appts.Source;
import com.backend.pro.model.vo.appts.SourceApptsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author l
 * @description 针对表【appts_source(号源库)】的数据库操作Service
 * @createDate 2024-12-09 11:42:31
 */
public interface SourceService extends IService<Source> {

    /**
     * 创建一个新的号源集合
     *
     * @param irId    关联的机构或资源ID，用于确定号源的归属
     * @param data    号源数据列表，包含需要创建的号源的具体信息
     * @param request HTTP请求对象，用于获取请求相关的上下文信息
     * @return 返回新创建的号源的ID，用于后续的查询或更新操作
     */
    void createSource(Long irId, List<String> data, HttpServletRequest request);

    /**
     * 根据指定的机构ID和日期列表获取号源信息
     *
     * @param sourceUpdateRequest 包含机构ID和日期列表的请求对象，用于筛选号源
     * @param request             HTTP请求对象，用于获取请求相关的上下文信息
     * @return 返回一个SourceApptsVO对象列表，每个对象包含特定日期和机构的号源信息
     */
    List<SourceApptsVO> listDateAndIrId(SourceQueryRequest sourceUpdateRequest, HttpServletRequest request);

    /**
     * 更新指定号源的信息
     *
     * @param source  需要更新的号源对象，包含号源的最新信息
     * @param request HTTP请求对象，用于获取请求相关的上下文信息
     */
    void updateSource(Source source, Long request);

    /**
     * 根据条件获取源ID列表
     *
     * @param irId      用于查询的IR ID，作为筛选条件之一
     * @return 返回一个List集合，包含符合筛选条件的Long类型源ID
     */
    List<Source> listSourceId(Long irId, String date);





}
