package xatu.school.control;


import java.util.ArrayList;
import java.util.List;

import xatu.school.bean.CourseGrades;
import xatu.school.bean.FileBean;
import xatu.school.bean.ScoreItem;
import xatu.school.bean.Semester;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.service.DBManager;

/**
 * 所有课程界面 管理器
 * Created by penfi on 2015/11/10.
 */
public class FindScoreManager {
    private static FindScoreManager mInstance;// 单例模式

    private FindScoreManager() {
    }

    public static FindScoreManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new FindScoreManager();
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

    private void addSingleCourse(int maxSemester, int semester, List<FileBean> data, List<SourceSingleCourse> mSemester) {
        SourceSingleCourse sourceSingleCourse;
        for (int courseId = 0; courseId < mSemester.size(); courseId++) {
            sourceSingleCourse = mSemester.get(courseId);
            String grade;
            if (sourceSingleCourse.getJidian() == null || sourceSingleCourse.getJidian().length() <= 1) {
                grade = "暂无";
            } else {
                grade = sourceSingleCourse.getJidian() + "分";
            }
            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setCourseName(sourceSingleCourse.getName());
            scoreItem.setCouresScore(grade);
            FileBean fileBean = new FileBean(maxSemester, semester, scoreItem);
            data.add(fileBean);
        }
    }
}
