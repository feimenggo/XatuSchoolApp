package xatu.school.control;

import android.content.Context;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

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
    public void checkUpdate(final Context context) {
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                                               @Override
                                               public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                                                   //状态在statusCode中，0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时。
                                                   switch (updateStatus) {
                                                       case UpdateStatus.No: // has no update
                                                           Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
                                                           break;
                                                       case UpdateStatus.NoneWifi: // none wifi
                                                           Toast.makeText(context, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                                           break;
                                                       case UpdateStatus.Timeout: // time out
                                                           Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                                                           break;
                                                   }
                                               }
                                           }

        );
        UmengUpdateAgent.forceUpdate(context);
    }
}
