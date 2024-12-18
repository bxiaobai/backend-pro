package com.backend.pro.http.model.saveVO;


import com.backend.pro.http.model.ir.Brxx;
import com.backend.pro.http.model.ir.MainData;
import com.backend.pro.http.model.ir.Yzxxs;
import com.backend.pro.model.dto.appts.Details.DetailsAddRequest;
import lombok.Data;

import java.util.List;

@Data
public class AddIrVO {
    private Area area;
    private Bed bed;
    private Brxx brxx;
    private String Cfxx;
    private List<MainData> Cfxxlb;
    private List<SydsVo> Syds;
    // 输液单长度
    private int Zs;
    // 预约时间
    private String yuyuesj;
    // 预约操作人员ID
    private String yuyuehs_id;
    // 预约操作人员姓名
    private String yuyuehsxm;
    // 预约号段（例如：9表示第9号）
    private String yuyuehm;
    //护士工号
    private String djhs_gh;
    private String tsbrxx = "0";
    // 返回字符串信息
    private int sgjs;
    //就诊地id
    private int jdt_id;
    private int fklk = 0;
    private String txsj = "-1";
    private List<Yzxxs> yzzxs;
    private Integer djhs_id;
    private String creationTime;
    private String zwsc;


}
