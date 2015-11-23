package xatu.school.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import feimeng.coursetableview.CouresTableView;
import feimeng.coursetableview.SimpleSection;
import xatu.school.R;
import xatu.school.bean.CourseTable;
import xatu.school.control.CourseTableManager;

public class CourseTableActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTitle;// 标题栏 内容
    private ImageButton mReturn;// 标题栏 返回按钮

    private Button mSwitchBtnLeft;// 所有课程表
    private Button mSwitchBtnRight;// 本周课程表

    private CouresTableView mCourseTable;// 课程表
    private String[] mDayTitle;// 天的标题
    private String[] mSectionTitle;//节的标题
    private ArrayList<SimpleSection> mALlSimpleSections = new ArrayList<>();// 所有课程
    private ArrayList<SimpleSection> mPresentSections = new ArrayList<>();// 本周课程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);
        initDatas();// 数据初始化
        initViews();// 控件初始化
        initEvent();// 事件初始化
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

    private void initEvent() {

    }

    private void initViews() {
        mCourseTable = (CouresTableView) findViewById(R.id.coursetable);
        mReturn = (ImageButton) findViewById(R.id.btn_left);

        mSwitchBtnLeft = (Button) findViewById(R.id.switch_btn_left);
        mSwitchBtnRight = (Button) findViewById(R.id.switch_btn_right);


        mCourseTable.setmDayTitle(mDayTitle);
        mCourseTable.setmSectionTitle(mSectionTitle);
        mCourseTable.setmSimpleSections(mPresentSections);
        mCourseTable.setmBorderColor(Color.parseColor("#00bbff"));

        mReturn.setOnClickListener(this);

        mSwitchBtnLeft.setOnClickListener(this);
        mSwitchBtnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
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
}
