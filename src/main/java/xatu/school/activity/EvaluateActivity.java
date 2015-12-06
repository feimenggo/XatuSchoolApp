package xatu.school.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import xatu.school.R;
import xatu.school.adapter.ProblemAdapter;
import xatu.school.bean.RadioCheck;
import xatu.school.bean.SingleCourse;

public class EvaluateActivity extends Activity implements View.OnClickListener {
    private SingleCourse singleCourse;

    private ListView mListView;
    private ProblemAdapter mAdapter;
    // private EvaluateBean mContent;
    private ArrayList<RadioCheck> mData;

    private Button btn_submit;
    private int selectRadio[]=new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        singleCourse= (SingleCourse) bundle.getSerializable(StudyFragment.SINGLE_COURSE);
        initData();
        initView();
    }

    private void initData() {
        String a[]={"教师态度是否认真负责","教师态度是","态度是否认真负责"
                ,"教师态真负责","教认真负责","教师态度是否认真负责"
                ,"教师态认真负责","教师态度认真负责","教师态认真负责"
                ,"教师态度是否"};
        mData=new ArrayList<RadioCheck>();
        for (int j = 0; j < 10; j++)
        {
            mData.add(new RadioCheck(a[j]));
        }
    }

    private void initView() {
        //按钮
        btn_submit= (Button) findViewById(R.id.evaluate_btn_submit);
        btn_submit.setOnClickListener(this);
        //对list的初始化
        mListView= (ListView) findViewById(R.id.list_evaluate);
        mAdapter=new ProblemAdapter(this,mData,selectRadio);
        mListView.setAdapter(mAdapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.evaluate_btn_submit: //提交选项
                Toast.makeText(this,"点击了提交"+singleCourse.getName(),Toast.LENGTH_SHORT).show();
                for (int i=0;i<10;i++)
                    Log.i("Tag","content="+selectRadio[i]);
                finish();
                break;
        }
    }
}