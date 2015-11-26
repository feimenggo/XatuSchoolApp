package xatu.school.control;

import java.util.ArrayList;

import xatu.school.activity.StudyFragment;
import xatu.school.bean.CoursePassRate;
import xatu.school.bean.SemesterAverageScore;
import xatu.school.bean.CourseGrades;
import xatu.school.service.DBManager;
import xatu.school.service.IGetStudyStatisticsInfo;
import xatu.school.service.GetStudyStatisticsInfoImp;
import xatu.school.service.ISingleCourseInfo;
import xatu.school.service.SingleCourseInfoImp;

/**
 * 学习界面 控制器
 * Created by penfi on 2015/10/25.
 */
public class StudyManager {
    private static StudyManager mInstance;// 单例模式

    private StudyManager() {

    }

    public static StudyManager getInstance() {
        if (mInstance == null) {
            synchronized (StudyManager.class) {
                if (mInstance == null) {
                    mInstance = new StudyManager();
                }
            }
        }
        return mInstance;
    }



    /**
     * 得到课程成绩对象
     */
    private CourseGrades getCourseGrades() {
        return new DBManager().getCourseGrades();
    }

    /**
     * 获取课程统计信息
     */
    public CoursePassRate getCourseStatisticsInfo() {
        IGetStudyStatisticsInfo info = new GetStudyStatisticsInfoImp();
        return info.getCourseInfoSection(getCourseGrades());
    }

    /**
     * 获取各学期平均分信息
     *
     * @return 学期平均分对象
     */
    public SemesterAverageScore getSemesterAveScore() {
        IGetStudyStatisticsInfo info = new GetStudyStatisticsInfoImp();
        return info.getAverageScore(getCourseGrades());
    }
//    public CourseTable getCourseTableInfo() {
//        String startDate = "2015-9-07";// 第一周起始日期
//        String endDate = WeekNumber.getNowDate();// 当前日期
//
//        // 获取当前周数
//        int nowWeek = 1;
//        try {
//            nowWeek = WeekNumber.calWeekNumber(startDate, endDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        //从数据库获取课程表信息
//        DBManager dbManager = new DBManager();
//        CourseTable courseTable = dbManager.getCourseTable();
//
//        //获取当前周的课程表信息
//        ICourseTable table = new CourseTableImp();
//        return table.getCourseTableByWeek(courseTable, nowWeek);
//    }


         /*
          得到所有 学期名+课程名
           */
    public ArrayList<String> getAllCourseInfo(){
        ISingleCourseInfo mCourseInfo=new SingleCourseInfoImp();
        return mCourseInfo.getSingleCourseInfo();
    }
}
