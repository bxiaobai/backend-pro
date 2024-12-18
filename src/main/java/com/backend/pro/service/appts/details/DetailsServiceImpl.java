package com.backend.pro.service.appts.details;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.backend.pro.common.ErrorCode;
import com.backend.pro.exception.BusinessException;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.http.model.ir.Yzxxs;
import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.http.model.saveVO.RemoveVO;
import com.backend.pro.http.service.HisService;
import com.backend.pro.mapper.ir.seat.SeatMapper;
import com.backend.pro.model.dto.appts.Details.AutoRequest;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import com.backend.pro.model.dto.appts.Details.DetailsQueryRequest;
import com.backend.pro.model.entity.appts.Drug;
import com.backend.pro.model.entity.appts.DrugChildren;
import com.backend.pro.model.entity.appts.Source;
import com.backend.pro.model.entity.ir.seat.Seat;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.appts.AutoVO;
import com.backend.pro.model.vo.appts.DetailsVO;
import com.backend.pro.service.appts.drug.DrugService;
import com.backend.pro.service.appts.source.SourceService;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.MessageUtils;
import com.backend.pro.utils.PageResult;
import com.backend.pro.utils.WebServerUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.mapper.appts.DetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.DETAILS_EXISTS;
import static com.backend.pro.common.CodeConstant.SYSTEM_ERROR;
import static com.backend.pro.constant.AptConstant.*;

/**
 * @author l
 * @description 针对表【appts_details(号源库)】的数据库操作Service实现
 * @createDate 2024-12-09 17:37:24
 */
