package xatu.school.utils;

import xatu.school.bean.SingleCourse;
import xatu.school.bean.SourceSingleCourse;

/**
 * Created by penfi on 2015/11/24.
 */
public class SourceToSingleCourse {

    /**
     * 将原始单科成绩对象转换成单科成绩对象
     *
     * @param source SourceSingleCourse 对象
     * @return SingleCourse 对象
     */
    public static SingleCourse toSingleCourse(SourceSingleCourse source) {
        SingleCourse sc = new SingleCourse();
        sc.setName(source.getName());
        sc.setXuefen(Float.valueOf(replace(source.getXuefen())));
        sc.setChengji(Integer.valueOf(source.getYuanshichengji()));
        sc.setRenkejiaoshi(replace(source.getRenkejiaoshi()));
        sc.setKaoshileixing(replace(source.getKaoshileixing()));
        sc.setUrl(source.getUrl());
        sc.setStatus(judgeStatus(source));
        return sc;
    }

    private static int judgeStatus(SourceSingleCourse source) {
//        状态：1->没考试(无)， 2->未归档(提交)， 3->未评价(归档&未评价)， 4->已评价(归档&已评价)
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

    public static String replace(String str) {
        return str.replace("  ", "").replace(" ", "").replace(" ", "");
    }

}
