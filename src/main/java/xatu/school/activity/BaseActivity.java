package xatu.school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 基础Activity
 */
public class BaseActivity extends AppCompatActivity {
    private BaseApplication mApp;// 获取基础Application

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (BaseApplication) getApplication();
    }

    protected BaseApplication getApp() {
        return mApp;
    }
}
