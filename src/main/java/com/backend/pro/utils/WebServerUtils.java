package com.backend.pro.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.backend.pro.exception.ThrowUtils;
import com.backend.pro.http.model.RemoveApptsDTO;
import com.backend.pro.http.model.ir.Brxx;
import com.backend.pro.http.model.ir.MainData;
import com.backend.pro.http.model.saveVO.AddIrVO;
import com.backend.pro.http.model.saveVO.RemoveVO;
import com.backend.pro.service.med.pat.PatService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.xml.xpath.XPathConstants;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.backend.pro.common.CodeConstant.SOURCE_EXISTS;
import static com.backend.pro.common.CodeConstant.SYSTEM_ERROR;

/**
 * 请求webServer工具类
 */
public class WebServerUtils {

    @Value(value = "${app.host}")
    private String host = "4040";

    private static final Gson gson = new Gson();

    @Resource
    private PatService patService;

    /**
     * 根据输液单号获取医嘱
     *
     * @param syd
     * @return
     */
    public MainData getIrInfo(String syd) {
        String xml = buildXml("Web_GetCfxx", "<cfid>" + escapeXml(syd) + "</cfid>");
        String response = sendRequest(xml);
        Document docResult = XmlUtil.readXML(response);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return gson.fromJson(value, MainData.class);
    }

    /**
     * 根据病历号开始时间结束时间获取输液医嘱集合
     */
    public List<MainData> getIrCardAndDataList(String medicalNumber, String startTime, String endTime) {
        String xml = buildXml("Web_GetCflb",
                "<blh>" + escapeXml(medicalNumber) + "</blh>" +
                        "<startime>" + escapeXml(startTime) + "</startime>" +
                        "<endtime>" + escapeXml(endTime) + "</endtime>");
        String response = sendRequest(xml);
        Document docResult = XmlUtil.readXML(response);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        List<MainData> list = Arrays.asList(gson.fromJson(value, MainData[].class));
        ThrowUtils.throwIf(list.isEmpty(), SOURCE_EXISTS);
        return list;
    }

    /**
     * 根据病历号开始时间结束时间获取输液医嘱集合
     *
     * @param removeVO
     * @return
     */
    public boolean removeAppts(RemoveApptsDTO removeVO) {
        String xml = buildXml("Web_CancelReservation",
                "<blh>" + escapeXml(removeVO.getCard()) + "</blh>" +
                        "<yuyuehm>" + escapeXml(removeVO.getYuyuehm()) + "</yuyuehm>" +
                        "<jdtid>" + escapeXml(removeVO.getIrId()) + "</jdtid>" +
                        "<Operator>" + escapeXml(removeVO.getUserId()) + "</Operator>");
        String response = sendRequest(xml);
        Document docResult = XmlUtil.readXML(response);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return value.equals("取消预约成功");
    }

    public boolean addIrInfo(AddIrVO addIrVO) {
        String json = gson.toJson(addIrVO);
        String xml = buildXml("Web_SaveYySydj", "<sydj>" + escapeXml(json) + "</sydj>");
        String response = sendRequest(xml);
        Document docResult = XmlUtil.readXML(response);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return value.equals("登记成功！");
    }

    /**
     * 构建 SOAP XML 请求
     *
     * @param methodName 方法名
     * @param params     参数
     * @return XML 字符串
     */
    private String buildXml(String methodName, String params) {
        //%s是占位符
        return String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <%s xmlns=\"http://tempuri.org/\">\n" +
                "%s\n" +
                "    </%s>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>", methodName, params, methodName);
    }

    /**
     * 发送 HTTP 请求
     *
     * @param xml XML 请求体
     * @return 响应体
     */
    private String sendRequest(String xml) {
        try {
            HttpRequest request = HttpRequest.post("http://192.168.63.6:" + host + "/EOIISService.asmx")
                    .header("Content-Type", "application/soap+xml")
                    .header(Header.ACCEPT_CHARSET, "utf-8")
                    .body(xml);
            return request.execute().body();
        } catch (Exception e) {
            // 记录日志
            System.err.println("请求失败或解析出错: " + e.getMessage());
            ThrowUtils.throwIf(false, SYSTEM_ERROR);
            return null;
        }
    }

    /**
     * 转义 XML 特殊字符
     *
     * @param input 输入字符串
     * @return 转义后的字符串
     */
    private String escapeXml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }


    /**
     * 根据病历号获取病人信息
     *
     * @param card 病历号
     * @return 病人信息对象
     */
    public Brxx getPatByCard(String card) {
        //获取当前日期，获取前30天的日期，开始时间位前30天的日期，结束时间位当前日期
        String startTime = DateUtil.format(DateUtil.offsetDay(new Date(), -30), "yyyy-MM-dd");
        String endTIme = DateUtil.format(new Date(), "yyyy-MM-dd");
        List<MainData> irCardAndDataList = this.getIrCardAndDataList(card, startTime, endTIme);
        return irCardAndDataList.get(0).getBrxx();
    }

    public boolean removeServerApt(RemoveVO removeVO) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Web_CancelReservation xmlns=\"http://tempuri.org/\">\n" +
                "      <blh>" + removeVO.getBlh() + "</blh>\n" +
                "      <yuyuehm>" + removeVO.getYuyuehm() + "</yuyuehm>\n" +
                "      <jdtid>" + removeVO.getSys() + "</jdtid>\n" +
                "      <Operator>" + removeVO.getUserId() + "</Operator>\n" +
                "    </Web_CancelReservation>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.post("http://192.168.63.6:"+host +"/EOIISService.asmx");
        // 设置请求头 Content-Type 为 application/xml
        request.header("Content-Type", "application/soap+xml");
        request.header(Header.ACCEPT_CHARSET, "utf-8");
        // 将 XML 字符串转换为 StringEntity，并设置到请求体中
        //查询参数
        String body = request.body(xml).execute().body();
        Document docResult = XmlUtil.readXML(body);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return value.equals("取消预约成功");
    }

}
