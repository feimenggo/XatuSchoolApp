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

import xatu.school.R;
import xatu.school.bean.CoursePassRate;
import xatu.school.control.MainManager;

/**
 * Created by mmcc on 2015/11/22.
 */
public class StudyFragment extends Fragment{

    private TextView mAllCourse;  //总课程数
    private TextView mTongCourse;  //通过课程数
    private TextView mTongguolv;  //通过率
    private Button find_all_course;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.studylayout,container,false);
        initView(view);
        displayCourseInfoSection();//获取课程信息，并显示在课程信息区
        return view;
    }

    private void displayCourseInfoSection() {
        CoursePassRate courseInfo = MainManager.getInstance().getCourseInfo();
        mAllCourse.setText(String.valueOf(courseInfo.getTotal()));
        mTongCourse.setText(String.valueOf(courseInfo.getPass()));
        mTongguolv.setText(String.valueOf(courseInfo.getPassRate()) + "%");
    }
    private void initView(View view) {
        mAllCourse= (TextView) view.findViewById(R.id.id_allkemu);
        mTongCourse= (TextView) view.findViewById(R.id.id_tgkms);
        mTongguolv= (TextView) view.findViewById(R.id.id_tgl);
        find_all_course= (Button) view.findViewById(R.id.find_all_course);
        find_all_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入成绩页面
                startActivity(new Intent(getActivity(),FindScore.class));
            }
        });

    }

}
