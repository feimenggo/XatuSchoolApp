package xatu.school.control;

import java.text.ParseException;

import xatu.school.bean.CourseTable;
import xatu.school.service.CourseTableImp;
import xatu.school.service.DBManager;
import xatu.school.service.ICourseTable;
import xatu.school.utils.WeekNumber;

/**
 * 课程表界面 控制器
 * Created by penfi on 2015/11/15.
 */
public class CourseTableManager {
    private static CourseTableManager instance;// 单例模式

    private CourseTableManager() {

    }

    public static CourseTableManager getInstance() {
        if (instance == null) {
            synchronized (CourseTableManager.class) {
                if (instance == null)
                    instance = new CourseTableManager();
            }
        }
        return instance;
    }

    public CourseTable getPresentCuCourseTable() {
        CourseTable courseTable = this.getAllCourseTable();
        // 判断学期类型 上学期还是下学期
        String startDate;
        if (WeekNumber.getSemesterType()) {
            startDate = "2015-9-07";// 第一周起始日期
        } else {
            startDate = "2015-3-01";// 第一周起始日期
        }
        String endDate = WeekNumber.getNowDate();// 当前日期

        // 获取当前周数
        int nowWeek = 1;
        try {
            nowWeek = WeekNumber.calWeekNumber(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取当前周的课程表信息
        ICourseTable table = new CourseTableImp();
        return table.getCourseTableByWeek(courseTable, nowWeek);
    }

    public CourseTable getAllCourseTable() {
        //从数据库获取课程表信息
        DBManager dbManager = new DBManager();
        return dbManager.getCourseTable();
    }
}
