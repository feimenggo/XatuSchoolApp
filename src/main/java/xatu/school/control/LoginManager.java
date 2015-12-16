package xatu.school.control;

import android.content.Context;
import android.os.Handler;

import xatu.school.activity.BaseApplication;
import xatu.school.bean.CourseTable;
import xatu.school.bean.StudentInfo;
import xatu.school.bean.CourseGrades;
import xatu.school.service.CourseTableImp;
import xatu.school.service.DBManager;
import xatu.school.service.ICourseTable;
import xatu.school.service.IGetCourseGradesFromNet;
import xatu.school.service.IStudentInfo;
import xatu.school.service.GetCourseGradesFromNetImp;
import xatu.school.service.StudentInfoImp;
import xatu.school.service.StudentLoginImp;
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

    /**
     * 获取验证码
     *
     * @param context 上下文
     * @param handler handler
     */
    public void checkcode(Context context, Handler handler) {
        StudentLoginImp studentLogin = new StudentLoginImp();
        studentLogin.getCheckcodePic(CreateInitMsg.msg(context, handler, Code.CONTROL.CHECKCODE));
    }

    /**
     * @param context   上下文
     * @param handler   handler
     * @param username  用户名
     * @param password  密码
     * @param checkcode 验证码
     */
    public void login(Context context, Handler handler,
                      String username, String password, String checkcode) {
        StudentLoginImp studentLogin = new StudentLoginImp();
        studentLogin.login(CreateInitMsg.msg(context, handler, Code.CONTROL.LOGIN),
                username, password, checkcode);
    }

    /**
     * 登录 不需验证码
     *
     * @param context  上下文
     * @param handler  handler
     * @param username 用户名
     * @param password 密码
     */
    public void login(Context context, Handler handler, String username, String password) {
        StudentLoginImp studentLogin = new StudentLoginImp();
        studentLogin.loginWithOcr(CreateInitMsg.msg(context, handler, Code.CONTROL.LOGIN_WITH_OCR),
                username, password);
    }

    /**
     * 从网络获取个人信息
     *
     * @param context 上下文
     * @param handler handler
     */
    public void getStudentInfoFromWeb(Context context, Handler handler) {
        IStudentInfo stuInfo = new StudentInfoImp();
        stuInfo.getStudentInfo(CreateInitMsg.msg(context, handler, Code.CONTROL.STUDENTINFO));
    }


    /**
     * 从网络获取课程表信息
     */
    public void getCourseTableFromWeb(Context context, Handler handler) {
        ICourseTable courseTable = new CourseTableImp();
        courseTable.getCourseTableFromWeb(CreateInitMsg.msg(context,
                handler, Code.CONTROL.COURSETABLE));
    }

    /**
     * 从网络获取全年级课程成绩
     *
     * @param context 上下文
     * @param handler handler
     */
    public void getCourseGradesFromWeb(Context context, Handler handler) {
        IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
        courseGrades.getCourseGrades(CreateInitMsg.msg(context, handler, Code.CONTROL.COURSEGRADES));
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

    /**
     * 保存课程表信息
     *
     * @param courseTable 课程表对象
     */
    public void saveCourseTable(CourseTable courseTable) {
        DBManager save = new DBManager();
        save.saveCourseTable(courseTable);

        // 更新状态
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, true);
        BaseApplication.getEditor().apply();
    }

    /**
     * 从数据库检测是否存在学生信息
     *
     * @param context 上下文
     * @return 存在 true ， 不存在 false
     */
    private boolean checkStudentInfoFromDB(Context context) {
        boolean result;
        BaseApplication con = (BaseApplication) context.getApplicationContext();
        result = BaseApplication.getSp().getBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
        return result;
    }

    /**
     * 从数据库检测年级信息
     *
     * @return 存在 true ，不存在 false
     */
    private boolean checkSemesterInfoFromDB(Context context) {
        boolean result;
        BaseApplication con = (BaseApplication) context.getApplicationContext();
        result = BaseApplication.getSp().getBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
        return result;
    }
}
