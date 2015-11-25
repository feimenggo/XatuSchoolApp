package xatu.school.control;

import xatu.school.activity.BaseApplication;
import xatu.school.bean.StudentInfo;
import xatu.school.service.DBManager;

/**
 * “我”界面 控制器
 * Created by penfi on 2015/11/25.
 */
public class MineManager {

    private static MineManager mInstance;// 单例模式

    private MineManager() {

    }

    public static MineManager getInstance() {
        if (mInstance == null) {
            synchronized (MineManager.class) {
                if (mInstance == null) {
                    mInstance = new MineManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 获取学生信息
     */
    public StudentInfo getStudentInfo() {
        return new DBManager().getStudentInfo();
    }

    /**
     * 退出登录
     */
    public void logout() {
        new DBManager().clearAllTable();// 清空数据库记录

        // 修改状态信息
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_IS_LOGIN, false);
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSEGRADES_INFO, false);
        BaseApplication.getEditor().putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, false);
        BaseApplication.getEditor().apply();
    }
}
