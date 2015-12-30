package xatu.school.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import xatu.school.R;
import xatu.school.adapter.UseAdapter;
import xatu.school.bean.FileBean;
import xatu.school.bean.SingleCourse;
import xatu.school.control.CourseGradesManager;
import xatu.school.control.StudyManager;

/**
 * 课程成绩界面
 * Created by mmcc on 2015/11/10.
 */
public class CourseGradesActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 标题栏 返回按钮
    private ListView mTree;
    private UseAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    private List<SingleCourse> AllCourseInfo;
    @Override
    protected void onResume() {
        super.onResume();
        if(EvaluateActivity.isSucceed)   //判断是否已经在服务器提交成功
        {
            //重新获取最新数据
            mDatas.clear();
            AllCourseInfo.clear();
            mDatas.addAll(CourseGradesManager.getInstance().getCourseGradesInfo());
            AllCourseInfo.addAll(StudyManager.getInstance().getAllCourseInfo());
            EvaluateActivity.isSucceed=false;
            mAdapter.notifyDataSetChanged();
        }else{
          //  Log.i("Tag","未提交dialog,不需要刷新页面");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mTree = (ListView) findViewById(R.id.score_listview);
        initData();
        initViews();// 控件初始化
        try {
            mAdapter = new UseAdapter<>(mTree, this, mDatas, 0,AllCourseInfo);
            mTree.setAdapter(mAdapter);

            mAdapter.setEvaluateClick(new UseAdapter.OnEvaluateClick() {
                @Override
                public void onEvaluateClick(SingleCourse singleCourse) {   //需要评价时的点击事件
                        if(singleCourse!=null)
                        {
                            Intent intent = new Intent(CourseGradesActivity.this, EvaluateActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(StudyFragment.SINGLE_COURSE, singleCourse);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                }
            });

            mAdapter.setOnClick(new UseAdapter.OnLongClick() {
                @Override
                public void onlongClick(SingleCourse singleCourse) {
                       if(singleCourse!=null)
                       {
                           Intent intent = new Intent(CourseGradesActivity.this, SingleCourseActivity.class);
                           Bundle bundle = new Bundle();
                           bundle.putSerializable(StudyFragment.SINGLE_COURSE,singleCourse);
                           intent.putExtras(bundle);
                           startActivity(intent);
                       }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas = CourseGradesManager.getInstance().getCourseGradesInfo();
        AllCourseInfo = StudyManager.getInstance().getAllCourseInfo();
    }

    private void initViews() {
        mReturn = (ImageButton) findViewById(R.id.btn_left);
        TextView title = (TextView) findViewById(R.id.id_title);

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
