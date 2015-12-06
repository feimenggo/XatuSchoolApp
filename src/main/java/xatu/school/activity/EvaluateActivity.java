package xatu.school.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


import xatu.school.R;
import xatu.school.adapter.ProblemAdapter;
import xatu.school.bean.EvaluateBean;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.RadioCheck;
import xatu.school.bean.SingleCourse;
import xatu.school.control.CourseGradesManager;
import xatu.school.utils.Code;

public class EvaluateActivity extends Activity implements View.OnClickListener {
    private SingleCourse singleCourse;

    private ListView mListView;
    private ProblemAdapter mAdapter;
    // private EvaluateBean mContent;
    private ArrayList<RadioCheck> mData;

    private Button btn_submit;
    private int selectRadio[] = new int[10];
    private boolean isSucceed = false;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Code.CONTROL.COURSE_EVALUATE:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        Log.i("Tag", "评价成功");
                        isSucceed = true;
                        finish();
                    } else {
                        // 得到失败提示信息
                        String error = (String) msg.obj;
                        Log.i("Tag", "评价失败：" + error);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        singleCourse = (SingleCourse) bundle.getSerializable(StudyFragment.SINGLE_COURSE);
        initData();
        initView();
    }

    private void initData() {
//        String a[]={"教师态度是否认真负责","教师态度是","态度是否认真负责"
//                ,"教师态真负责","教认真负责","教师态度是否认真负责"
//                ,"教师态认真负责","教师态度认真负责","教师态认真负责"
//                ,"教师态度是否"};
        // 得到表单所有单选标题
        String formRadiosTitles[] = EvaluateInfo.formRadioTitles;

        mData = new ArrayList<RadioCheck>();
        for (int j = 0; j < 10; j++) {
            mData.add(new RadioCheck(formRadiosTitles[j]));
        }
    }

    private void initView() {
        //按钮
        btn_submit = (Button) findViewById(R.id.evaluate_btn_submit);
        btn_submit.setOnClickListener(this);
        //对list的初始化
        mListView = (ListView) findViewById(R.id.list_evaluate);
        mAdapter = new ProblemAdapter(this, mData, selectRadio);
        mListView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_btn_submit: //提交选项
                Toast.makeText(this, "点击了提交" + singleCourse.getName(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 10; i++)
                    Log.i("Tag", "content=" + selectRadio[i]);
                CourseGradesManager.getInstance().evaluate(this, handler, singleCourse, selectRadio);
                break;
        }
    }
}
