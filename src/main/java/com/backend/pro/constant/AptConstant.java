package com.backend.pro.constant;

/**
 * 文件常量
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface AptConstant {

    /**
     * 1到诊（患者当日到达就是到诊）、2已提交药品（提交药品发送pw表示药品）、3已保存药品（只进行预约登记未发药）、4进行中（患者扫码代表开始输液，录入输液开始时间、）、5已完成（录入输液结束时间）
     */
    /**
     * 预约状态：0已预约时间（只是医生约好时间）
     */
    Integer APT_STATUS_WAIT = 0;
    /**
     * 预约状态：1已到诊
     */
    Integer APT_STATUS_ARRIVE = 1;
    /**
     * 预约状态：2已提交药品
     */
    Integer APT_STATUS_SUBMIT_DRUG = 2;
    /**
     * 预约状态：3已保存药品
     */
    Integer APT_STATUS_SAVE_DRUG = 3;
    /**
     * 预约状态：4进行中
     */
    Integer APT_STATUS_START = 4;
    /**
     * 预约状态：5已完成
     */
    Integer APT_STATUS_FINISH = 5;
    /**
     * 预约状态：6已取消
     */
    Integer APT_STATUS_CANCEL = 6;

    /**
     * 预约状态：7已取消
     */
    Integer APT_STATUS_DELETE = 7;
}
