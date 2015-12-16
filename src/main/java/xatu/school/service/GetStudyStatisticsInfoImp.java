package xatu.school.service;


import java.util.ArrayList;
import java.util.List;

import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.CoursePassRate;
import xatu.school.bean.Semester;
import xatu.school.bean.SemesterAverageScore;
import xatu.school.bean.SingleCourse;
import xatu.school.utils.GetNowSemester;

/**
 * Created by Administrator on 2015-11-5.
 */
public class GetStudyStatisticsInfoImp implements IGetStudyStatisticsInfo {
    private int total = 0;// 总课程数
    private int pass = 0;// 通过课程数
    private int passRate;// 通过率
    private String evaluation = "";// 评价
    private double sum = 0;
    private double xf = 0; //已参加考试  的 总学分
    private double passR;

    public CoursePassRate getCourseInfoSection(CourseGrades courseGrades) {
        for (Semester s : courseGrades.getSemester()) {
            for (BaseSingleCourse c : s.getSourceSingleCourses()) {
                SingleCourse source = (SingleCourse) c;

                if (source.getStatus() == 4) {
                    int num = source.getChengji();
                    if (num != 0) {
                        total++;
                        if (num > 59)
                            pass++;
                        sum += source.getChengji() * source.getXuefen();
                        xf += source.getXuefen();
                    }
                }
            }
        }
        sum /= xf;
        passR = (pass * 1.0) / (total * 1.0);
        passRate = (int) (passR * 100);
        if (sum >= 90) {
            evaluation += "你就是传说中的学霸";
        } else if (sum >= 80) {
            evaluation += "成绩很优秀，继续保持";
        } else if (sum >= 70) {
            evaluation += "成绩还不错，继续努力";
        } else if (sum >= 60) {
            evaluation += "成绩很悬，要加油了";
        } else {
            evaluation += "不要再玩了，要留级了";
        }

        return new CoursePassRate(total, pass, passRate, evaluation);
    }

    @Override
    public SemesterAverageScore getAverageScore(CourseGrades courseGrades) {
        SemesterAverageScore avg = new SemesterAverageScore();
        List<Semester> semester = courseGrades.getSemester();
        List<BaseSingleCourse> bc;

        int size = semester.size();
        int i, l;
        String name[] = {"大一上", "大一下", "大二上", "大二下", "大三上", "大三下", "大四上", "大四下"};
        int semCount = new GetNowSemester().Get(courseGrades);
        for (i = size - 1; i >= size - semCount; --i) {
            double sum = 0;
            double xuefen = 0;
            for (
                    l = 0, bc = semester.get(i).getSourceSingleCourses();
                    l < bc.size();
                    l++
                    ) {
                SingleCourse source = (SingleCourse) bc.get(l);
                sum += source.getChengji() * source.getXuefen();
                xuefen += source.getXuefen();
            }
            int avgnum = (int) (sum / xuefen);
            avg.addData(new SemesterAverageScore.SemesterPoint(name[size - i - 1], avgnum));
        }
        return avg;
    }
}
