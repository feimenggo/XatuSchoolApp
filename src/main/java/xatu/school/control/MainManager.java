package xatu.school.control;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import xatu.school.activity.MainActivity;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.InitMsg;
import xatu.school.service.DBManager;
import xatu.school.service.GetCourseGradesFromNetImp;
import xatu.school.service.IGetCourseGradesFromNet;
import xatu.school.service.StudentLoginImp;
import xatu.school.utils.Code;
import xatu.school.utils.CookieUtil;
import xatu.school.utils.RefreshTimeUtil;

/**
 * 主界面控制器
 * Created by penfi on 2015/12/14.
 */
public class MainManager {
    private static MainManager mInstance;// 单例模式
    private Context mContext;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:// 刷新，更新
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);// 更新cookie时间
                        // 获取课程成绩
                        IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
                        courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.REFRESH));
                    } else {
                        Log.i("error_msg", "刷新：更新Cookie失败");
                    }
                    break;
                case 101:// 自动刷新，更新
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);// 更新cookie时间
                        // 获取课程成绩
                        IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
                        courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.AUTO_REFRESH));
                    } else {
                        Log.i("error_msg", "自动刷新：更新Cookie失败");
                    }
                    break;
                case Code.CONTROL.REFRESH:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        // 回调界面
                        ((MainActivity) mContext).reflushOver();
                        // 刷新数据库
                        refreshToDb((CourseGrades) msg.obj);
                    } else {
                        Log.i("error_msg", "刷新：刷新失败");
                    }
                    break;
                case Code.CONTROL.AUTO_REFRESH:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        refreshToDb((CourseGrades) msg.obj);
                        RefreshTimeUtil.updateAutoRefreshTime(true);// 更新自动刷新时间
                    } else {
                        Log.i("error_msg", "自动刷新：刷新失败");
                    }
                    break;
            }
        }
    };

    public MainManager(Context context) {
        this.mContext = context;
    }

    public static MainManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MainManager.class) {
                if (mInstance == null) {
                    mInstance = new MainManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 刷新，结束后回调界面
     */
    public void refresh() {
        if (!CookieUtil.check()) {// cookie 过期 先进行重新登录操作
            new StudentLoginImp().loginWithOcr(new InitMsg(mContext, mHandler, 100), CookieUtil.getUsername(), CookieUtil.getPassword());
        } else {// cookie 没过期 直接刷新
            IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
            courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.REFRESH));
        }
    }

    /**
     * 自动刷新，结束后不回调界面
     *
     * @param context 上下文
     */
    public void autoRefresh(Context context) {
        if (RefreshTimeUtil.checkAutoRefreshTime(mContext)) {
            if (!CookieUtil.check()) {// cookie 过期 先进行重新登录操作
                new StudentLoginImp().loginWithOcr(new InitMsg(context, mHandler, 101), CookieUtil.getUsername(), CookieUtil.getPassword());
            } else {// cookie 没过期 直接刷新
                IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp();
                courseGrades.getCourseGrades(new InitMsg(context, mHandler, Code.CONTROL.AUTO_REFRESH));
            }
        }
    }

    /**
     * 刷新数据库数据
     *
     * @param courseGrades 课程成绩对象
     */
    private void refreshToDb(CourseGrades courseGrades) {
        DBManager save = new DBManager();
        save.updateCourseGrades(courseGrades);
    }
}
