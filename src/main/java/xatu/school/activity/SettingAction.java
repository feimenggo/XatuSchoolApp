package xatu.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import xatu.school.R;
import xatu.school.control.MineManager;

public class SettingAction extends BaseActivity implements View.OnClickListener {

    private Button about_btn;
    private ImageButton btn_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        setContentView(R.layout.setting_activity);
        initView();
    }

    private void initView() {
        about_btn = (Button) findViewById(R.id.about_btn);
        btn_left= (ImageButton) findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);
        about_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_btn:
                Intent intent = new Intent(SettingAction.this, AboutActivity.class);
                startActivity(intent);
                break;
             case R.id.btn_left:
                 finish();
                 break;

        }
    }
}
