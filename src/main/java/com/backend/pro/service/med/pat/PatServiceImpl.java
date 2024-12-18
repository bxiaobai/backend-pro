package com.backend.pro.service.med.pat;

import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.model.dto.Med.pat.PatAddRequest;
import com.backend.pro.model.dto.Med.pat.PatUpdateRequest;
import com.backend.pro.model.entity.system.dept.Dept;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.Med.Pat.PatVO;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.med.pat.Pat;
import com.backend.pro.service.med.pat.PatService;
import com.backend.pro.mapper.med.pat.PatMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【med_pat】的数据库操作Service实现
 * @createDate 2024-12-13 17:09:35
 */
@Service
public class PatServiceImpl extends ServiceImpl<PatMapper, Pat>
        implements PatService {

    @Resource
    private PatMapper patMapper;

    @Resource
    UserService userService;

    @Override
    public Long createPat(PatAddRequest patAddRequest , HttpServletRequest request) {
        validatePatExists(null , patAddRequest.getCard());
        Pat pat = BeanUtils.toBean(patAddRequest, Pat.class);
        if (patAddRequest.getUserId() == null){
            User loginUser = userService.getLoginUser(request);
            pat.setUserId(loginUser.getId());

        }
        patMapper.insert(pat);
        return pat.getId();
    }

    @Override
    public PatVO getPatByCard(String card) {
        return patMapper.getPatByCard(card);
    }

    @Override
    public void updatePat(PatUpdateRequest patUpdateRequest , HttpServletRequest request) {
        //检验是否存在
        validatePatExists(patUpdateRequest.getId());
        //检验卡号是否有相同打的
        validatePatExists(patUpdateRequest.getId() , patUpdateRequest.getCard());
        Pat updateObj = BeanUtils.toBean(patUpdateRequest, Pat.class);
        if (patUpdateRequest.getUserId() == null){
            User loginUser = userService.getLoginUser(request);
            updateObj.setUserId(loginUser.getId());
        }
        patMapper.updateById(updateObj);
    }


    //检验患者是否存在
    void validatePatExists(Long id) {
        Pat dept = patMapper.selectById(id);
        ThrowUtils.throwIf(dept == null, NOT_FOUND_ERROR);
    }


    //检验患者卡号是否重复
    void validatePatExists(Long id, String card) {
        PatVO dept = patMapper.getPatByCard(card);
        if (dept == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, CARD_EXISTS);
        ThrowUtils.throwIf(!dept.getId().equals(id), CARD_EXISTS);
    }


}




