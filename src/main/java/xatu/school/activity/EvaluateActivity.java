package xatu.school.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import xatu.school.R;
import xatu.school.adapter.ProblemAdapter;
import xatu.school.bean.EvaluateBean;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.RadioCheck;
import xatu.school.bean.SingleCourse;
import xatu.school.bean.SourceSingleCourse;
import xatu.school.bean.WebError;
import xatu.school.control.CourseGradesManager;
import xatu.school.utils.Code;
import xatu.school.utils.EvaluateCheckForm;
import xatu.school.utils.SourceToSingleCourse;

public class EvaluateActivity extends Activity implements View.OnClickListener {
    private SingleCourse singleCourse;

    private ListView mListView;
    private ProblemAdapter mAdapter;  //适配器
    // private EvaluateBean mContent;
    private ArrayList<RadioCheck> mData; //需要评价的内容

    private Button btn_submit;  //提交按钮
    private int selectRadio[]; //提交的按钮十个 1代表A 2代表B
    public static  boolean isSucceed = false; //是否提交成功

    private FrameLayout mProgress;// 进度条
    private TextView mProgressContent;// 进度条显示文字


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Code.CONTROL.COURSE_EVALUATE:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                      //  CourseGradesManager.getInstance().getSingleCourse(EvaluateActivity.this, handler, singleCourse.getName());
                        mProgressContent.setText("正在更新课程...");
                    } else {
                        // 得到失败提示信息
                        if(msg.obj == WebError.renzhenpingjiao){
                            Log.i("Tag", "两次评价间隔太短");
                        }else{
                            Log.i("Tag", "评价失败，未知原因");
                        }
                    }
                    break;
                case Code.CONTROL.GET_SINGLECOURSE:
                    if(msg.arg1 == Code.RESULT.TRUE)
                    {
                        SingleCourse newSingleCourse= SourceToSingleCourse.toSingleCourse((SourceSingleCourse) msg.obj);
                        CourseGradesManager.getInstance().updateSingleCourseToDB(newSingleCourse);
                        Log.i("Tag", "评价成功");
                        isSucceed = true;
                        finish();
                    }else{
                        Toast.makeText(EvaluateActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
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
        //进度条初始化
        mProgress= (FrameLayout) findViewById(R.id.submit_progress);
        mProgressContent= (TextView) findViewById(R.id.progressbar_content);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_btn_submit: //提交选项
                boolean isSubmit=true;  //是否可以提交，判断用户选择是否合法
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
                    String submitMessage= EvaluateCheckForm.checkForm(selectRadio);
                    if(submitMessage==null){
                        mProgress.setVisibility(View.VISIBLE);
                        mProgressContent.setText("正在提交数据...");
                        CourseGradesManager.getInstance().evaluate(this, handler, singleCourse, selectRadio);
                    }else{
                        Toast.makeText(this,submitMessage,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"请输入填写完整!",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
