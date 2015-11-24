package xatu.school.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 大学类
 * Created by Administrator on 2015-10-18.
 */
public class CourseGrades {
    List<Semester> semester = new ArrayList<Semester>();

    /**
     * 得到学期集合
     *
     * @return 学期集合
     */
    public List<Semester> getSemester() {
        return semester;
    }

    /**
     * 添加学期
     *
     * @param xq 学期对象
     */

    public void addSemester(Semester xq) {
        semester.add(xq);
    }

}
