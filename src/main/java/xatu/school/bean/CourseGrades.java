package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程成绩类
 * Created by Administrator on 2015-10-18.
 */
public class CourseGrades {
    List<Semester> mSemester = new ArrayList<>();

    /**
     * 得到学期集合
     *
     * @return 学期集合
     */
    public List<Semester> getSemester() {
        return mSemester;
    }

    /**
     * 添加学期
     *
     * @param semester 学期对象
     */

    public void addSemester(Semester semester) {
        mSemester.add(semester);
    }

}
