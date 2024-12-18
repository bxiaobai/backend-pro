package com.backend.pro.http.service;

import com.backend.pro.http.model.RemoveApptsDTO;
import com.backend.pro.http.model.ir.MainData;
import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import com.backend.pro.model.entity.appts.Details;
import com.backend.pro.model.vo.Med.Pat.PatVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 医疗信息系统服务接口
 */
public interface HisService {

    /**
     * 取消预约
     * @param removeApptsDTO 取消预约的数据传输对象
     */
    void removeAppts(RemoveApptsDTO removeApptsDTO);

    /**
     * 新增预约
     */
    void addAppts(DetailsAddRequest addIrVO, HttpServletRequest httpServletRequest, Details bean);

    /**
     * 获取患者信息
     * @param card 患者卡号
     */
    void getPatByCard(String card);

    /**
     * 根据输液单获取药品
     * @param irId 输液单ID
     */
    MainData getDrugByIrList(Set<String> irId);

    /**
     * 根据开始时间和结束时间卡号获取输液单列表信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param card 患者卡号
     */
    List<MainData> getIrList(String startTime, String endTime, String card);

    /**
     * 获取病人信息
     *
     * @param card 患者卡号
     * @return
     */
    PatVO getPatInfo(String card);


    /**
     * 发送信息接口
     */
    void sendSms(Long irId, String phone , String date , String time);


    /**
     * 暴漏controller接口的构建方法
     * @param card
     * @param startTime
     * @param endTime
     * @return
     */
    List<IrStrListVO> buildIrStrListVO(String card , String startTime , String endTime);
}
