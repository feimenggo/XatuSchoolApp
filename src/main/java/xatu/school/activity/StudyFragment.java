package xatu.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import feimeng.linechartview.LineChartView;
import xatu.school.R;
import xatu.school.bean.CoursePassRate;
import xatu.school.bean.SemesterAverageScore;
import xatu.school.bean.SingleCourse;
import xatu.school.control.StudyManager;

/**
 * Created by mmcc on 2015/11/22.
 */
public class StudyFragment extends Fragment implements View.OnClickListener {

    public static final String SINGLE_COURSE = "single_course";
    private TextView mAllCourse;  //总课程数
    private TextView mTongCourse;  //通过课程数
    private TextView mTongguolv;  //通过率
    private Button find_all_course, find_course_btn;//进入所有科目，查找单个科目
    private Button mReset;
    private ScrollView mScrollView;

    private AutoCompleteTextView find_course;
    private List<SingleCourse> AllCourseInfo; //课程的所有信息
    private SingleCourse singleCourse; //单个科目的信息

    private List<String> mCourse; //存放所有用于查询的课程信息

    private LineChartView mLineChart; //曲线图
    private List<LineChartView.Coord> mDatas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.studylayout, container, false);
        initData();   //初始化曲线表
        initView(view);
        displayCourseInfoSection();//获取课程信息，并显示在课程信息区
        return view;
    }

    private void initData() {
        //获得曲线图的信息
        mDatas = SemesterAverageScore.semesterAdapter(StudyManager.getInstance().getSemesterAveScore());
        //获取课程的所有信息的
        AllCourseInfo = StudyManager.getInstance().getAllCourseInfo();
        mCourse = new ArrayList<String>();
        int len = AllCourseInfo.size();

        for (int i = 0; i < len; i++) {
            mCourse.add(AllCourseInfo.get(i).getName());
        }
    }

    private void displayCourseInfoSection() {
        CoursePassRate courseInfo = StudyManager.getInstance().getCourseStatisticsInfo();
        mAllCourse.setText(String.valueOf(courseInfo.getTotal()));
        mTongCourse.setText(String.valueOf(courseInfo.getPass()));
        mTongguolv.setText(String.valueOf(courseInfo.getPassRate()) + "%");
    }

    private void initView(View view) {
        //滑动监听

        mScrollView= (ScrollView) view.findViewById(R.id.id_ScrollView);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    View inputView = getActivity().getWindow().peekDecorView();
                    if (inputView != null) {
                        //滑动时隐藏软键盘
                        InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(inputView.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });

        mAllCourse = (TextView) view.findViewById(R.id.id_allkemu);
        mTongCourse = (TextView) view.findViewById(R.id.id_tgkms);
        mTongguolv = (TextView) view.findViewById(R.id.id_tgl);
        mReset = (Button) view.findViewById(R.id.id_reset);
        mReset.setOnClickListener(this);
        find_course_btn = (Button) view.findViewById(R.id.find_course_btn);
        find_course_btn.setOnClickListener(this);
        mLineChart = (LineChartView) view.findViewById(R.id.linechart);//曲线图
        mLineChart.setCoords(mDatas);
        find_all_course = (Button) view.findViewById(R.id.find_all_course);
        find_all_course.setOnClickListener(this);
        find_course = (AutoCompleteTextView) view.findViewById(R.id.find_course);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, mCourse);
        find_course.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_reset:
                Toast.makeText(getActivity(), "点击了刷新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_course_btn:
                if (find_course.getText() != null) {
                    String course = find_course.getText().toString();
                    for (int i = 0; i < AllCourseInfo.size(); i++)
                    {
                        if (AllCourseInfo.get(i).getName().equals(course))
                        {
                            singleCourse=AllCourseInfo.get(i);
                        }
                    }
                    if(singleCourse!=null)
                    {
                        Intent intent = new Intent(getActivity(), SingleCourseActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SINGLE_COURSE,singleCourse);
                        intent.putExtras(bundle);
                        singleCourse=null;
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(),"请输入正确的课程名!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"请输入要查询的科目",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.find_all_course:
                //进入成绩页面
                startActivity(new Intent(getActivity(), FindScore.class));
                break;
        }
    }
}
