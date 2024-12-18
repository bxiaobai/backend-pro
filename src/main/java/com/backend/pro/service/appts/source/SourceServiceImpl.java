package com.backend.pro.service.appts.source;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.mapper.appts.DetailsMapper;
import com.backend.pro.model.dto.appts.Source.DoctorQueryRequest;
import com.backend.pro.model.dto.appts.Source.SourceQueryRequest;
import com.backend.pro.model.vo.appts.SourceApptsVO;
import com.backend.pro.model.vo.appts.TemplateVO;
import com.backend.pro.service.appts.template.TemplateService;
import com.backend.pro.service.ir.room.IrDeptRoomService;
import com.backend.pro.service.system.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.appts.Source;
import com.backend.pro.mapper.appts.SourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.*;

/**
 * @author l
 * @description 针对表【appts_source(号源库)】的数据库操作Service实现
 * @createDate 2024-12-09 11:42:31
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source>
        implements SourceService {

    @Resource
    private SourceMapper sourceMapper;

    @Resource
    private UserService userService;

    @Resource
    private DetailsMapper detailsMapper;

    @Resource
    private TemplateService templateService;

    @Resource
    private IrDeptRoomService IrDeptRoomService;


    @Override
    @Transactional
    public void createSource(Long irId, List<String> date, HttpServletRequest request) {
        //根据输液室查询模板
        List<TemplateVO> templateVOS = templateService.listTemplateListByIrId(irId);
        ThrowUtils.throwIf(templateVOS.isEmpty(), TEMPLATE_NOT_EXISTS);
        //开始时间和结束时间
        List<DateTime> dateTimes = splitDate(date.get(0), date.get(1));
        //需要添加的号源集合
        List<Source> sourceList = new ArrayList<>();
        //循环日期
        for (DateTime dateTime : dateTimes) {
            //检测该日期是否有数据
            Integer count = validateAndDateSource(irId, dateTime);
            //如果有数据则跳过
            if (count > 0) {
                continue;
            }
            //如果没有进行数据创建
            for (TemplateVO templateVO : templateVOS) {
                //创建对象
                Source source = Source.buildSource(templateVO, dateTime);
                source.setUserId(userService.getLoginUser(request).getId());
                source.setEditUserId(userService.getLoginUser(request).getId());
                sourceList.add(source);
            }
        }
        //批量插入
        if (!sourceList.isEmpty()){
            boolean b = saveBatch(sourceList);
            ThrowUtils.throwIf(!b, SYSTEM_ERROR);
        }
    }

    @Override
    public List<SourceApptsVO> listDateAndIrId(SourceQueryRequest sourceUpdateRequest, HttpServletRequest request) {
        //获取登录人的输液室
        Long irId;
        if (sourceUpdateRequest.getIrId() != null){
            irId = sourceUpdateRequest.getIrId();
        }else{
            irId = userService.getLoginUser(request).getDeptId();
        }
        //根据选择的日期和输液时返回号源集合
        String format = DateUtil.format(sourceUpdateRequest.getDate(), "yyyy-MM-dd");
        List<Source> sourceList = sourceMapper.listDateAndIrId(format, irId);
        //如果结束时间并且日期符合，则返回空集合，则去掉小于当前时间的号源
        List<Source> filteredSources = new ArrayList<>();
        for (Source source : sourceList) {
            if (!source.getDate().before(new Date())) { // 只保留开始时间未到的对象
                filteredSources.add(source);
            }
        }
        //循环集合,把开始时间和结束时间转换为字符串
        List<SourceApptsVO> sourceApptsVOList = new ArrayList<>();
        for (Source source : filteredSources) {
            SourceApptsVO sourceApptsVO = new SourceApptsVO();
            BeanUtil.copyProperties(source, sourceApptsVO);
            //开始时间
            String startTimeStr = DateUtil.format(source.getStartTime(), "HH");
            //结束时间
            String endTimeStr = DateUtil.format(source.getEndTime(), "HH");
            if (startTimeStr.equals(endTimeStr)) {
                sourceApptsVO.setTime(startTimeStr);
            } else {
                sourceApptsVO.setTime(startTimeStr + "-" + endTimeStr);
            }
            sourceApptsVOList.add(sourceApptsVO);
        }
        //如果小于当前时间，并且小于当前日期，则不显示
        return sourceApptsVOList;
    }

    @Override
    public void updateSource(Source source, Long userId) {
        //检验是否存在
        validateSource(source);
        source.setEditUserId(userId);
        //更新数据
        sourceMapper.updateById(source);
    }

    //检验当天是否已有号源

    Integer validateAndDateSource(Long irId, Date date) {
        List<Source> source = sourceMapper.selectByDateAndIrId(irId, DateUtil.format(date, "yyyy-MM-dd"));
        if (!source.isEmpty()) {
            return source.size();
        }
        return 0;
    }

    //检测是否有可供选择号源
    void validateSourceIrId(Long irId, Date date) {
        List<Source> source = sourceMapper.selectByDateAndIrId(irId, DateUtil.format(date, "yyyy-MM-dd"));
        ThrowUtils.throwIf(source.isEmpty(), SOURCE_EXISTS);
    }

    //将开始日期和结束日期分解成日期
    private List<DateTime> splitDate(String startDate, String endDate) {
        //使用hutool工具类分解
        return DateUtil.rangeToList(DateUtil.parse(startDate), DateUtil.parse(endDate), DateField.DAY_OF_MONTH);
    }

    void validateSource(Source source) {
        Source source1 = baseMapper.selectById(source.getId());
        ThrowUtils.throwIf(source1 == null, SYSTEM_ERROR);
    }


    @Override
    public List<Source> listSourceId(Long irId, String date) {
        //查询是否有可供选择号源
        validateSourceIrId(irId, DateUtil.parse(date));
//        //如果没有开始时间表示刚刚查询患者结束，自选最早的时间
//        //查询输液室所有座位
//        //根据所需时间和开始时间构建出总的需要用道德号源id
//        //根据根据号源id集合每项匹配预约表中的数据，返回座位号，根据全部座位筛选出可用座位，选择第一个返回
//        if (startTime == null) {
//
//        }
        return sourceMapper.listDateAndIrId(date, irId);
    }


}




