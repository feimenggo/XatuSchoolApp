package xatu.school.control;


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
     * 更新信息
     */
    public void updateInfo() {

    }
}
