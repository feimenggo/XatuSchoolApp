package xatu.school.utils;

import android.content.Context;
import android.widget.Toast;

import xatu.school.activity.BaseApplication;

/**
 * 刷新时间 工具类
 * Created by penfi on 2015/12/16.
 */
public class RefreshTimeUtil {
    /**
     * 检测自动刷新时间
     *
     * @return true 需要自动刷新，false 不需要自动刷新
     */
    public static boolean checkAutoRefreshTime() {
        long lastRefreshTime = BaseApplication.getSp().getLong(BaseApplication.SP_AUTO_REFRESH_TIME, 0);
        long nowTime = System.currentTimeMillis();
        return nowTime > ((BaseApplication.AUTO_REFRESH_INTERVAL * 1000) + lastRefreshTime);
    }

    /**
     * 更新自动刷新时间
     *
     * @param auto 自动提交
     */
    public static void updateAutoRefreshTime(boolean auto) {
        BaseApplication.getEditor().putLong(BaseApplication.SP_AUTO_REFRESH_TIME, System.currentTimeMillis());
        if (auto) BaseApplication.getEditor().commit();
    }
}
