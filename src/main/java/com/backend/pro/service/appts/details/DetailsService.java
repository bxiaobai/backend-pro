package com.backend.pro.service.appts.details;

import com.backend.pro.http.model.ir.Yzxxs;
import com.backend.pro.model.dto.appts.Details.AutoRequest;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import com.backend.pro.model.dto.appts.Details.DetailsQueryRequest;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.vo.appts.AutoVO;
import com.backend.pro.model.vo.appts.DetailsVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author l
* @description 针对表【appts_details(号源库)】的数据库操作Service
* @createDate 2024-12-09 17:37:24
*/
public interface DetailsService extends IService<Details> {

    /**
     * 创建详情信息
     *
     * @param details     待添加的详情信息对象
     * @param request     HTTP请求对象，可用于获取请求相关的信息
     * @return            返回新创建的详情信息的主键ID
     */
    Long createDetails(DetailsAddRequest details , HttpServletRequest request);

    /**
     * 取消详情信息
     *
     * @param id          需要取消的详情信息的主键ID
     */
    void cancelDetails(Long id ,HttpServletRequest request);

    /**
     * 根据ID获取详情信息
     *
     * @param id          需要查询的详情信息的主键ID
     * @return            返回对应的详情信息对象
     */
    DetailsVO getDetailsById(Long id);


    /**
     * 分页查询详情信息
     * @return
     */
    PageResult<DetailsVO> listDetailsPage(DetailsQueryRequest detailsQueryRequest , HttpServletRequest request);


    /**
     * 获取预约详情
     */
//    DetailsVO getDetails(Long id);

    /**
     * 计算药品所需时间
     */
    Integer countDrugTime(List<Yzxxs> list);

    /**
     * 自动选择时间和座位
     */
    AutoVO autoSelect(AutoRequest addIrVO);
}
