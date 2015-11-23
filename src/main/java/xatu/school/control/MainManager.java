package xatu.school.control;

import java.text.ParseException;

import xatu.school.activity.BaseApplication;
import xatu.school.bean.CourseInfoSection;
import xatu.school.bean.CourseTable;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.CourseGrades;
import xatu.school.service.CourseTableImp;
import xatu.school.service.DBManager;
import xatu.school.service.ICourseTable;
import xatu.school.service.IMainSectionInfo;
import xatu.school.service.MainSectionInfoImp;
import xatu.school.utils.WeekNumber;

/**
 * 主界面控制器
 * Created by penfi on 2015/10/25.
 */
public class MainManager {
    private static MainManager mInstance;// 单例模式

    private MainManager() {

    }

    public static MainManager getInstance() {
        if (mInstance == null) {
            synchronized (MainManager.class) {
                if (mInstance == null) {
                    mInstance = new MainManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取个人信息
     */
    public StudentInfo getStudentInfo() {
        DBManager dbManager = new DBManager();
        return dbManager.getStudentInfo();
    }


    /**
     * 获取课程成绩信息
     */
    public CourseGrades getSemesterInfo() {
        DBManager dbManager = new DBManager();
        return dbManager.getCourseGrades();
    }

    /**
     * 获取课程概况信息
     */
    public CourseInfoSection getCourseInfo() {
        IMainSectionInfo info = new MainSectionInfoImp();
        CourseGrades semesterInfo = getSemesterInfo();
        return info.getCourseInfoSection(semesterInfo);
    }

    public CourseTable getCourseTableInfo() {
        String startDate = "2015-9-07";// 第一周起始日期
        String endDate = WeekNumber.getNowDate();// 当前日期

        // 获取当前周数
        int nowWeek = 1;
        try {
            nowWeek = WeekNumber.calWeekNumber(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //从数据库获取课程表信息
        DBManager dbManager = new DBManager();
        CourseTable courseTable = dbManager.getCourseTable();

        //获取当前周的课程表信息
        ICourseTable table = new CourseTableImp();
        return table.getCourseTableByWeek(courseTable, nowWeek);
    }

    /**
     * 退出登录
     */
    public boolean logout() {
        boolean result = new DBManager().clearAllTable();
        if (result) {
            BaseApplication.getEditor().putBoolean(BaseApplication.SP_IS_LOGIN, false);
            BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
            BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSEGRADES_INFO, false);
            BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, false);
            BaseApplication.getEditor().apply();
        }
        return result;
    }
}
