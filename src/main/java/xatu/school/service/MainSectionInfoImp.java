package xatu.school.service;

import xatu.school.bean.CourseGrades;
import xatu.school.bean.CourseInfoSection;
import xatu.school.bean.Semester;
import xatu.school.bean.SourceSingleCourse;

/**
 * Created by Administrator on 2015-11-5.
 */
public class MainSectionInfoImp implements IMainSectionInfo {
    private int total = 0;// 总课程数
    private int pass = 0;// 通过课程数
    private int passRate;// 通过率
    private String evaluation = "";// 评价
    private double sum = 0;
    private double xf = 0;
    private double passR;

    @Override
    public CourseInfoSection getCourseInfoSection(CourseGrades courseGrades) {

        for (Semester s : courseGrades.getSemester()) {
            for (SourceSingleCourse c : s.getSourceSingleCourses()) {

                if (!c.getZhuangtai().equals("") && c.getZhuangtai().indexOf("档") > 0) {
                    total++;
                    if (!c.getJidian().replaceAll(" ", "").equals("") && Integer.parseInt(c.getJidian().replaceAll(" ", "")) >= 60) {
                        pass++;
                        sum += Integer.parseInt(c.getJidian().replaceAll(" ", "")) * Double.parseDouble(c.getXuefen().replaceAll(" ", ""));
                        xf += Double.parseDouble(c.getXuefen().replaceAll(" ", ""));
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

        return new CourseInfoSection(total, pass, passRate, evaluation);
    }
}