@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper, Details>
        implements DetailsService {

    @Resource
    private DetailsMapper detailsMapper;

    @Resource
    private DrugService drugService;

    @Resource
    private UserService userService;

    @Resource
    private HisService hisService;

    @Resource
    SourceService sourceService;

    @Resource
    private SeatMapper seatMapper;


    @Override
    @Transactional
    public Long createDetails(DetailsAddRequest details, HttpServletRequest request) {
        // 校验是否有相同的记录
        validateDetails(details.getId(), details);
        // 添加输液室
        Details bean = BeanUtil.toBean(details, Details.class);
        //查询座位详情

        User loginUser = null;
        if (details.getUserId() == null) {
            loginUser = userService.getLoginUser(request);
            bean.setUserId(loginUser.getId());
            bean.setEditUserId(loginUser.getId());
        }
        if (bean.getEditUserId() == null) {
            loginUser = userService.getLoginUser(request);
            bean.setEditUserId(loginUser.getId());
        }
        //如果有userid证明是医生预约
        if (bean.getSeatId() != null) {
            Seat seat = seatMapper.selectById(bean.getSeatId());
            bean.setSeatNum(seat.getSeatNumber());
            bean.setSeatType(seat.getFlag());
        }
        // 设置日期和队列号
        String startDate = DateUtil.format(bean.getDate(), "yyyy-MM-dd");
        //根据传入id获取开始时间
        List<Source> startTime = getStartTime(bean.getSourceId());
        List<String> collect = startTime.stream().map(item -> DateUtil.format(item.getStartTime(), "HH")).collect(Collectors.toList());
        String join = String.join(",", collect);
        bean.setTime(join);
        bean.setQueueNum(buildQueueNumber(startDate, startTime));

        if (details.getId() == null) {
            detailsMapper.insert(bean);
        } else {
            //如果是修改
            updateSources(details.getSourceId(), details.getType(), 1, bean.getUserId());
            //先将已用号源恢复
            detailsMapper.updateById(bean);
        }

        updateSources(details.getSourceId(), details.getType(), 0, bean.getUserId());

        if (details.getSaveFlag().equals(0)) {
            bean.setStatus(APT_STATUS_SAVE_DRUG);
        } else {
            bean.setStatus(APT_STATUS_SUBMIT_DRUG);
        }
        //如果不等于空，添加药品
        if (details.getIrStrListVO() != null) {
            BeanUtils.copyProperties(bean, DetailsAddRequest.class);
            hisService.addAppts(details, request, bean);
            addDrugs(details.getIrStrListVO(), bean, loginUser, request);
        }
        //如果等于空说明时医生只预约了时间，
        hisService.sendSms(details.getIrId(), "16639579302", startDate + "日", bean.getTime() + "时");
        return bean.getId();
    }

    //获取开始时间
    private List<Source> getStartTime(String id) {
        String[] split = id.split(",");
        //转换为集合
        List<Long> list = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", list);
        return sourceService.list(queryWrapper);
    }


    @Override
    public DetailsVO getDetailsById(Long id) {
        Details details = detailsMapper.selectById(id);
        DetailsVO detailsVO = BeanUtil.copyProperties(details, DetailsVO.class);
        detailsVO.setDate(DateUtil.format(details.getDate(), "YYYY-MM-dd"));
        //获取药品
        Drug drugById = drugService.getDrugById(id);
        detailsVO.setDrugList(drugById);

        List<IrStrListVO> irStrListVOList = new ArrayList<>();
        if (drugById != null) {
            IrStrListVO bean = BeanUtils.toBean(drugById, IrStrListVO.class);
            List<Yzxxs> bean1 = BeanUtils.toBean(drugById.getDrugChildren(), Yzxxs.class);
            bean.setYzxxsList(bean1);
            irStrListVOList.add(bean);
        }
        detailsVO.setIrStrListVO(irStrListVOList);
        return detailsVO;
    }

    @Override
    public PageResult<DetailsVO> listDetailsPage(DetailsQueryRequest detailsQueryRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long deptId = loginUser.getDeptId();
        return detailsMapper.selectPage(detailsQueryRequest, deptId);
    }


    /**
     * 扣减
     *
     * @param source
     * @param type
     */
    private void deleteSource(Source source, Integer type) {
        if (type == 0) {
            source.setNormalNum(source.getNormalNum() - 1);
            source.setNormalUsedNum(source.getNormalUsedNum() + 1);
        } else {
            source.setTempNum(source.getTempNum() - 1);
            source.setTempUsedNum(source.getTempUsedNum() + 1);
        }
    }

    /**
     * 新增
     *
     * @param source
     * @param type
     */
    private void addSource(Source source, Integer type) {
        if (type == 0) {
            source.setNormalNum(source.getNormalNum() + 1);
            source.setNormalUsedNum(source.getNormalUsedNum() - 1);
        } else {
            source.setTempNum(source.getTempNum() + 1);
            source.setTempUsedNum(source.getTempUsedNum() - 1);
        }
    }

    /**
     * 更新号源
     * 添加号源是扣减还是增加
     *
     * @param sourceIds
     * @param type
     */
    private void updateSources(String sourceIds, Integer type, Integer status, Long userId) {
        String[] split = sourceIds.split(",");
        for (String s : split) {
            Long id = Long.valueOf(s);
            Source source = sourceService.getById(id);
            ThrowUtils.throwIf(source == null, SYSTEM_ERROR);
            if (status == 0) {
                deleteSource(source, type);
            } else {
                addSource(source, type);
            }
            sourceService.updateSource(source, userId);
        }
    }


    /**
     * 添加药品
     */
    private void addDrugs(List<IrStrListVO> irStrListVO, Details bean, User loginUser, HttpServletRequest request) {
        if (irStrListVO.isEmpty()) {
            return;
        }
        List<Drug> drugs = irStrListVO.stream()
                .map(irStrListVO1 -> createDrug(irStrListVO1, bean, loginUser))
                .collect(Collectors.toList());
        for (Drug drug : drugs) {
            drugService.createDrug(drug, request);
        }
    }


    /**
     * 创建药品添加内容
     *
     * @param irStrListVO
     * @param bean
     * @param loginUser
     * @return
     */
    private Drug createDrug(IrStrListVO irStrListVO, Details bean, User loginUser) {
        Drug drug = new Drug();
        drug.setDrugChildren(BeanUtils.toBean(irStrListVO.getYzxxsList(), DrugChildren.class));
        drug.setDrugTime(DateUtil.format(bean.getDate(), "yyyy-MM-dd"));
        drug.setIrNos(irStrListVO.getYzxxsList().stream()
                .map(Yzxxs::getSydh)
                .collect(Collectors.joining("&")));
        drug.setDetailsId(bean.getId());
        drug.setUserId(loginUser.getId());
        return drug;
    }


    /**
     * 构建排队号
     */
    public String buildQueueNumber(String date, List<Source> aptTimeList) {
        LambdaQueryWrapper<Details> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Details::getDate, date)
                .ne(Details::getStatus, APT_STATUS_DELETE)
                .orderByDesc(Details::getQueueNum)
                .last("LIMIT 1");

        Details lastDetail = detailsMapper.selectOne(lambdaQueryWrapper);

        int nextSequence;
        if (lastDetail == null) {
            nextSequence = 1;
        } else {
            // 提取当前最大排队号的序号部分并加1
            String queueNum = lastDetail.getQueueNum();
            nextSequence = Integer.parseInt(queueNum.substring(queueNum.length() - 2)) + 1;
        }
        return formatQueueNumber(date, aptTimeList, nextSequence);
    }

    /**
     * 格式化排队号
     */
    private String formatQueueNumber(String date, List<Source> aptTimeList, int sequence) {
        List<String> collect = aptTimeList.stream().map(item -> DateUtil.format(item.getStartTime(), "HH")).collect(Collectors.toList());
        String a = String.join(",", collect);
        // 只保留月份和日期
        String[] dateParts = date.split("-");
        String formattedDate = dateParts[1] + dateParts[2]; // MMdd
        String timePart = a.split(",")[0].replace(":", "");
        String sequencePart = String.format("%02d", sequence);
        return formattedDate + timePart + sequencePart;
    }

    void validateDetails(Long id, DetailsAddRequest details) {
        LambdaQueryWrapper<Details> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Details::getDate, DateUtil.format(details.getDate(), "yyyy-MM-dd"));
        lambdaQueryWrapper.eq(Details::getCard, details.getCard());
        Details details1 = detailsMapper.selectOne(lambdaQueryWrapper);
        if (details1 == null) {
            return;
        }
        ThrowUtils.throwIf(id == null, DETAILS_EXISTS);
        ThrowUtils.throwIf(details1.getStatus() != 7 && !details1.getId().equals(id), DETAILS_EXISTS);
    }


    @Override
    public void cancelDetails(Long id , HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        //获取预约记录
        Details details = detailsMapper.selectById(id);
        //等于7不能重复取消
        ThrowUtils.throwIf(Objects.equals(details.getStatus(), APT_STATUS_DELETE), new ErrorCode(400, "不能重复取消"));
        //如果进行中不能取消
        ThrowUtils.throwIf(Objects.equals(details.getStatus(), APT_STATUS_START), new ErrorCode(400, "进行中不能取消"));
        //如果已完成不能取消
        ThrowUtils.throwIf(Objects.equals(details.getStatus(), APT_STATUS_FINISH), new ErrorCode(400, "已完成不能取消"));
        //已提交药品不能取消
        ThrowUtils.throwIf(Objects.equals(details.getStatus(), APT_STATUS_SUBMIT_DRUG), new ErrorCode(400, "已提交药品不能取消"));
        WebServerUtils webServerUtils = new WebServerUtils();
        RemoveVO removeVO = new RemoveVO();
        removeVO.setBlh(details.getCard());
        removeVO.setYuyuehm(details.getQueueNum());
        removeVO.setSys(String.valueOf(details.getIrId()));
        removeVO.setUserId(loginUser.getCode());
        boolean b = webServerUtils.removeServerApt(removeVO);
        if (b) {
            String smsContent = "您已取消" + DateUtil.format(details.getDate() , "yyyy-MM-dd") + "日预约";
            MessageUtils messageUtils = new MessageUtils();
            messageUtils.sendTextMessage("16639579302", smsContent);
            updateSources(details.getSourceId(), 1, 7, details.getUserId());
            //修改本身状态为已取消
            details.setStatus(7);
            detailsMapper.updateById(details);
        }
    }


    @Override
    public Integer countDrugTime(List<Yzxxs> list) {
        // 获取所有计量
        // 获取ml的计量
        List<String> mlList = list.stream()
                .filter(item -> item.getUnit().equals("ml"))
                .map(Yzxxs::getDose)
                .collect(Collectors.toList());
        // 获取mg的计量
        List<String> mgList = list.stream()
                .filter(item -> item.getUnit().equals("mg"))
                .map(Yzxxs::getDose)
                .collect(Collectors.toList());

        // 转换为Double并相加
        double mlSum = mlList.stream()
                .mapToDouble(Double::parseDouble)
                .sum();
        double mgSum = mgList.stream()
                .mapToDouble(Double::parseDouble)
                .sum();
        // 总和
        double totalSum = mlSum + mgSum;
        // 除以每小时的量200毫升，并四舍五入
        return (int) Math.round(totalSum / 200);
    }


    @Override
    public AutoVO autoSelect(AutoRequest addIrVO) {
        // 获取当前时间
        String startTime = addIrVO.getStartTime() + ":00";
        String date = addIrVO.getDate();
        Integer totalTime = addIrVO.getTotalTime();
        Long irId = addIrVO.getIrId();
        //计算出的例如需要4小时开始时间是08，结束时间是12，再根据时间段查询 InfusionRoomSeat 表中可以预约的座位
        // 计算时间范围
        LocalTime startLocalTime = LocalTime.parse(startTime);
        LocalTime endLocalTime = startLocalTime.plusHours(totalTime);
        // 获取所有时间段
        List<String> requiredTimes = new ArrayList<>();
        LocalTime currentTime = startLocalTime;
        while (!currentTime.isAfter(endLocalTime.minusMinutes(1))) {
            requiredTimes.add(currentTime.toString()); // 取小时部分
            currentTime = currentTime.plusHours(1);
        }
        //先去查询当日已经预约过的号源
        List<Details> bookedDetails = detailsMapper.selectList(new QueryWrapper<Details>()
                .ne("status", APT_STATUS_DELETE)
                .eq("irId", irId)
                .eq("date", date)
                .isNotNull("seatId") // 确保座位 ID 不为空
        );


        //获取当前输液室所有座位
        List<Seat> seats = seatMapper.selectIrByCount(irId);

        // 获取已预约的座位 ID 和时间集合
        // 将已预约的时间段按座位分组
        Map<Long, Set<String>> bookedSeatTimesMap = bookedDetails.stream()
                .collect(Collectors.groupingBy(
                        Details::getId, // 假设 Details 中有一个 getId() 方法来获取 ID
                        Collectors.mapping(Details::getTime, Collectors.toSet())
                ));

        // 筛选出可用的座位
        // 找到第一个可用的座位
        Seat availableSeat = seats.stream()
                .filter(seat -> {
                    Set<String> bookedTimes = bookedSeatTimesMap.getOrDefault(seat.getId(), new HashSet<>());
                    return requiredTimes.stream().noneMatch(bookedTimes::contains);
                })
                .findFirst()
                .orElse(null);
        ThrowUtils.throwIf(availableSeat == null, new ErrorCode(400, "未找到可用座位"));
        //获取输液室当日号源
        //获取输液室当日预约时间段
        //根据时间段获取 InfusionRoomSeat 表中可以预约的座位
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("irId", irId);
        queryWrapper.eq("date", date);
        queryWrapper.in("startTime", requiredTimes);
        List<Source> list = sourceService.list(queryWrapper);
        ThrowUtils.throwIf(list.isEmpty(), new ErrorCode(400, "未找到可用号源"));
        AutoVO autoVO = new AutoVO();
        autoVO.setSeatId(availableSeat.getId());
        autoVO.setSourceId(list.stream().map(Source::getId).collect(Collectors.toList()));

        return autoVO;
    }
}




