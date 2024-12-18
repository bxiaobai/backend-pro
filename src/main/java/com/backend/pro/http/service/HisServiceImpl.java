package com.backend.pro.http.service;

import cn.hutool.core.date.DateUtil;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.http.model.RemoveApptsDTO;
import com.backend.pro.http.model.ir.Brxx;
import com.backend.pro.http.model.ir.MainData;
import com.backend.pro.http.model.ir.Yzxxs;
import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.http.model.irrep.MainDataKey;
import com.backend.pro.http.model.saveVO.AddIrVO;
import com.backend.pro.http.model.saveVO.Area;
import com.backend.pro.http.model.saveVO.Bed;
import com.backend.pro.http.model.saveVO.SydsVo;
import com.backend.pro.mapper.appts.DetailsMapper;
import com.backend.pro.mapper.system.medical.MedicalMapper;
import com.backend.pro.model.dto.Med.pat.PatAddRequest;
import com.backend.pro.model.dto.Med.pat.PatUpdateRequest;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.entity.system.medical.Medical;
import com.backend.pro.model.entity.system.user.User;
import com.backend.pro.model.vo.Med.Pat.PatVO;
import com.backend.pro.model.vo.ir.room.RoomVO;
import com.backend.pro.service.ir.room.RoomService;
import com.backend.pro.service.med.pat.PatService;
import com.backend.pro.service.system.user.UserService;
import com.backend.pro.utils.BeanUtils;
import com.backend.pro.utils.MessageUtils;
import com.backend.pro.utils.WebServerUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend.pro.common.CodeConstant.*;


@Service
public class HisServiceImpl implements HisService {

    @Resource
    RoomService roomService;

    @Resource
    UserService userService;

    @Resource
    MedicalMapper medicalMapper;

    @Resource
    DetailsMapper detailsMapper;

    @Resource
    PatService patService;


    @Override
    public void removeAppts(RemoveApptsDTO removeApptsDTO) {
        WebServerUtils webServerUtils = new WebServerUtils();
        boolean b = webServerUtils.removeAppts(removeApptsDTO);
        ThrowUtils.throwIf(!b, WEB_SERVER_REMOVE_ERROR);
    }

    @Override
    public void addAppts(DetailsAddRequest detailsAddRequest, HttpServletRequest httpServletRequest, Details bean) {
        WebServerUtils webServerUtils = new WebServerUtils();
        Brxx patByCard = webServerUtils.getPatByCard(detailsAddRequest.getCard());
        //获取当前登录用户
        User loginUser = userService.getLoginUser(httpServletRequest);
        //根据编码获取护士信息
        Medical medical = medicalMapper.getByCode(loginUser.getCode());
        //构建添加输液信息的请求
        AddIrVO addIrVO = new AddIrVO();
        addIrVO.setBrxx(patByCard);
        //输液室
        RoomVO room = roomService.getRoom(detailsAddRequest.getIrId());
        Area area = new Area();
        area.setAreaName(room.getIrName());
        area.setId(Math.toIntExact(room.getId()));
        addIrVO.setArea(area);
        //座位号
        addIrVO.setBed(Bed.build(detailsAddRequest.getSeatNum()));

        List<Yzxxs> yzxxsList = detailsAddRequest.getIrStrListVO().get(0).getYzxxsList();
        //
        String card = detailsAddRequest.getCard();
        //开始时间时随机一个医嘱药品的开始时间
        String creationTime = detailsAddRequest.getIrStrListVO().get(0).getCreationTime();
        //格式化开始时间
        String startDate = DateUtil.format(DateUtil.parse(creationTime), "yyyy-MM-dd");
        //结束时间等于开始时间向后偏移一天
        String endDate = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(creationTime), 1), "yyyy-MM-dd");
        //endtime
        List<MainData> patient = webServerUtils.getIrCardAndDataList(card, startDate, endDate);
