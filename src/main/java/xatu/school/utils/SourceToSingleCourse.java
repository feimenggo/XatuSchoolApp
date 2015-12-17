package xatu.school.utils;

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
        SingleCourse singleCourse = new SingleCourse();

        singleCourse.setName(source.getName());// 课程名
        singleCourse.setXuefen(Float.valueOf(StringUtil.replace(source.getXuefen())));// 学分
        singleCourse.setChengji(Integer.valueOf(source.getYuanshichengji()));// 成绩
        singleCourse.setRenkejiaoshi(StringUtil.replace(source.getRenkejiaoshi()));// 任课教师
        singleCourse.setKaoshileixing(StringUtil.replace(source.getKaoshileixing()));// 考试类型
        singleCourse.setUrl(source.getUrl());// 评教地址

        // 检测评教
        checkEvaluate(source, singleCourse);
        // 检测课程状态
        checkStatus(source, singleCourse);

        return singleCourse;
    }

    private static void checkEvaluate(SourceSingleCourse source, SingleCourse singleCourse) {
        // 判断是否需要评教
        if (source.getCaozuo().contains("评价")) {
            // 新版，需要评教
            Pattern pattern = Pattern.compile("\\d+(.\\d)?");
            Matcher matcher = pattern.matcher(source.getCaozuo());

            if (matcher.find()) {
                // 已评教
                singleCourse.setEvaluateScore(Float.parseFloat(matcher.group()));
            } else {
                // 未评教
                singleCourse.setEvaluateScore(0);
            }
        } else {
            // 旧版，不需评教
            singleCourse.setEvaluateScore(0);
        }
    }

    private static void checkStatus(SourceSingleCourse source, SingleCourse singleCourse) {
//        状态：1->无(无)， 2->未归档(提交)， 3->未评价(归档&未评价)， 4->已评价(归档&已评价)
        int result;
        if (source.getZhuangtai().contains("无")) {// 未考试或者已考试但老师未提交
            result = 1;
        } else if (source.getZhuangtai().contains("提交")) {// 老师已提交
            result = 2;
        } else {// 已归档
            // 根据是否评教，设置状态
            if (singleCourse.isEvaluated()) {// 已评教
                result = 4;
            } else {// 未评教
                // 判断是否旧版
                if (singleCourse.getChengji() > 0) {// 旧版，不需评教
                    result = 4;
                } else {// 新版，未评教
                    result = 3;
                }
            }
        }
        singleCourse.setStatus(result);
    }
}
