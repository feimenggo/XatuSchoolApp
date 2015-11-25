package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 学习界面 各学期平均分类
 * Created by penfi on 2015/11/25.
 */
public class SemesterAverageScore {
    private List<SemesterPoint> datas = new ArrayList<>();


    public SemesterAverageScore() {
    }

    /**
     * 获取各学期平均分集合
     *
     * @return 学期点集合
     */
    public List<SemesterPoint> getDatas() {
        return datas;
    }

    /**
     * 添加学期点
     *
     * @param data 学期点
     */
    public void addData(SemesterPoint data) {
        datas.add(data);
    }

    /**
     * 学期点
     */
    public static class SemesterPoint {
        public String name;// 学期名
        public int score;// 平均分

        public SemesterPoint(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

}
