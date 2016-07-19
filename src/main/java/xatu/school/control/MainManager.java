package xatu.school.control;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import xatu.school.activity.MainActivity;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.InitMsg;
import xatu.school.bean.WebError;
import xatu.school.service.DBManager;
import xatu.school.service.GetCourseGradesFromNetImp2;
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp2();
                                courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.REFRESH));
                            }
                        }).start();
                    } else {
                        String error;
                        switch ((WebError) msg.obj) {
                            case userAndPwdError:
                                error = "亲，学号或密码不正确哦！";
                                break;
                            case checkcodeError:
                                error = "验证码错误！";
                                break;
                            case TIMEOUT:
                                error = "啊，教务网崩溃啦！";
                                break;
                            case OFTEN:
                                error = "登录频繁，惩罚你10秒内不能登录！";
                                break;
                            case FAIL:
                                error = "教务网，你肿么了！";
                                break;
                            case OTHER:
                                error = "未知错误，请重试！";
                                break;
                            default:
                                error = "未知错误来源。";
                                break;
                        }
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        Log.i("error_msg", "刷新：更新Cookie失败");
                    }
                    break;
                case 101:// 自动刷新，更新
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        CookieUtil.updateCookieTime(true);// 更新cookie时间
                        // 获取课程成绩
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp2();
                                courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.AUTO_REFRESH));
                            }
                        }).start();
                    } else {
                        Log.i("error_msg", "自动刷新：更新Cookie失败");
                    }
                    break;
                case Code.CONTROL.REFRESH:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        // 回调界面
                        ((MainActivity) mContext).reflushOver(true);
                        // 刷新数据库
                        refreshToDb((CourseGrades) msg.obj);
                        // 更新自动刷新时间
                        RefreshTimeUtil.updateAutoRefreshTime(true);
                    } else {
                        String error;
                        switch ((WebError) msg.obj) {
                            case TIMEOUT:
                                error = "啊，教务网崩溃啦！";
                                break;
                            case FAIL:
                                error = "教务网，你肿么了！";
                                break;
                            default:
                                error = "未知错误来源";
                                break;
                        }
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        // 回调界面
                        ((MainActivity) mContext).reflushOver(false);
                        Log.i("error_msg", "刷新：刷新失败" + msg.arg1);
                    }
                    break;
                case Code.CONTROL.AUTO_REFRESH:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        // 刷新数据库
                        refreshToDb((CourseGrades) msg.obj);
                        // 更新自动刷新时间
                        RefreshTimeUtil.updateAutoRefreshTime(true);
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
            Log.i("error_msg", "cookie过期了");
            Toast.makeText(mContext, "refresh:cookie过期了", Toast.LENGTH_SHORT).show();
            new StudentLoginImp().loginWithOcr(new InitMsg(mContext, mHandler, 100), CookieUtil.getUsername(), CookieUtil.getPassword());
        } else {// cookie 没过期 直接刷新
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp2();
                    courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.REFRESH));
                }
            }).start();
        }
    }

    /**
     * 自动刷新，结束后不回调界面
     */
    public void autoRefresh() {
        if (RefreshTimeUtil.checkAutoRefreshTime()) {
            Toast.makeText(mContext, "自动刷新", Toast.LENGTH_SHORT).show();
            if (!CookieUtil.check()) {// cookie 过期 先进行重新登录操作
                Toast.makeText(mContext, "autoRefresh:cookie过期了", Toast.LENGTH_SHORT).show();
                new StudentLoginImp().loginWithOcr(new InitMsg(mContext, mHandler, 101), CookieUtil.getUsername(), CookieUtil.getPassword());
            } else {// cookie 没过期 直接刷新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IGetCourseGradesFromNet courseGrades = new GetCourseGradesFromNetImp2();
                        courseGrades.getCourseGrades(new InitMsg(mContext, mHandler, Code.CONTROL.AUTO_REFRESH));
                    }
                }).start();
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
