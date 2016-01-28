package xatu.school.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import xatu.school.R;
import xatu.school.adapter.ProblemAdapter;
import xatu.school.bean.CourseGrades;
import xatu.school.bean.EvaluateInfo;
import xatu.school.bean.RadioCheck;
import xatu.school.bean.SingleCourse;
import xatu.school.bean.WebError;
import xatu.school.control.CourseGradesManager;
import xatu.school.utils.Code;
import xatu.school.utils.EvaluateCheckForm;

public class EvaluateActivity extends Activity implements View.OnClickListener {
    private SingleCourse singleCourse;

    private ListView mListView;
    private ProblemAdapter mAdapter;  //适配器
    // private EvaluateBean mContent;
    private ArrayList<RadioCheck> mData; //需要评价的内容

    private Button btn_submit;  //提交按钮
    private int selectRadio[]; //提交的按钮十个 1代表A 2代表B
    public static boolean isSucceed = false; //是否提交成功

    private FrameLayout mProgress;// 进度条
    private TextView mProgressContent;// 进度条显示文字
    boolean isSubmit = false;  //是否可以提交，判断用户选择是否合法

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Code.CONTROL.COURSE_EVALUATE:
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        // 获取新的课程成绩，异步返回 课程成绩 到 “Code.CONTROL.COURSEGRADES”
                        CourseGradesManager.getInstance().getNewCourseGradesFromNet(
                                EvaluateActivity.this, handler);
                        mProgressContent.setText("正在更新课程...");
                    } else {
                        // 得到失败提示信息
                        String error;
                        if (msg.obj == WebError.renzhenpingjiao) {
                            error = "请稍后再评教！";
                            Log.i("Tag", "请您认真评教！！");
                        } else if (msg.obj == WebError.TIMEOUT) {
                            error = "啊，教务网崩溃啦！";
                            Log.i("Tag", "连接超时！");
                        } else if (msg.obj == WebError.FAIL) {
                            error = "教务网，你肿么了！";
                            Log.i("Tag", "访问服务器失败！");
                        } else if (msg.obj == WebError.OTHER) {
                            error = "评教失败！";
                            Log.i("Tag", "评教失败，未知原因！");
                        } else {
                            error = "评教失败，未知原因！";
                            Log.i("Tag", "评教失败，未知原因！");
                        }
                        Toast.makeText(EvaluateActivity.this, error, Toast.LENGTH_SHORT).show();
                        isSucceed = false;
                        finish();
                    }
                    break;
                case Code.CONTROL.COURSEGRADES:// 返回新的课程成绩
                    if (msg.arg1 == Code.RESULT.TRUE) {
                        // 将新的课程成绩更新到数据库
                        CourseGradesManager.getInstance().updateSingleCourseToDB((CourseGrades) msg.obj,
                                singleCourse.getName());
                        isSucceed = true;
                    } else {
                        String error;
                        switch ((WebError) msg.obj) {
                            case TIMEOUT:
                                error = "啊，教务网崩溃啦！";
                                break;
                            case FAIL:
                                error = "教务网，你肿么了！";
                                break;
                            default:
                                error = "未知错误来源";
                                break;
                        }
                        Toast.makeText(EvaluateActivity.this, error, Toast.LENGTH_SHORT).show();
                        isSucceed = false;
                    }
                    finish();
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
        // 得到表单所有单选标题
        String formRadiosTitles[] = EvaluateInfo.formRadioTitles;
        int radiosTitlesLength = formRadiosTitles.length;
        selectRadio = new int[radiosTitlesLength];
        mData = new ArrayList<>();
        for (String formRadiosTitle : formRadiosTitles) {
            mData.add(new RadioCheck(formRadiosTitle));
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
        mProgress = (FrameLayout) findViewById(R.id.submit_progress);
        mProgressContent = (TextView) findViewById(R.id.progressbar_content);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Log.i("Tag","不允许点击返回");
        if (keyCode == KeyEvent.KEYCODE_BACK)
            if (isSubmit)
                return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_btn_submit: //提交选项
                isSubmit = true;  //是否可以提交，判断用户选择是否合法
                int len = selectRadio.length;
                for (int aSelectRadio : selectRadio) {
                    if (aSelectRadio == 0) {
                        isSubmit = false;
                    }
                }

                if (isSubmit) {
                    String submitMessage = EvaluateCheckForm.checkForm(selectRadio);
                    if (submitMessage == null) {
                        mProgress.setVisibility(View.VISIBLE);
                        mProgressContent.setText("正在提交数据...");
                        CourseGradesManager.getInstance().evaluate(this, handler, singleCourse, selectRadio);
                    } else {
                        Toast toast = Toast.makeText(this, submitMessage, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(this, "请填写完整!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
        }
    }
}
