package xatu.school.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;

import org.jsoup.Connection;

import xatu.school.R;

public class SplashActivity extends BaseActivity {
    private Button mToLogin;// 进入登录界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkVersion();//版本检测和登录状态跳转
        initOtherService();// 第三方服务初始化
//        initViews();// 视图初始化
//        initEvent();// 事件初始化
    }

    /**
     * 版本检测
     */
    private void checkVersion() {
        SharedPreferences sp = BaseApplication.getSp();// 得到SP实例

        // 是否初始化(首次启动)
        boolean firstStart = sp.getBoolean(BaseApplication.SP_FIRST_START, true);
        int version = sp.getInt(BaseApplication.SP_VERSION, 0);
        if (firstStart) {// 首次启动 进行 数据初始化
            initOption();// 配置信息初始化
        } else if (version < BaseApplication.VERSION) {
            //版本升级
            update();
        }

        // 是否登录
        boolean isLogin = sp.getBoolean(BaseApplication.SP_IS_LOGIN, false);
        // 是否获取个人信息
        boolean isHasStudent = sp.getBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
        // 是否获取课程信息
        boolean isHasSemester = sp.getBoolean(BaseApplication.SP_HAS_COURSEGRADES_INFO, false);

        Intent intent;
        if (isLogin && isHasStudent && isHasSemester) {
            // 进入 主界面
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            // 进入 登录界面
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);// 跳转
        finish();// 销毁
    }

    private void update() {
        SharedPreferences.Editor editor = BaseApplication.getEditor();
        editor.putInt(BaseApplication.SP_VERSION, BaseApplication.VERSION);// 修改版本号
        editor.putBoolean(BaseApplication.SP_IS_GUIDE_STUDY, true);
        editor.commit();
        Toast.makeText(SplashActivity.this, "恭喜，版本升级成功！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 配置信息初始化
     */
    private void initOption() {
        SharedPreferences.Editor editor = BaseApplication.getEditor();
        editor.putBoolean(BaseApplication.SP_FIRST_START, false);
        editor.putBoolean(BaseApplication.SP_IS_LOGIN, false);
        editor.putInt(BaseApplication.SP_VERSION, BaseApplication.VERSION);
        editor.putLong(BaseApplication.SP_COOKIE_TIME, 0);
        editor.putString(BaseApplication.SP_USERNAME, "");
        editor.putString(BaseApplication.SP_PASSWORD, "");

        editor.putBoolean(BaseApplication.SP_HAS_STUDENT_INFO, false);
        editor.putBoolean(BaseApplication.SP_HAS_COURSEGRADES_INFO, false);
        editor.putBoolean(BaseApplication.SP_HAS_COURSETABLE_INFO, false);

        editor.putBoolean(BaseApplication.SP_IS_GUIDE_STUDY, true);

        editor.putLong(BaseApplication.SP_AUTO_REFRESH_TIME, 0);
        editor.commit();
    }

    private void initEvent() {
        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入登录界面
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        setContentView(R.layout.activity_splash);
        // 得到登录按钮控件
        mToLogin = (Button) findViewById(R.id.btn_to_login);
    }

    /**
     * 第三方服务初始化
     */
    private void initOtherService() {

    }
}
