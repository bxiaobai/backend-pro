package com.backend.pro.http.model.saveVO;


import com.backend.pro.http.model.ir.Brxx;
import com.backend.pro.http.model.ir.Sfxmxx;
import com.backend.pro.http.model.ir.Yzxxs;
import lombok.Data;

import java.util.List;

@Data
public class CfxxlbItem {
    private String Sjly;
    private Brxx Brxx;
    private List<Sfxmxx> sfxmxx;
    private String Cfsbh;
    private Object Kfksdm;
    private String Kfksmc;
    private String Kfsj;
    private String Kfysxm;
    private List<Yzxxs> Yzxxs;
    private String Zdxx;
    private Object Jzlb;
    private int sysl;
    private int sysfsl;
    private int syzssl;
    private int lgby;
    private String scdjsj;
    private String ysdm;
    private String ksdm;
    private String xiuzhengd;
    private Object bz;
    private String PatientSourceID;
    private String RegisterID;
    private String InfusionDays;
    private Object hlybz;
}
