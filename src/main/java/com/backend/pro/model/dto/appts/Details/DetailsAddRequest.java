package com.backend.pro.model.dto.appts.Details;

import com.backend.pro.http.model.irrep.IrStrListVO;
import com.backend.pro.http.model.saveVO.AddIrVO;
import com.backend.pro.model.entity.appts.Drug;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 创建字典类型请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class DetailsAddRequest {

    private Long id;
    /**
     * 患者姓名
     */
    private String patName;

    /**
     * 卡号
     */
    private String card;

    /**
     * 输液室id
     */
    private Long irId;

    /**
     * 日期
     */
    private Date date;


    private List<String> times;

    /**
     * 座位类型(VIP,common,Bed)
     */
    private String seatType;

    /**
     * 座位号
     */
    private String seatNum;

    /**
     * 号源id
     */
    private String sourceId;

    /**
     * 号源类型(0正常号源,1临时号源)
     */
    private Integer type;

    private Long seatId;
    /**
     * 排队号
     */
    private String queueNum;
    /**
     * 手机号
     */
    private String phone;

    //预约的药品
    private List<Drug> drugList;

    /**
     * 保存标志0 保存 1提交
     */
    private Integer saveFlag;

    private AddIrVO addIrVO;

    private Long userId;
    private Long editUserId;

    private List<IrStrListVO> irStrListVO;

}