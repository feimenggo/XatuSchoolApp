package xatu.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import feimeng.linechartview.LineChartView;
import xatu.school.R;
import xatu.school.bean.CoursePassRate;
import xatu.school.control.StudyManager;

/**
 * Created by mmcc on 2015/11/22.
 */
public class StudyFragment extends Fragment implements View.OnClickListener{

    private TextView mAllCourse;  //总课程数
    private TextView mTongCourse;  //通过课程数
    private TextView mTongguolv;  //通过率
    private Button find_all_course,find_course_btn;//进入所有科目，查找单个科目
    private Button mReset;

    private LineChartView mLineChart; //曲线图
    private List<LineChartView.Coord> mDatas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.studylayout,container,false);
        initData();   //初始化曲线表
        initView(view);
        displayCourseInfoSection();//获取课程信息，并显示在课程信息区
        return view;
    }

    private void initData() {
        mDatas=new ArrayList<LineChartView.Coord>();
        mDatas.add(new LineChartView.Coord(62, "大一上"));
        mDatas.add(new LineChartView.Coord(77, "大一下"));
        mDatas.add(new LineChartView.Coord(92, "大二上"));
        mDatas.add(new LineChartView.Coord(76, "大二下"));
        mDatas.add(new LineChartView.Coord(83, "大三上"));
        mDatas.add(new LineChartView.Coord(61, "大三下"));
        mDatas.add(new LineChartView.Coord(88, "大四上"));
        mDatas.add(new LineChartView.Coord(81, "大四下"));
    }

    private void displayCourseInfoSection() {
        CoursePassRate courseInfo = StudyManager.getInstance().getCourseStatisticsInfo();
        mAllCourse.setText(String.valueOf(courseInfo.getTotal()));
        mTongCourse.setText(String.valueOf(courseInfo.getPass()));
        mTongguolv.setText(String.valueOf(courseInfo.getPassRate()) + "%");
    }
    private void initView(View view) {
        mAllCourse= (TextView) view.findViewById(R.id.id_allkemu);
        mTongCourse= (TextView) view.findViewById(R.id.id_tgkms);
        mTongguolv= (TextView) view.findViewById(R.id.id_tgl);
        mReset= (Button) view.findViewById(R.id.id_reset);
        mReset.setOnClickListener(this);
        find_course_btn= (Button) view.findViewById(R.id.find_course_btn);
        find_course_btn.setOnClickListener(this);
        mLineChart= (LineChartView) view.findViewById(R.id.linechart);//曲线图
        mLineChart.setCoords(mDatas);
        find_all_course= (Button) view.findViewById(R.id.find_all_course);
        find_all_course.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_reset:
                Toast.makeText(getActivity(),"点击了刷新",Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_course_btn:
                Toast.makeText(getActivity(),"点击了搜索",Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_all_course:
                //进入成绩页面
                startActivity(new Intent(getActivity(),FindScore.class));
                break;
        }
    }
}
