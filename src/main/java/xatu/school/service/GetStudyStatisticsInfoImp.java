package xatu.school.service;


import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.CoursePassRate;
import xatu.school.bean.Semester;
import xatu.school.bean.SemesterAverageScore;
import xatu.school.bean.SingleCourse;

/**
 * Created by Administrator on 2015-11-5.
 */
public class GetStudyStatisticsInfoImp implements IGetStudyStatisticsInfo {
    private int total = 0;// 总课程数
    private int pass = 0;// 通过课程数
    private int passRate;// 通过率
    private String evaluation = new String();// 评价
    private double sum = 0;
    private double xf = 0; //已参加考试  的 总学分
    private double passR;
    int count = 0;

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
            evaluation += "学霸";
        } else if (sum >= 80) {
            evaluation += "优秀";
        } else if (sum >= 70) {
            evaluation += "良好";
        } else if (sum >= 60) {
            evaluation += "中等";
        } else {
            evaluation += "需加油了";
        }

        return new CoursePassRate(total, pass, passRate, evaluation);
    }

    @Override
    public SemesterAverageScore getAverageScore(CourseGrades courseGrades) {
        return null;
    }

}
