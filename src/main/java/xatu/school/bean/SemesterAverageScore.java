package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

import feimeng.linechartview.LineChartView;

/**
 * 学习界面 学期平均分类
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

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }

    /**
     * 适配器 将学期平均分对象转换为折线图可使用的坐标点集
     *
     * @param semester 学期平均分对象
     * @return 坐标点集
     */
    public static List<LineChartView.Coord> semesterAdapter(SemesterAverageScore semester) {
        List<LineChartView.Coord> results = new ArrayList<>();
        for (SemesterAverageScore.SemesterPoint data : semester.getDatas()) {
            results.add(new LineChartView.Coord(data.getScore(), data.getName()));
        }
        return results;
    }
}