//        //药品信息
        List<MainData> cfxx = getCfxx(patient, yzxxsList);
        addIrVO.setZs(cfxx.size());
        addIrVO.setJdt_id(Math.toIntExact(room.getId()));
        addIrVO.setCfxxlb(cfxx);
        addIrVO.setYuyuehm(detailsAddRequest.getQueueNum());
        String[] split = bean.getTime().split(",");
        String joinedString = String.join("-", split);
        addIrVO.setZwsc(joinedString);
        //根据编码和
        String code = medical.getCode();
        addIrVO.setDjhs_gh(code);
        addIrVO.setDjhs_id(Math.toIntExact(medical.getId()));
        //预约护士信息
        addIrVO.setYuyuehsxm(medical.getName());
        addIrVO.setYuyuehs_id(String.valueOf(medical.getId()));
        //预约时长
        addIrVO.setSyds(createSYds(yzxxsList));
        //如果是0不发药
        if (detailsAddRequest.getSaveFlag() == 0) {
            addIrVO.setYuyuehm("");
            addIrVO.setYuyuesj("0001/1/1 0:00:00");
            addIrVO.setYuyuehsxm("");
            addIrVO.setYuyuehs_id("0");
        }
        boolean b = webServerUtils.addIrInfo(addIrVO);
        ThrowUtils.throwIf(!b, WEB_SERVER_SAVE_ERROR);
    }


    @Override
    public void getPatByCard(String card) {
        WebServerUtils webServerUtils = new WebServerUtils();
    }

    @Override
    public MainData getDrugByIrList(Set<String> irId) {
        // 将集合转换为有[]的字符串，并且每个元素用双引号括住，用分号分隔
        String irIdStr = irId.stream()
                .map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(","));
        irIdStr = "[" + irIdStr + "]";

        WebServerUtils webServerUtils = new WebServerUtils();
        return webServerUtils.getIrInfo(irIdStr);
    }

    public List<SydsVo> createSYds(List<Yzxxs> yzxx) {
        List<SydsVo> mainDataList = new ArrayList<>();
        Map<String, List<Yzxxs>> yzxxsMap = yzxx.stream().collect(Collectors.groupingBy(Yzxxs::getSydh));
        yzxxsMap.forEach((key, value) -> {
            SydsVo sydsVo = new SydsVo();
            sydsVo.setPs(value.get(0).getPs());
            sydsVo.setKbbz(value.get(0).getKbbz());
            List<Yzxxs> list = new ArrayList<>(value);
            sydsVo.setYzxxs(list);
            mainDataList.add(sydsVo);
        });
        return mainDataList;
    }


    @Override
    public List<MainData> getIrList(String startTime, String endTime, String card) {
        //获取患者信息和输液信息
        WebServerUtils webServerUtils = new WebServerUtils();
        //获取今天的日期
        //如果开始时间为空，默认获取今日前40天的的输液内容
        if (startTime == null) {
            startTime = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), -40), "yyyy-MM-dd");
        }
        if (endTime == null) {
            //结束时间是今天的日期
            endTime = DateUtil.format(DateUtil.date(), "yyyy-MM-dd");
        }
        return webServerUtils.getIrCardAndDataList(card, startTime, endTime);
    }

    @Override
    public PatVO getPatInfo(String card) {
        WebServerUtils webServerUtils = new WebServerUtils();
        Brxx patByCard = webServerUtils.getPatByCard(card);
        ThrowUtils.throwIf(patByCard == null , CARD_NOT_EXISTS);
        //获取完病人信息后保存到数据库一份
        PatVO patVO = patService.getPatByCard(patByCard.getBlh());
        PatAddRequest patAddRequest = new PatAddRequest();
        patAddRequest.setCard(patByCard.getBlh());
        patAddRequest.setUserId(1L);
        patAddRequest.setName(patByCard.getBrxm());
        patAddRequest.setSexCode(patByCard.getBrxb().equals("男") ? 1 : 2);
        patAddRequest.setContactAddress(patByCard.getJtzz());
        patAddRequest.setPhone(patByCard.getLxdh());
        patAddRequest.setBirthday(DateUtil.parse(patByCard.getCsrq(), "yyyy-MM-dd"));
        //年龄等于当前年减去出生日期年月
        patAddRequest.setDisplayAge(DateUtil.ageOfNow(patByCard.getCsrq()) + "岁");
        if (patVO == null) {
            patService.createPat(patAddRequest, null);
        }else {
            PatUpdateRequest patUpdateRequest = BeanUtils.toBean(patAddRequest, PatUpdateRequest.class);
            patUpdateRequest.setId(patVO.getId());
            patService.updatePat(patUpdateRequest, null);
        }
        //返回创建后的病人信息
        return patService.getPatByCard(card);
    }

    @Override
    public void sendSms(Long irId, String phone, String date, String time) {
        RoomVO room = roomService.getRoom(irId);
        String smsTemplateId = room.getSmsTemplateId();
        String location = room.getIrPlace();
        String consultationPhone = room.getPhone();
        // 替换占位符
        String smsContent = smsTemplateId
                .replace("{appointment_date}", date)
                .replace("{appointment_time}", time.split(",")[0])
                .replace("{location}", location)
                .replace("{phone}", consultationPhone);

        MessageUtils messageUtils = new MessageUtils();
        messageUtils.sendTextMessage(phone, smsContent);
    }


    public List<MainData> getCfxx(List<MainData> cfxx, List<Yzxxs> yzxx) {
        List<MainData> mainDataList = new ArrayList<>();
        for (MainData mainData : cfxx) {
            List<Yzxxs> yzxxsList = new ArrayList<>();
            for (Yzxxs yzxxs : yzxx) {
                if (mainData.getCfsbh().equals(yzxxs.getSydh())) {
                    yzxxsList.add(yzxxs);
                }
            }
            mainData.setYzxxs(yzxxsList);
            mainDataList.add(mainData);
        }
        return mainDataList;
    }


    @Override
    public List<IrStrListVO> buildIrStrListVO(String card, String startTime, String endTime) {
        // 获取患者信息和输液信息
        List<MainData> irList = this.getIrList(startTime, endTime, card);
        // 按照kfsj, RegisterID和Kfysxm进行分组
        Map<MainDataKey, List<MainData>> listMap = irList.stream().collect(Collectors.groupingBy(MainDataKey::new));
        // 循环分组
        List<IrStrListVO> result = new ArrayList<>();
        for (Map.Entry<MainDataKey, List<MainData>> entry : listMap.entrySet()) {
            MainDataKey index = entry.getKey();
            List<MainData> value = entry.getValue();

            IrStrListVO transfusionVO = new IrStrListVO();
            Set<String> sydlist = value.stream().map(MainData::getCfsbh).collect(Collectors.toSet());
            MainData mainData = this.getDrugByIrList(sydlist);

            transfusionVO.setYzxxsList(groupYzxxs(mainData.getYzxxs()));
            transfusionVO.setCreationTime(index.getKfsj());
            // 已输液天数
            Integer fysycs = getFysycs(mainData);
            // 需要输液天数
            Integer sycs = getsycs(mainData);
            transfusionVO.setInfusionNum(fysycs);
            transfusionVO.setInfusionTotal(sycs);
            transfusionVO.setKfksmc(value.get(0).getKfksmc());
            transfusionVO.setInfusionTime(calculateInfusionTime(mainData));
            // 判断是否输液状态
            transfusionVO.setStatus(determineStatus(fysycs, sycs));
            transfusionVO.setCreateDoctor(value.get(0).getKfysxm());
            transfusionVO.setIsCharge(isCharge(mainData.getYzxxs()));
            result.add(transfusionVO);
        }
        result.sort(Comparator.comparing(IrStrListVO::getCreationTime).reversed());
        return result; // 返回结果列表而不是空列表
    }

    private List<Yzxxs> groupYzxxs(List<Yzxxs> yzxxsList) {
        Map<Integer, List<Yzxxs>> yzxxsMap = yzxxsList.stream().collect(Collectors.groupingBy(Yzxxs::getZ_id));
        List<Yzxxs> updatedYzxxsList = new ArrayList<>();
        boolean colorFlag = true;

        for (List<Yzxxs> yzxxss : yzxxsMap.values()) {
            if (!yzxxss.isEmpty()) {
                yzxxss.get(0).setTxbz(0);
            }
            if (yzxxss.size() > 1) {
                yzxxss.get(yzxxss.size() - 1).setTxbz(2);
            }
            for (int i = 1; i < yzxxss.size() - 1; i++) {
                yzxxss.get(i).setTxbz(1);
            }
            for (Yzxxs yzxxs : yzxxss) {
                yzxxs.setColor(colorFlag ? "1" : "0");
            }
            if (yzxxss.size() == 1) {
                yzxxss.get(0).setTxbz(3);
            }
            yzxxss.sort(Comparator.comparingInt(Yzxxs::getTxbz));
            updatedYzxxsList.addAll(yzxxss);
            colorFlag = !colorFlag;
        }
        updatedYzxxsList.sort(Comparator.comparingInt(Yzxxs::getZ_id));
        return updatedYzxxsList;
    }

    /**
     * 是否全部收费
     *
     * @param yzxxsList 医嘱信息列表
     * @return 是否全部收费
     */
    private Boolean isCharge(List<Yzxxs> yzxxsList) {
        return yzxxsList.stream().anyMatch(y -> "0".equals(y.getSfzt()));
    }

    /**
     * 获取已输液天数
     *
     * @param mainData 主数据
     * @return 已输液天数
     */
    private Integer getFysycs(MainData mainData) {
        return mainData.getYzxxs().stream().map(Yzxxs::getFysycs).findFirst().orElse(0);
    }

    /**
     * 获取需要输液天数
     *
     * @param mainData 主数据
     * @return 需要输液天数
     */
    private Integer getsycs(MainData mainData) {
        return mainData.getYzxxs().stream().map(Yzxxs::getSycs).findFirst().orElse(0);
    }

    /**
     * 计算输液总时间（小时）
     *
     * @param mainData 主数据
     * @return 输液总时间（小时）
     */
    private Integer calculateInfusionTime(MainData mainData) {
        int sumTime = mainData.getYzxxs().stream()
                .filter(item -> item.getDsybz() == 1)
                .mapToInt(item -> Integer.parseInt(item.getSysc()))
                .sum();
        return (int) Math.ceil(sumTime / 60.0);
    }

    /**
     * 确定输液状态
     *
     * @param fysycs 已输液天数
     * @param sycs   需要输液天数
     * @return 输液状态
     */
    private int determineStatus(Integer fysycs, Integer sycs) {
        if (Objects.equals(fysycs, sycs)) {
            return 0; // 完成输液
        } else if (fysycs == 0) {
            return 2; // 未开始输液
        }
        return 1; // 正在输液
    }





}
