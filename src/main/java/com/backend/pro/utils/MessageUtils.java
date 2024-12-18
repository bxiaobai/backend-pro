package com.backend.pro.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;

import java.util.Date;

/**
 * 短信发送工具类
 */
public class MessageUtils {
    /**
     * 发送短信接口
     *
     * @return
     */
    public String sendTextMessage(String phone, String Message) {
        String url = "http://msg23.rjh.com.cn:8513/sms/Api/VarSend.do";
        String result = HttpRequest.get(url)
                .form("f", "1")
                .form("SpCode", "100001")
                .form("LoginName", "15710185979")
                .form("Password", "ad0854ccf109817a894dde63c6cedfde")
                .form("MessageContent", "{\"文本\":\"" + Message + "\"}")
                .form("SerialNumber", DateUtil.format(new Date(), "yyyyMMddHHmmssHHmmss"))
                .form("UserNumber", phone)
                .form("templateId", "1100000018124")
                .execute()
                .body();
        System.out.println(result);
        if (result.contains("发送短信成功")){
            return "发送短信成功";
        }else {
            return "发送短信失败，请联系管理员";
        }
    }
}
