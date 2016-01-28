package xatu.school.utils;

import android.util.Log;

import xatu.school.activity.BaseApplication;

/**
 * Cookie工具
 * Created by penfi on 2015/12/2.
 */
public class CookieUtil {

    /**
     * 检测Cookie是否正常
     *
     * @return true 正常，false 过期
     */
    public static boolean check() {
        long cookieTime = BaseApplication.getSp().getLong(BaseApplication.SP_COOKIE_TIME, 0);
        long nowTime = System.currentTimeMillis();
        return nowTime < ((BaseApplication.COOKIE_OUT_TIME * 1000) + cookieTime);
    }

    /**
     * 更新Cookie时间
     *
     * @param auto 是否自动提交
     */
    public static void updateCookieTime(boolean auto) {
        BaseApplication.getEditor().putLong(BaseApplication.SP_COOKIE_TIME, System.currentTimeMillis());
        if (auto)
            BaseApplication.getEditor().commit();
    }

    /**
     * 获取用户名
     *
     * @return 存在 返回String，否则返回null
     */
    public static String getUsername() {
        if (BaseApplication.getSp().getBoolean(BaseApplication.SP_IS_LOGIN, false))
            return BaseApplication.getSp().getString(BaseApplication.SP_USERNAME, "");
        return null;
    }

    /**
     * 获取密码
     *
     * @return 存在 返回String，否则返回null
     */
    public static String getPassword() {
        if (BaseApplication.getSp().getBoolean(BaseApplication.SP_IS_LOGIN, false))
            return BaseApplication.getSp().getString(BaseApplication.SP_PASSWORD, "");
        return null;
    }

    /**
     * 获取Cookie
     *
     * @return Cookie
     */
    public static String getCookieContent() {
        return BaseApplication.getSp().getString(BaseApplication.SP_COOKIE_CONTENT, "");
    }

    /**
     * 更新Cookie
     *
     * @param newCookie Cookie
     */
    public static void updateCookieContent(String newCookie) {
        newCookie = newCookie.substring(0, newCookie.indexOf(";"));
//        Log.i("tag", "更新cookie：" + newCookie);
        BaseApplication.getEditor().putString(BaseApplication.SP_COOKIE_CONTENT, newCookie);
        BaseApplication.getEditor().commit();
    }

}
