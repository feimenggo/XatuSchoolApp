package xatu.school.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xatu.school.bean.SingleCourse;
import xatu.school.bean.SourceSingleCourse;

/**
 * 原始单科课程 转换类
 * Created by penfi on 2015/11/24.
 */
public class SourceToSingleCourse {

    /**
     * 将原始单科课程对象转换成单科课程对象
     *
     * @param source SourceSingleCourse 对象
     * @return SingleCourse 对象
     */
    public static SingleCourse toSingleCourse(SourceSingleCourse source) {
        SingleCourse sc = new SingleCourse();
        sc.setName(source.getName());
        sc.setXuefen(Float.valueOf(StringUtil.replace(source.getXuefen())));
        sc.setChengji(Integer.valueOf(source.getYuanshichengji()));
        sc.setRenkejiaoshi(StringUtil.replace(source.getRenkejiaoshi()));
        sc.setKaoshileixing(StringUtil.replace(source.getKaoshileixing()));
        sc.setEvaluateScore(getEvaluateScore(source.getCaozuo()));
        sc.setUrl(source.getUrl());
        sc.setStatus(judgeStatus(source));
        return sc;
    }

    /**
     * 判断当前课程的状态
     *
     * @param source 原始单科课程对象
     * @return 状态码
     */
    private static int judgeStatus(SourceSingleCourse source) {
//        状态：1->无(无)， 2->未归档(提交)， 3->未评价(归档&未评价)， 4->已评价(归档&已评价)
        int result;
        if (source.getZhuangtai().contains("无")) {
            result = 1;
        } else if (source.getZhuangtai().contains("提交")) {
            result = 2;
        } else if (source.getZhuangtai().contains("归档") & source.getCaozuo().contains("已")) {
            result = 4;
        } else {
            if (!source.getCaozuo().contains("评价")) {
                result = 4;
            } else {
                if (Integer.valueOf(source.getYuanshichengji()) > 0) {
                    result = 4;
                } else {
                    result = 3;
                }
            }
        }
        return result;
    }

    private static float getEvaluateScore(String caozuo) {
        float score = 0;
        Pattern pattern = Pattern.compile("\\d+(.\\d)?");
        Matcher matcher = pattern.matcher(caozuo);

        if (matcher.find()) {
            score = Float.parseFloat(matcher.group());
        }
        return score;
    }

}
