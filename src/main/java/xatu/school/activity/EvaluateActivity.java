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
import xatu.school.bean.WebError;
import xatu.school.control.CourseGradesManager;
import xatu.school.utils.Code;

public class EvaluateActivity extends Activity implements View.OnClickListener {
    private SingleCourse singleCourse;

    private ListView mListView;
    private ProblemAdapter mAdapter;
    // private EvaluateBean mContent;
    private ArrayList<RadioCheck> mData;

    private Button btn_submit;
    private int selectRadio[];
    public static  boolean isSucceed = false;


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
                        if(msg.obj==WebError.renzhenpingjiao)
                        {
                        }
                        Log.i("Tag", "请认真评教!");
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
        Log.i("Tag","isSucceed="+isSucceed);
    }

    private void initData() {
//        String a[]={"教师态度是否认真负责","教师态度是","态度是否认真负责"
//                ,"教师态真负责","教认真负责","教师态度是否认真负责"
//                ,"教师态认真负责","教师态度认真负责","教师态认真负责"
//                ,"教师态度是否"};
        // 得到表单所有单选标题
        String formRadiosTitles[] = EvaluateInfo.formRadioTitles;
        int radiosTitlesLength=formRadiosTitles.length;
        selectRadio=new int[radiosTitlesLength];
        mData = new ArrayList<RadioCheck>();
        for (int j = 0; j < radiosTitlesLength; j++) {
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
                boolean isSubmit=true;
                int len=selectRadio.length;
                for (int i = 0; i < len; i++)
                {
                    if(selectRadio[i]==0)
                    {
                        isSubmit=false;
                    }
                }

                if(isSubmit)
                {
                    CourseGradesManager.getInstance().evaluate(this, handler, singleCourse, selectRadio);
                }else{
                    Toast.makeText(this,"请输入填写完整!",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
