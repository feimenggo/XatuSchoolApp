package xatu.school.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import xatu.school.R;
import xatu.school.control.CreateCourseTableManager;

/**
 * 创建课程界面
 */
public class CreateCourseTableActivity extends BaseActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {
    private ImageButton back;
    private ImageButton ok;

    private EditText courseName;
    private NumberPicker weekStart;
    private NumberPicker weekEnd;
    private NumberPicker day;
    private NumberPicker sectionStart;
    private NumberPicker sectionEnd;
    private EditText courseRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_create_course_table);
        TextView title = (TextView) findViewById(R.id.id_title);
        back = (ImageButton) findViewById(R.id.btn_left);
        ok = (ImageButton) findViewById(R.id.btn_right);

        courseName = (EditText) findViewById(R.id.et_course_name);
        courseRoom = (EditText) findViewById(R.id.et_course_room);
        weekStart = (NumberPicker) findViewById(R.id.np_week_start);
        weekEnd = (NumberPicker) findViewById(R.id.np_week_end);
        day = (NumberPicker) findViewById(R.id.np_day);
        sectionStart = (NumberPicker) findViewById(R.id.np_section_start);
        sectionEnd = (NumberPicker) findViewById(R.id.np_section_end);

        title.setText("创建课程");

        // 设置周数
        weekStart.setMinValue(1);
        weekStart.setMaxValue(25);
        weekEnd.setMinValue(1);
        weekEnd.setMaxValue(25);
        weekStart.setOnValueChangedListener(this);
        // 设置节数
        String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五"};
        day.setDisplayedValues(week);
        day.setValue(2);
        day.setMinValue(0);
        day.setMaxValue(week.length - 1);
        sectionStart.setMinValue(1);
        sectionStart.setMaxValue(10);
        sectionEnd.setMinValue(1);
        sectionEnd.setMaxValue(10);
        sectionStart.setOnValueChangedListener(this);

        ok.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                String newCourseName = courseName.getText().toString();
                if (TextUtils.isEmpty(newCourseName)) {
                    Toast.makeText(this, "课程名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                int newCourseWeekStart = weekStart.getValue();
                int newCourseWeekEnd = weekEnd.getValue();
                int newCourseDay = day.getValue() + 1;
                int newCourseSectionStart = sectionStart.getValue();
                int newCourseSectionEnd = sectionEnd.getValue();
                String newCourseRoom = courseRoom.getText().toString();

                boolean result = CreateCourseTableManager.getInstance().createNewCourse(newCourseName, newCourseDay, newCourseRoom, newCourseWeekStart, newCourseWeekEnd, newCourseSectionStart, newCourseSectionEnd);
                if (result) {
                    // 成功
                    setResult(Activity.RESULT_OK);
                } else {
                    // 失败
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("检测到同时间段有课，请重新选择。");
                    builder.setPositiveButton("明白", null);
                    builder.show();
                    return;
                }
                finish();
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        switch (numberPicker.getId()) {
            case R.id.np_week_start:
                weekEnd.setMinValue(newVal);
                break;
            case R.id.np_section_start:
                sectionEnd.setMinValue(newVal);
                break;
        }
    }
}
