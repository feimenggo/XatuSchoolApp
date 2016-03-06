package xatu.school.control;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import feimeng.coursetableview.SimpleSection;
import xatu.school.activity.BaseApplication;
import xatu.school.bean.CourseTable;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.CourseGrades;
import xatu.school.service.CourseTableImp2;
import xatu.school.service.DBManager;
import xatu.school.service.GetCourseGradesFromNetImp2;
import xatu.school.service.ICourseTable;
import xatu.school.service.IGetCourseGradesFromNet;
import xatu.school.service.IStudentInfo;
import xatu.school.service.IStudentLogin;
import xatu.school.service.StudentInfoImp2;
import xatu.school.service.StudentLoginImp;
import xatu.school.service.StudentLoginImp2;
import xatu.school.utils.Code;
import xatu.school.utils.CreateInitMsg;
import xatu.school.utils.RefreshTimeUtil;

/**
 * 登录界面管理器
 * Created by penfi on 2015/10/19.
 */
public class LoginManager {
    private static LoginManager mInstance;// 单例模式

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new LoginManager();
                }
            }
        }
        return mInstance;
    }

//    /**
//     * 获取验证码
//     *
//     * @param context 上下文
//     * @param handler handler
//     */
//    public void checkcode(Context context, Handler handler) {
//        StudentLoginImp studentLogin = new StudentLoginImp();
//        studentLogin.getCheckcodePic(CreateInitMsg.msg(context, handler, Code.CONTROL.CHECKCODE));
//    }

//    /**
//     * @param context   上下文
//     * @param handler   handler
//     * @param username  用户名
//     * @param password  密码
//     * @param checkcode 验证码
//     */
//    public void login(Context context, Handler handler,
//                      String username, String password, String checkcode) {
//        StudentLoginImp studentLogin = new StudentLoginImp();
//        studentLogin.login(CreateInitMsg.msg(context, handler, Code.CONTROL.LOGIN),
//                username, password, checkcode);
//    }

    /**
     * 登录 不需验证码
     *
     * @param context  上下文
     * @param handler  handler
     * @param username 用户名
     * @param password 密码
     */
    public void login(final Context context, final Handler handler, final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                IStudentLogin studentLogin = new StudentLoginImp2();
                studentLogin.loginWithOcr(CreateInitMsg.msg(context, handler, Code.CONTROL.LOGIN_WITH_OCR),
                        username, password);
            }
        }).start();
    }

    /**
     * 从网络获取个人信息
     *
     * @param context 上下文
     * @param handler handler
     */
    public void getStudentInfoFromWeb(final Context context, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                IStudentInfo stuInfo = new StudentInfoImp2();
                stuInfo.getStudentInfo(CreateInitMsg.msg(context, handler, Code.CONTROL.STUDENTINFO));
            }
        }).start();
    }


    /**
     * 从网络获取课程表信息
     */
    public void getCourseTableFromWeb(final Context context, final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ICourseTable courseTable = new CourseTableImp2();
                courseTable.getCourseTableFromWeb(CreateInitMsg.msg(context,
                        handler, Code.CONTROL.COURSETABLE));
            }
        }).start();
    }

    /**
     * 从网络获取全年级课程成绩
     *
     * @param context 上下文
     * @param handler handler
     */
    public void getCourseGradesFromWeb(final Context context, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp2();
                courseGrades.getCourseGrades(CreateInitMsg.msg(context, handler, Code.CONTROL.COURSEGRADES));
            }
        }).start();
    }

    /**
     * 保存个人信息
     *
     * @param stuInfo 学生信息
     */
    public void saveStudentInfo(StudentInfo stuInfo) {
        DBManager save = new DBManager();
        save.saveStudentInfo(stuInfo);

        // 更新状态
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_STUDENT_INFO, true);
        BaseApplication.getEditor().apply();
    }

    /**
     * 保存课程成绩信息
     *
     * @param courseGrades 大学信息
     */
    public void saveCourseGradesInfo(CourseGrades courseGrades) {
        DBManager save = new DBManager();
        save.saveCourseGrades(courseGrades);

        // 更新状态
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSEGRADES_INFO, true);
        // 更新自动刷新时间
        RefreshTimeUtil.updateAutoRefreshTime(false);
        BaseApplication.getEditor().apply();
    }

//    /**
//     * 保存课程表信息
//     *
//     * @param courseTable 课程表对象
//     */
//    public void saveCourseTable(CourseTable courseTable) {
//        DBManager save = new DBManager();
//        save.saveCourseTable(courseTable);
//
//        // 更新状态
//        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, true);
//        BaseApplication.getEditor().apply();
//    }

    /**
     * 保存课程节信息
     */
    public void saveCourseSection(List<SimpleSection> sections){
        DBManager save = new DBManager();
        save.saveCourseTable(sections);

        // 更新状态
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, true);
        BaseApplication.getEditor().apply();
    }

//    /**
//     * 从数据库检测是否存在学生信息
//     *
//     * @param context 上下文
//     * @return 存在 true ， 不存在 false
//     */
//    private boolean checkStudentInfoFromDB(Context context) {
//        boolean result;
//        BaseApplication con = (BaseApplication) context.getApplicationContext();
//        result = BaseApplication.getSp().getBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
//        return result;
//    }
//
//    /**
//     * 从数据库检测年级信息
//     *
//     * @return 存在 true ，不存在 false
//     */
//    private boolean checkSemesterInfoFromDB(Context context) {
//        boolean result;
//        BaseApplication con = (BaseApplication) context.getApplicationContext();
//        result = BaseApplication.getSp().getBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
//        return result;
//    }
}
