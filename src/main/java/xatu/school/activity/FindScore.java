package xatu.school.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import xatu.school.R;
import xatu.school.adapter.UseAdapter;
import xatu.school.bean.FileBean;
import xatu.school.control.CourseGradesManager;

/**
 * 所有成绩界面
 * Created by mmcc on 2015/11/10.
 */
public class FindScore extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 标题栏 返回按钮
    private ListView mTree;
    private UseAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mTree = (ListView) findViewById(R.id.score_listview);
        initData();
        initViews();// 控件初始化
        try {
            mAdapter = new UseAdapter<>(mTree, this, mDatas, 0);

            mTree.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas = CourseGradesManager.getInstance().getCourseGradesInfo();
    }

    private void initViews() {
        mReturn = (ImageButton) findViewById(R.id.btn_left);
        TextView title = (TextView) findViewById(R.id.title);

        mReturn.setOnClickListener(this);
        title.setText("所有成绩");
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
