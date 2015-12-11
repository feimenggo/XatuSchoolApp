package xatu.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import xatu.school.R;
import xatu.school.bean.SingleCourse;

/**
 * Created by mmcc on 2015/11/27.
 */
public class SingleCourseActivity extends BaseActivity {
    private SingleCourse singleCourse;
    private TextView course_name;
    private TextView course_teacher;
    private TextView course_type;
    private TextView course_status;
    private TextView course_xuefen;
    private TextView course_chengji;

    //actionbar
    private ImageButton btn_left;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_course);
        //得到信息
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        singleCourse= (SingleCourse) bundle.getSerializable(StudyFragment.SINGLE_COURSE);
        initView();
        initData(); //设置数据
    }

    private void initData()
    {
         course_name.setText(singleCourse.getName());
         course_teacher.setText(singleCourse.getRenkejiaoshi());
         course_type.setText(singleCourse.getKaoshileixing());
         course_status.setText(singleCourse.getStatusContent());
         course_xuefen.setText(singleCourse.getXuefen()+"");
         course_chengji.setText(singleCourse.getChengji()+"");
    }
    private void initView()
    {
        course_name= (TextView) findViewById(R.id.course_name);
        course_teacher= (TextView) findViewById(R.id.course_teacher);
        course_type= (TextView) findViewById(R.id.course_type);
        course_status= (TextView) findViewById(R.id.course_status);
        course_xuefen= (TextView) findViewById(R.id.course_xuefen);
        course_chengji= (TextView) findViewById(R.id.course_chengji);
        //actionbar
        btn_left= (ImageButton) findViewById(R.id.btn_left);
        mTitle= (TextView) findViewById(R.id.id_title);
        mTitle.setText("详细信息");
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
