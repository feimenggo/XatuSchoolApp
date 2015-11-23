package xatu.school.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import feimeng.coursetableview.CouresTableView;
import feimeng.coursetableview.SimpleSection;
import xatu.school.R;
import xatu.school.bean.CourseTable;
import xatu.school.control.CourseTableManager;

/**
 * Created by mmcc on 2015/11/7.
 */
public class CourseFragment extends Fragment implements View.OnClickListener{
    private TextView mTitle;// 标题栏 内容
    private Button mSwitchBtnLeft;// 所有课程表
    private Button mSwitchBtnRight;// 本周课程表

    private CouresTableView mCourseTable;// 课程表
    private String[] mDayTitle;// 天的标题
    private String[] mSectionTitle;//节的标题
    private ArrayList<SimpleSection> mALlSimpleSections = new ArrayList<>();// 所有课程
    private ArrayList<SimpleSection> mPresentSections = new ArrayList<>();// 本周课程

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.courselayout,container,false);
        initDatas();// 数据初始化
        initViews(view);// 控件初始化
        initEvent();// 事件初始化

        return view;
    }

    private void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_btn_left:
                mSwitchBtnLeft.setTextColor(Color.parseColor("#00bbff"));
                mSwitchBtnRight.setTextColor(Color.WHITE);
                mSwitchBtnLeft
                        .setBackgroundResource(R.drawable.switch_btn_pink_left_96);
                mSwitchBtnRight
                        .setBackgroundResource(R.drawable.switch_btn_trans_right_96);

                mCourseTable.updateSimpleSections(mALlSimpleSections);
                break;
            case R.id.switch_btn_right:
                mSwitchBtnLeft.setTextColor(Color.WHITE);
                mSwitchBtnRight.setTextColor(Color.parseColor("#00bbff"));
                mSwitchBtnLeft
                        .setBackgroundResource(R.drawable.switch_btn_trans_left_96);
                mSwitchBtnRight
                        .setBackgroundResource(R.drawable.switch_btn_pink_right_96);

                mCourseTable.updateSimpleSections(mPresentSections);
                break;
        }
    }

    private void initViews(View view) {
        mCourseTable = (CouresTableView) view.findViewById(R.id.coursetable);


        mSwitchBtnLeft = (Button) view.findViewById(R.id.switch_btn_left);
        mSwitchBtnRight = (Button) view.findViewById(R.id.switch_btn_right);


        mCourseTable.setmDayTitle(mDayTitle);
        mCourseTable.setmSectionTitle(mSectionTitle);
        mCourseTable.setmSimpleSections(mPresentSections);
        mCourseTable.setmBorderColor(Color.parseColor("#00bbff"));



        mSwitchBtnLeft.setOnClickListener(this);
        mSwitchBtnRight.setOnClickListener(this);
    }
    private void initDatas() {
        mDayTitle = new String[]{"周一", "周二", "周三", "周四", "周五"};
        mSectionTitle = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        // 所有课程表
        CourseTable allCourseTable = CourseTableManager.getInstance().getAllCourseTable();
        // 本周课程表
        CourseTable presentCourseTable = CourseTableManager.getInstance().getPresentCuCourseTable();

        for (int poi = 1; poi <= 5; poi++) {
            CourseTable.Day day = allCourseTable.get(poi);
            for (int j = 1; j <= 5; j++) {
                CourseTable.Subject subject = day.get(j);
                if (subject.courseName.length() != 0) {
                    String str1 = subject.jieci.split("-")[0];
                    String str2 = subject.jieci.split("-")[1];
                    mALlSimpleSections.add(new SimpleSection(poi, Integer.valueOf(str1),
                            Integer.valueOf(str2), subject.courseName + "@" + subject.jiaoshi));
                }
            }
        }

        for (int poi = 1; poi <= 5; poi++) {
            CourseTable.Day day = presentCourseTable.get(poi);
            for (int j = 1; j <= 5; j++) {
                CourseTable.Subject subject = day.get(j);
                if (subject.courseName.length() != 0) {
                    String str1 = subject.jieci.split("-")[0];
                    String str2 = subject.jieci.split("-")[1];
                    mPresentSections.add(new SimpleSection(poi, Integer.valueOf(str1),
                            Integer.valueOf(str2), subject.courseName + "@" + subject.jiaoshi));
                }
            }
        }


//        mSimpleSections.add(new SimpleSection(1, 1, 2, "计算机通信与网络@教3-204"));
//        mSimpleSections.add(new SimpleSection(1, 3, 4, "微机接口实验@教4-311"));
//        mSimpleSections.add(new SimpleSection(1, 5, 6, "组件技术J1@教3-209"));
//        mSimpleSections.add(new SimpleSection(2, 3, 4, "软件工程@教3-404"));
//        mSimpleSections.add(new SimpleSection(3, 1, 2, "计算机通信与网络@教3-316"));
//        mSimpleSections.add(new SimpleSection(3, 3, 4, "操作系统@教1-205"));
//        mSimpleSections.add(new SimpleSection(3, 5, 6, "汇编程序设计与微机接口@教2-307"));
//        mSimpleSections.add(new SimpleSection(4, 5, 6, "专业外语@教3-211"));
//        mSimpleSections.add(new SimpleSection(4, 9, 10, "围棋入门@教2-206"));
//        mSimpleSections.add(new SimpleSection(5, 1, 2, "汇编程序设计与微机接口@教2-307"));
//        mSimpleSections.add(new SimpleSection(5, 3, 4, "操作系统@教1-205"));
//        mSimpleSections.add(new SimpleSection(5, 5, 8, "计算机通信与网络实验工@1-204"));
    }
}
