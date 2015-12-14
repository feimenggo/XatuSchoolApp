package xatu.school.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import xatu.school.R;
import xatu.school.control.AboutManager;
import xatu.school.control.DefaultManager;

/**
 * Created by penfi on 2015/11/20.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 返回
    private Button mCheck; //检查更新

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvent();
    }

    private void initEvent() {

    }

    private void initViews() {
        setContentView(R.layout.activity_about);
        mReturn = (ImageButton) findViewById(R.id.left_return);
        mCheck = (Button) findViewById(R.id.check_update);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("关于我们");
        mReturn.setOnClickListener(this);
        mCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.check_update:
                DefaultManager.getInstance().checkUpdate(this);
        }
    }
}
