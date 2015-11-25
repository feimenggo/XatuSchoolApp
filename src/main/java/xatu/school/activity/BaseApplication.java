package xatu.school.activity;

import android.app.Application;
import android.content.SharedPreferences;

import xatu.school.db.UniversityDbOpenHelper;

/**
 * 基础Application
 * Created by penfi on 2015/10/21.
 */
public class BaseApplication extends Application {
    public static final String SP_VERSION = "version";// 版本号
    public static final String SP_FIRST_START = "first_start";// 首次启动
    public static final String SP_IS_LOGIN = "is_login";// 是否登录
    public static final String SP_HAS_STUDENT_INFO = "has_student_info";// 是否存在学生信息
    public static final String SP_HAS_COURSEGRADES_INFO = "has_coursegrades_info";// 是否存在课程成绩信息
    public static final String SP_HAS_COURSETABLE_INFO = "has_coursetable_info";// 是否存在课程表信息

    public static final String SP_USERNAME = "username";// 用户名
    public static final String SP_PASSWORD = "passwrod";// 密码
    public static final String SP_COOKIE_TIME = "cookie_time";// 获取或更新cookie的时间戳

    private static final String SP_NAME = "school_sp";
    public static final int VERSION = 1;

    private static SharedPreferences mSp;
    private static SharedPreferences.Editor mEditor;

    private static UniversityDbOpenHelper mDbHelper;// 数据库操作

    @Override
    public void onCreate() {
        super.onCreate();
        init();// 信息初始化
    }

    /**
     * 信息初始化
     */
    private void init() {
        mDbHelper = UniversityDbOpenHelper.getInstance(this.getApplicationContext());
        mSp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

    public static SharedPreferences getSp() {
        return mSp;
    }

    public static SharedPreferences.Editor getEditor() {
        if (mEditor == null)
            mEditor = mSp.edit();
        return mEditor;
    }

    public static UniversityDbOpenHelper getDBHelper() {
        return mDbHelper;
    }
}
