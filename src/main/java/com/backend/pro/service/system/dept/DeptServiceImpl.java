package com.backend.pro.service.system.dept;

import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.model.dto.system.dept.DeptAddRequest;
import com.backend.pro.model.dto.system.dept.DeptQueryRequest;
import com.backend.pro.model.dto.system.dept.DeptUpdateRequest;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.system.dept.DeptTreeVO;
import com.backend.pro.model.vo.system.dept.DeptVO;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.system.dept.Dept;
import com.backend.pro.mapper.dept.DeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【sys_dept(科室表)】的数据库操作Service实现
 * @createDate 2024-12-04 19:17:01
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
        implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private UserService userService;

    @Override
    public Long createDept(DeptAddRequest createReqVO, HttpServletRequest request) {
        //检验名称是否重复
        validateDeptName(null, createReqVO.getDeptName());
        Dept dept = BeanUtils.toBean(createReqVO, Dept.class);
        User loginUser = userService.getLoginUser(request);
        dept.setUserId(loginUser.getId());
        dept.setEditUserId(loginUser.getId());
        deptMapper.insert(dept);
        return loginUser.getId();
    }

    @Override
    public void deleteDept(Long id, HttpServletRequest request) {
        //检验是否存在
        validateDeptExists(id);
        //检验是否存在子科室
        validateDeptChildren(id);
        deptMapper.deleteById(id);
    }

    @Override
    public void updateDept(DeptUpdateRequest updateReqVO, HttpServletRequest request) {
        //检验是否存在
        validateDeptExists(updateReqVO.getId());
        //检验名称是否重复
        validateDeptName(updateReqVO.getId(), updateReqVO.getDeptName());
        //更新
        Dept updateObj = BeanUtils.toBean(updateReqVO, Dept.class);
        //更新者
        updateObj.setEditUserId(userService.getLoginUser(request).getId());
        deptMapper.updateById(updateObj);
    }

    @Override
    public DeptVO getDept(Long id) {
        return BeanUtils.toBean(deptMapper.selectById(id), DeptVO.class);
    }

    @Override
    public PageResult<DeptVO> listDeptPage(DeptQueryRequest pageReqVO) {
        return deptMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeptVO> listDept() {
        return BeanUtils.toBean(deptMapper.selectList(null), DeptVO.class);
    }


    //检验科室名称是否重复
    void validateDeptName(Long id, String name) {
        Dept deptByName = deptMapper.getDeptByName(name);
        if (deptByName == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, SYSTEM_DEPT_EXISTS);
        ThrowUtils.throwIf(!deptByName.getId().equals(id), SYSTEM_DEPT_EXISTS);
    }

    //检验科室是否存在
    void validateDeptExists(Long id) {
        Dept dept = deptMapper.selectById(id);
        ThrowUtils.throwIf(dept == null, NOT_FOUND_ERROR);
    }

    //检验是否存在子科室
    void validateDeptChildren(Long id) {
        Long count = deptMapper.selectByParentIdLong(id);
        ThrowUtils.throwIf(count >  0, SYSTEM_CHILDREN_NOT_NULL_EXISTS);
    }



    @Override
    public List<DeptTreeVO> listDeptTree() {
        // 获取所有科室
        List<Dept> depts = deptMapper.selectList(new QueryWrapper<Dept>().eq("flag" , 1));
        return depts.stream()
                .filter(item -> item.getParentId() == 0)//构造最外层节点，即id=0的节点
                .map(item -> new DeptTreeVO(item.getDeptName(), item.getId(), getChildren(item, depts)))
                .collect(Collectors.toList());
    }

    private static List<DeptTreeVO> getChildren(Dept treeEntity, List<Dept> treeEntityList) {
        return treeEntityList.stream()
                .filter(item -> item.getParentId().equals(treeEntity.getId()))//判断当前节点的父id是不是要设置节点的id
                .map(item -> new DeptTreeVO(item.getDeptName(), item.getId(), getChildren(item, treeEntityList)))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeptTreeVO> listDeptAllOne2() {
        List<Dept> depts = deptMapper.selectList(new QueryWrapper<Dept>().eq("flag" , 0));
        return depts.stream()
                .filter(item -> item.getParentId() == 0)//构造最外层节点，即id=0的节点
                .map(item -> new DeptTreeVO(item.getDeptName(), item.getId(), getChildren(item, depts)))
                .collect(Collectors.toList());
    }
}




