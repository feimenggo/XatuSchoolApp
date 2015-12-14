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


}
