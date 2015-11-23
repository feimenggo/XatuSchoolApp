package xatu.school.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import xatu.school.R;

/**
 * Created by penfi on 2015/11/20.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 返回


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
        mReturn = (ImageButton) findViewById(R.id.btn_left);
        TextView title = (TextView) findViewById(R.id.title);

        title.setText("关于我们");
        mReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }
}
