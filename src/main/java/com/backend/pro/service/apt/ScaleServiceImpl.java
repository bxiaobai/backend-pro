package com.backend.pro.service.apt;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.backend.pro.mapper.apt.ScaleMapper;
import com.backend.pro.model.entity.med.apt.Scale;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author l
 * @description 针对表【_scale(评估结果表)】的数据库操作Service实现
 * @createDate 2024-10-28 10:01:56
 */
@Service
public class ScaleServiceImpl extends ServiceImpl<ScaleMapper, Scale>
        implements ScaleService {


    @Override
    public boolean addApppointmentScale(Scale Scale) {
        //先检测有没有相同的值
        Scale one = query().eq("detailsId", Scale.getDetailsId()).eq("tag", Scale.getTag()).one();
        // 根据传递的key判断是哪个评估表单
        if (Scale.getTag() == 1) {
            // 计算结果
            String result = resultCalculate(Scale.getScaleJson());
            String[] split = result.split(";");
            Scale.setScaleSuggest(split[1]);
        } else if (Scale.getTag() == 2) {
            String scaleJson = Scale.getScaleJson();
            // 使用 Hutool 的 JSONObject 解析 JSON 字符串
            JSONObject jsonObject = new JSONObject(scaleJson);
            int sliderValue = jsonObject.getInt("slider");
            String s = painResultCalculate(sliderValue);
            Scale.setScaleSuggest(s);
        } else if (Scale.getTag() == 3) {
            String scaleJson = Scale.getScaleJson();
            JSONObject jsonObject = JSONUtil.parseObj(scaleJson);
            String s = extractScore(jsonObject);
            Scale.setScaleSuggest(s);
        } else if (Scale.getTag() == 4) {
            Scale.setScaleSuggest("");
        } else if (Scale.getTag() == 5) {
            String scaleJson = Scale.getScaleJson();
            JSONObject jsonObject = JSONUtil.parseObj(scaleJson);
            String s = calculatePhysical(jsonObject);
            Scale.setScaleSuggest(s);
        }
        // 如果存在相同的记录，则更新，否则保存新的记录
        if (one != null) {
            Scale.setScaleId(one.getScaleId());
            return updateById(Scale);
        } else {
            return save(Scale);
        }
    }


    /**
     * 心理结果计算方式
     */
    public String resultCalculate(String result) {
        String[] split = result.split(",");
        //获取每个数组倒数倒数第一位的字符
        //计算总分数
        int sun = 0;
        for (String s : split) {
            String s1 = s.substring(s.length() - 1);
            //如果最后一位是}
            if (s1.equals("}")) {
                s1 = s.substring(s.length() - 2, s.length() - 1);
            }
            sun += Integer.parseInt(s1);
        }
        if (sun >= 30) {
            return "得分:" + sun + ";您的评分为30分或以上，您可能有重度的情绪困扰，如需进一步的帮助和指导，可以咨询心理科。";
        } else if (sun >= 25) {
            return "得分:" + sun + ";您的评分为25-29，您可能有中度的情绪困扰，如需进一步的帮助和指导，可以咨询心理科。";
        } else if (sun >= 20) {
            return "得分:" + sun + ";您的评分为20-24，您可能有轻度的情绪困扰，如需进一步的帮助和指导，可以咨询心理科。";
        } else {
            return "得分:" + sun + ";您的评分为19或以下，心理状态良好，请继续保持。";
        }
    }

    /**
     * 疼痛等级计算方式
     */
    public String painResultCalculate(Integer result) {
        if (result == 0) {
            return "您的疼痛评分为0，不存在疼痛";
        } else if (result >= 1 && result < 3) {
            return "存在轻度疼痛，遵医嘱可选用非阿片类药物解热镇痛抗炎药(非甾体类抗炎药NSAIDs)阿司匹林，布洛芬，双氯芬酸，塞来昔布，依托考昔，对乙酰氨基酚；";
        } else if (result >= 3 && result < 5) {
            return "存在轻度疼痛，遵医嘱可选用非阿片类药物解热镇痛抗炎药(非甾体类抗炎药NSAIDs)阿司匹林，布洛芬，双氯芬酸，塞来昔布，依托考昔，对乙酰氨基酚；";
        } else if (result >= 5 && result < 7) {
            return "存在重度疼痛，遵医嘱可选用强阿片类药物(尽量用纯激动剂)吗啡，哌替啶(度冷丁)，羟考酮，芬太尼，美沙酮";
        } else if (result >= 7 && result < 9) {
            return "存在重度疼痛，建议去疼痛科就诊；";
        } else {
            return "建议去疼痛科就诊。";
        }
    }

    /**
     * 营养提取
     *
     * @param item
     * @return
     */
    private String extractScore(JSONObject item) {
        JSONArray yyss = item.getJSONArray("yyss");
        JSONArray sex = item.getJSONArray("sex");
        JSONArray jb = item.getJSONArray("jb");
        //获取三个的所有分数
        int sum = 0;
        sum = getSum(yyss, sum);
        //第二个
        sum = getSum(sex, sum);
        //第二个
        sum = getSum(jb, sum);
        /**
         * 您的评分≥3，有营养不良的风险；可经口进食者加强营养支持，不可经口进食者请至营养科开具营养制剂；
         * 您的评分＜3，营养状况良好，请继续保持。
         */
        if (sum >= 3) {
            return "您的评分 " + sum + "，有营养不良的风险；可经口进食者加强营养支持，不可经口进食者请至营养科开具营养制剂；";
        } else {
            return "您的评分 " + sum + "，营养状况良好，请继续保持。";
        }
    }

    /**
     * @param jb
     * @param sum
     * @return
     */
    private int getSum(JSONArray jb, int sum) {
        for (Object object : jb) {
            String o = (String) object;
            String[] split = o.split(";");
            String s = split[1];
            String substring = s.substring(1, 2);
            sum += Integer.parseInt(substring);
        }
        return sum;
    }

    /**
     * 计算体力评
     */
    private String calculatePhysical(JSONObject result) {
        // 解析 JSON 字符串为 JSONArray
        JSONArray jsonArray = JSONUtil.parseArray(result);
        // 创建一个列表来存储所有的 value
        List<String> values = new ArrayList<>();
        // 遍历 JSONArray
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if (value instanceof String) { // 确保 value 是字符串
                    String strValue = (String) value;
                    if (strValue.contains("分")) {
                        String[] parts = strValue.split(";");
                        if (parts.length > 1) {
                            String s = parts[1];
                            String str = s.substring(1, 2);
                            values.add(str);
                        }
                    }
                }
                values.add(String.valueOf(value)); // 将所有值转换为字符串后添加
            }
        }
        // 创建一个新的列表来存储更新后的值
        List<String> updatedValues = new ArrayList<>();

        // 遍历原始的 values 列表
        for (String s : values) {
            if (s.contains("分")) {
                // 如果字符串包含 "分"，则分割字符串并提取 "分" 后面的数字
                String[] parts = s.split(";");
                if (parts.length > 1) {
                    String s1 = parts[1];
                    String substring = s1.substring(1, 2); // 提取 "分" 后面的数字
                    updatedValues.add(substring); // 将提取的数字添加到新列表中
                } else {
                    // 如果没有分号，直接将原始字符串添加到新列表中
                    updatedValues.add(s);
                }
            } else {
                // 如果不包含 "分"，则直接将原始字符串添加到新列表中
                updatedValues.add(s);
            }
        }
        //循环出来的字符串相加
        double sum = updatedValues.stream().mapToDouble(this::getDouble).sum() / 22;
        if (sum >= 0 && sum < 3) {
            return "您的评分为" + sum + "分，属于轻度疲乏，可进行中度以上有氧训练和抗阻练习";
        } else if (sum >= 3 && sum < 6) {
            return "您的评分为" + sum + "分，属于中度疲乏，可进行中度以上有氧训练和抗阻练习";
        } else if (sum >= 6 && sum < 10) {
            return "您的评分为" + sum + "分，属于重度疲乏，可进行轻度或中度有氧活动，建议至康复科就诊";
        }else {
            return "您的评分为" + sum + "分，属于无疲乏状态，请继续保持";
        }
    }

    private double getDouble(String result) {
        return Double.parseDouble(result);
    }
}




