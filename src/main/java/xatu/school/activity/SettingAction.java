package xatu.school.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xatu.school.R;
import xatu.school.control.MineManager;

public class SettingAction extends AppCompatActivity implements View.OnClickListener {

    private Button about_btn, exit_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        setContentView(R.layout.setting_activity);
        initView();
    }

    private void initView() {
        about_btn = (Button) findViewById(R.id.about_btn);
        //  exit= (Button) findViewById(R.id.exit);
        exit_number = (Button) findViewById(R.id.exit_number);
        about_btn.setOnClickListener(this);
        //exit.setOnClickListener(this);
        exit_number.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_btn:
                Intent intent = new Intent(SettingAction.this, AboutActivity.class);
                startActivity(intent);
                break;
             /*case R.id.exit:
                 finish();
                 break;*/
            case R.id.exit_number:

                MineManager.getInstance().logout();
                Intent intent2 = new Intent(SettingAction.this, LoginActivity.class);
                startActivity(intent2);
                BaseApplication.getInstance().exit();
                break;
        }
    }
}
