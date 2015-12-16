package xatu.school.control;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import xatu.school.bean.CourseGrades;
import xatu.school.bean.InitMsg;
import xatu.school.service.DBManager;
import xatu.school.service.GetCourseGradesFromNetImp;
import xatu.school.service.IGetCourseGradesFromNet;
import xatu.school.service.StudentLoginImp;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;

/**
 * 主界面控制器
 * Created by penfi on 2015/12/14.
 */
public class MainManager {
    private static MainManager mInstance;// 单例模式
    private InitMsg mMsg;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 88:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);// 更新cookie时间
                        // 获取课程成绩
                        IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
                        courseGrades.getCourseGrades(mMsg);
                    }
                    break;
            }
        }
    };

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
     * 刷新
     * 异步返回CourseGrades对象
     *
     * @param context 上下文
     * @param handler hander
     */
    public void refresh(Context context, Handler handler) {
        mMsg = new InitMsg(context, handler, Code.CONTROL.REFRESH);
        if (!CookieUtil.check()) {// cookie 过期 先进行重新登录操作
            new StudentLoginImp().loginWithOcr(new InitMsg(context, mHandler, 88), CookieUtil.getUsername(), CookieUtil.getPassword());
        } else {// cookie 没过期 直接刷新
            IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
            courseGrades.getCourseGrades(mMsg);
        }
    }

    /**
     * 自动刷新
     *
     * @param context 上下文
     * @param handler hander
     */
    public void autoRefresh(Context context, Handler handler) {
        mMsg = new InitMsg(context, handler, Code.CONTROL.AUTO_REFRESH);
        if (!CookieUtil.check()) {// cookie 过期 先进行重新登录操作
            new StudentLoginImp().loginWithOcr(new InitMsg(context, mHandler, 88), CookieUtil.getUsername(), CookieUtil.getPassword());
        } else {// cookie 没过期 直接刷新
            IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
            courseGrades.getCourseGrades(mMsg);
        }
    }

    /**
     * 刷新数据库数据
     *
     * @param courseGrades 课程成绩对象
     */
    public void refreshToDb(CourseGrades courseGrades) {
        DBManager save = new DBManager();
        save.updateCourseGrades(courseGrades);
    }
}
