package xatu.school.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import feimeng.coursetableview.CourseTableView;
import feimeng.coursetableview.SimpleSection;
import xatu.school.R;
import xatu.school.adapter.WeekDropdownAdapter;
import xatu.school.control.CourseTableManager;
import xatu.school.utils.Code;

/**
 * 课程表界面
 * Created by mmcc on 2015/11/7.
 */
public class CourseTableFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView mTitle;// 标题栏 内容
    private RelativeLayout titleWeek;// 周次项
    private TextView titleWeekContent;// 周次
    private PopupWindow dropdown;// 下拉列表
    private ListView dropdownWeek;// listview
    private WeekDropdownAdapter adapter;
    private String[] weeks;
    private int nowWeek;// 当前周
    private int selectWeek;// 选中周

    private ImageView createTable;// 添加课程表

    private CourseTableView mCourseTable;// 课程表
    private String[] mDayTitle;// 天的标题
    private String[] mSectionTitle;//节的标题

    private feimeng.coursetableview.CourseTableManager manager;// 课程表管理器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.courselayout, container, false);
        initDatas();// 数据初始化
        initViews(view);// 控件初始化
        initDropdown();// 初始化下拉列表
        return view;
    }

    private void initDatas() {
        weeks = getResources().getStringArray(R.array.week);
        mDayTitle = new String[]{"周一", "周二", "周三", "周四", "周五"};
        mSectionTitle = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        // 当前周
        nowWeek = selectWeek = CourseTableManager.getInstance().getNowWeek();

//        nowWeek = selectWeek = 11;
        // 本周课程表
        manager = new feimeng.coursetableview.CourseTableManager(CourseTableManager.getInstance().getCourseTable(), nowWeek);
    }

    private void initViews(View view) {
        mCourseTable = (CourseTableView) view.findViewById(R.id.coursetable);

        titleWeek = (RelativeLayout) view.findViewById(R.id.ll_title_week);
        titleWeekContent = (TextView) view.findViewById(R.id.tv_title_week_content);
        createTable = (ImageView) view.findViewById(R.id.iv_create);

        mCourseTable.setmDayTitle(mDayTitle);
        mCourseTable.setmSectionTitle(mSectionTitle);
        mCourseTable.setManager(manager);// 设置管理器

        titleWeek.setOnClickListener(this);
        if (nowWeek <= 25) {
            titleWeekContent.setText(weeks[nowWeek - 1]);
        } else {
            titleWeekContent.setText("休假中");
        }
        createTable.setOnClickListener(this);
    }

    private void initDropdown() {
        View layout = LayoutInflater.from(getContext()).inflate(
                R.layout.coursetable_dropdown, null);
        dropdownWeek = (ListView) layout.findViewById(R.id.lv_week);
        adapter = new WeekDropdownAdapter(getContext(), weeks, nowWeek);
        dropdownWeek.setAdapter(adapter);
        dropdownWeek.setOnItemClickListener(this);

        dropdown = new PopupWindow(layout,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        dropdown.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.dropdown_week_bg));
        dropdown.setOutsideTouchable(true);
        dropdown.setFocusable(true);
        dropdown.getContentView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dropdown.setFocusable(false);//失去焦点
//                dropdown.dismiss();//消除pw
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_week:
                if (nowWeek == selectWeek) {
                    dropdown.showAsDropDown(titleWeekContent, -titleWeekContent.getWidth() / 2 - 60, 0);
                } else {
                    dropdown.showAsDropDown(titleWeekContent, -60, 0);
                }
                // 定位listview到当前周
                dropdownWeek.setSelection(selectWeek - 1);
                break;
            case R.id.iv_create:
                // 跳转到创建课程界面
                startActivityForResult(new Intent(getContext(), CreateCourseTableActivity.class), 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                // 刷新数据
                manager.setDatas(CourseTableManager.getInstance().getCourseTable());
                mCourseTable.update();
                Toast.makeText(getActivity(), "创建新课程成功！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 计算选中的周
        selectWeek = position + 1;

        if (nowWeek == selectWeek) {
            titleWeekContent.setText(weeks[position]);
        } else {
            titleWeekContent.setText(weeks[position] + "(非本周)");
        }
        adapter.setSelectWeek(selectWeek);
        dropdown.dismiss();
        // 切换课程表选中的周
        manager.setWeek(selectWeek);
        mCourseTable.update();
    }
}
