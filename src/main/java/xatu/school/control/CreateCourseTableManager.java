package xatu.school.control;

import java.util.List;

import feimeng.coursetableview.SimpleSection;
import feimeng.coursetableview.Utils;
import xatu.school.bean.CourseTable;
import xatu.school.service.DBManager;

/**
 * 创建课程表界面 控制器
 * Created by penfi on 2015/11/15.
 */
public class CreateCourseTableManager {
    private static CreateCourseTableManager instance;// 单例模式

    private CreateCourseTableManager() {

    }

    public static CreateCourseTableManager getInstance() {
        if (instance == null) {
            synchronized (CreateCourseTableManager.class) {
                if (instance == null)
                    instance = new CreateCourseTableManager();
            }
        }
        return instance;
    }

    public boolean createNewCourse(String name, int day, String room, int weekStart, int weekEnd, int sectionStart, int sectionEnd) {
        // 判断课程是否冲突
        List<SimpleSection> allCourses = CourseTableManager.getInstance().getCourseTable();
        // 遍历所有课程
        for (SimpleSection course : allCourses) {
            // 是否是同一天
            if (course.getDay() == day) {
                // 是否周次和节次都重复
                if (Utils.isRepeat(course.getWeekStart(), course.getWeekEnd(), weekStart, weekEnd) && Utils.isRepeat(course.getStartSection(), course.getEndSection(), sectionStart, sectionEnd)) {
                    return false;
                }
            }
        }
        CourseTable.Subject course = new CourseTable.Subject();
        course.courseName = name;
        course.day = day;
        course.zhouci = weekStart + "-" + weekEnd;
        course.jieci = sectionStart + "-" + sectionEnd;
        course.jiaoshi = room;
        DBManager dbManager = new DBManager();
        dbManager.saveNewCourse(course);
        return true;
    }

}
