package com.backend.pro.service.system.dept;

import com.backend.pro.model.dto.system.dept.DeptAddRequest;
import com.backend.pro.model.dto.system.dept.DeptQueryRequest;
import com.backend.pro.model.dto.system.dept.DeptUpdateRequest;
import com.backend.pro.model.entity.system.dept.Dept;
import com.backend.pro.model.vo.system.dept.DeptTreeVO;
import com.backend.pro.model.vo.system.dept.DeptVO;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author l
* @description 针对表【sys_dept(科室表)】的数据库操作Service
* @createDate 2024-12-04 19:17:01
*/
/**
 * 科室服务接口，继承自通用服务接口IService
 */
public interface DeptService extends IService<Dept> {

    /**
     * 新增科室
     * @param createReqVO 科室添加请求对象，包含需要添加的科室信息
     * @param request HTTP请求对象，用于获取请求相关的信息
     * @return 返回新增科室的ID
     */
    Long createDept(DeptAddRequest createReqVO, HttpServletRequest request);

    /**
     * 删除科室
     * @param id 需要删除的科室的ID
     * @param request HTTP请求对象，用于获取请求相关的信息
     */
    void deleteDept(Long id, HttpServletRequest request);

    /**
     * 更新科室信息
     * @param updateReqVO 包含更新信息的科室请求对象
     * @param request HTTP请求对象，用于获取请求相关的信息
     */
    void updateDept(DeptUpdateRequest updateReqVO, HttpServletRequest request);

    /**
     * 获取科室详细信息
     * @param id 需要获取信息的科室的ID
     * @return 返回科室信息对象DeptVO
     */
    DeptVO getDept(Long id);

    /**
     * 分页查询科室信息
     * @param pageReqVO 包含分页查询条件的请求对象
     * @return 返回分页查询结果，包含科室信息列表和分页信息
     */
    PageResult<DeptVO> listDeptPage(DeptQueryRequest pageReqVO);

    /**
     * 查询所有科室信息
     * @return 返回所有科室信息的列表
     */
    List<DeptVO> listDept();


    /**
     * 获取科室树形结构
     * @return
     */
    List<DeptTreeVO> listDeptTree();

    /**
     * 查询只到院室的菜单
     * @return
     */
    List<DeptTreeVO> listDeptAllOne2();
}

