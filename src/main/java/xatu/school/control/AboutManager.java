package xatu.school.control;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;

/**
 * 关于界面 控制器
 * Created by penfi on 2015/12/14.
 */
public class AboutManager {
    private static AboutManager mInstance;// 单例模式

    private AboutManager() {
    }

    public static AboutManager getInstance() {
        if (mInstance == null) {
            synchronized (AboutManager.class) {
                if (mInstance == null) {
                    mInstance = new AboutManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 版本更新
     *
     * @param context 上下文
     */
    public void checkUpdate(Context context) {
        UmengUpdateAgent.forceUpdate(context);
//        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//            @Override
//            public void onUpdateReturned(int statusCode, UpdateResponse updateResponse) {
//                //状态在statusCode中，0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时。
//            }
//        });
    }
}
