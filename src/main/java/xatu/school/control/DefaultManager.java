package xatu.school.control;


import android.content.Context;

import com.umeng.update.UmengUpdateAgent;

/**
 * 默认控制器
 * Created by penfi on 2015/12/14.
 */
public class DefaultManager {
    private static DefaultManager mInstance;// 单例模式

    private DefaultManager() {
    }

    public static DefaultManager getInstance() {
        if (mInstance == null) {
            synchronized (DefaultManager.class) {
                if (mInstance == null) {
                    mInstance = new DefaultManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 版本自动更新
     */
    public void checkUpdate(Context context) {
        // 自动更新检测 使用了友盟s d k
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateCheckConfig(false);
        UmengUpdateAgent.update(context);
    }

}
