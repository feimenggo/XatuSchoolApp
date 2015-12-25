package xatu.school.activity;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import xatu.school.R;
import xatu.school.control.AboutManager;
import xatu.school.control.DefaultManager;

/**
 * Created by penfi on 2015/11/20.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 返回
    private Button mCheck; //检查更新
    private Button mContact;  //点击复制群号

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
        mContact= (Button) findViewById(R.id.our_contact);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("关于我们");
        mReturn.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.check_update:
                DefaultManager.getInstance().checkUpdate(this);
                break;
            case R.id.our_contact:
                Clipboard(); //点击复制群号
                break;
        }
    }

    private void Clipboard() {
        String contact="497230675";
        ClipboardManager cmb= (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        cmb.setText(contact);
        Toast.makeText(this,"群号已复制剪切板!",Toast.LENGTH_SHORT).show();
    }
}
