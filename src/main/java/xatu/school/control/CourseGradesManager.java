package xatu.school.control;


import java.util.ArrayList;
import java.util.List;

import xatu.school.bean.BaseSingleCourse;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.FileBean;
import xatu.school.bean.ScoreItem;
import xatu.school.bean.Semester;
import xatu.school.bean.SingleCourse;
import xatu.school.service.DBManager;

/**
 * 所有成绩界面 管理器
 * Created by penfi on 2015/11/10.
 */
public class CourseGradesManager {
    private static CourseGradesManager mInstance;// 单例模式

    private CourseGradesManager() {
    }

    public static CourseGradesManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new CourseGradesManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取课程成绩信息
     */
    public List<FileBean> getCourseGradesInfo() {
        List<FileBean> data = new ArrayList<>();
        DBManager dbManager = new DBManager();
        CourseGrades courseGrades = dbManager.getCourseGrades();
        List<Semester> semesters = courseGrades.getSemester();
        Semester mSemester;
        int maxSemester = semesters.size() + 1;
        for (int semester = 0; semester < semesters.size(); semester++) {
            mSemester = semesters.get(semester);
            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setSession(mSemester.getName());
            FileBean fileBean = new FileBean(semester + 1, 0, scoreItem);// 父
            data.add(fileBean);
            addSingleCourse(maxSemester, semester + 1, data, mSemester.getSourceSingleCourses());// 子
        }
        return data;
    }

    private void addSingleCourse(int maxSemester, int semester, List<FileBean> data, List<BaseSingleCourse> mSemester) {
        SingleCourse singleCourse;
        for (int courseId = 0; courseId < mSemester.size(); courseId++) {
            singleCourse = (SingleCourse) mSemester.get(courseId);
            String grade;
            switch (singleCourse.getStatus()) {
                case 1:
                    grade = "没考试";
                    break;
                case 2:
                    grade = "归档中";
                    break;
                case 3:
                    grade = "未评价";
                    break;
                case 4:
                    grade = String.valueOf(singleCourse.getChengji());
                    break;
                default:
                    grade = "未知错误";
                    break;
            }

            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setCourseName(singleCourse.getName());
            scoreItem.setCouresScore(grade);
            FileBean fileBean = new FileBean(maxSemester, semester, scoreItem);
            data.add(fileBean);
        }
    }
}
