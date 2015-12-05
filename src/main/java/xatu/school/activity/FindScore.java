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
 * 所有成绩界面
 * Created by mmcc on 2015/11/10.
 */
public class FindScore extends BaseActivity implements View.OnClickListener {
    private ImageButton mReturn;// 标题栏 返回按钮
    private ListView mTree;
    private UseAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    private List<SingleCourse> AllCourseInfo; //课程的所有信息
    private SingleCourse singleCourse;

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter!=null)
        {
            Log.i("Tag","已经提交了dialog");
            mAdapter.notifyDataSetChanged();
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
            mAdapter = new UseAdapter<>(mTree, this, mDatas, 0);
            mTree.setAdapter(mAdapter);

            mAdapter.setEvaluateClick(new UseAdapter.OnEvaluateClick() {
                @Override
                public void onEvaluateClick(String courseName) {
                    if(AllCourseInfo!=null)
                    {
                        int len=AllCourseInfo.size();
                        for(int i =0;i<len;i++)
                        {
                            if(courseName.equals(AllCourseInfo.get(i).getName()))
                            {
                                singleCourse=AllCourseInfo.get(i);
                            }
                        }
                        if(singleCourse!=null)
                        {
                            Intent intent = new Intent(FindScore.this, EvaluateActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(StudyFragment.SINGLE_COURSE,singleCourse);
                            intent.putExtras(bundle);
                            singleCourse=null;
                            startActivity(intent);
                        }
                    }
                }
            });

            mAdapter.setOnClick(new UseAdapter.OnLongClick() {
                @Override
                public void onlongClick(String courseName) {
                   if(AllCourseInfo!=null)
                   {
                       int len=AllCourseInfo.size();
                       for(int i =0;i<len;i++)
                       {
                           if(courseName.equals(AllCourseInfo.get(i).getName()))
                           {
                               singleCourse=AllCourseInfo.get(i);
                           }
                       }
                       if(singleCourse!=null)
                       {
                           Intent intent = new Intent(FindScore.this, SingleCourseActivity.class);
                           Bundle bundle = new Bundle();
                           bundle.putSerializable(StudyFragment.SINGLE_COURSE,singleCourse);
                           intent.putExtras(bundle);
                           singleCourse=null;
                           startActivity(intent);
                       }
                   }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas = CourseGradesManager.getInstance().getCourseGradesInfo();
        //得到所有的课程信息
        AllCourseInfo= StudyManager.getInstance().getAllCourseInfo();
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
