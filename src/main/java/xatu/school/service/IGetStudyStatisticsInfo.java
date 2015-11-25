package xatu.school.service;

import xatu.school.bean.CoursePassRate;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.SemesterAverageScore;

/**
 * 学习界面 获取学习统计信息
 * Created by penfi on 2015/11/4.
 */
public interface IGetStudyStatisticsInfo {

    /**
     * 得到课程通过率
     *
     * @param courseGrades 课程成绩实例对象
     * @return 课程通过率实例对象
     */
    public CoursePassRate getCourseInfoSection(CourseGrades courseGrades);

    /**
     * 得到各学期平均分
     * 用法：
     * SemesterAverageScore ave = new SemesterAverageScore();
     * String name ="";// 学期名
     * int score = 82;// 平均分
     * ave.addData(new SemesterAverageScore.SemesterPoint("大一上", 82));
     *
     * @param courseGrades 课程成绩实例对象
     * @return 各学期平均分实例对象
     */
    public SemesterAverageScore getAverageScore(CourseGrades courseGrades);
}
