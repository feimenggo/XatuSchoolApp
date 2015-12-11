package xatu.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xatu.school.R;
import xatu.school.bean.StudentInfo;
import xatu.school.control.MineManager;

/**
 * Created by mmcc on 2015/11/7.
 */
public class MineFragment extends Fragment{

    private Button mExit; //退出登陆
    private ImageButton mSetting; //设置
    private ImageView mHeader; //头像
    private TextView mCollege,mProfession,mName,mCard,mClass,mBirth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.minelayout,container,false);
        initView(view);
        initData();  //设置数据
        return view;
    }

    private void initData() {
        StudentInfo studentInfo=MineManager.getInstance().getStudentInfo();
        mCollege.setText("院系："+studentInfo.getYuanxi());
        mProfession.setText("专业："+studentInfo.getZhuanye());
        mName.setText("姓名："+studentInfo.getName());
        mCard.setText("学号："+studentInfo.getXuehao());
        mClass.setText("班级："+studentInfo.getBanji());
        mBirth.setText("生日："+studentInfo.getShengri());
        //设置头像
        mHeader.setImageDrawable(studentInfo.getHeadIcon(getActivity()));
    }

    private void initView(View view) {
        mCollege= (TextView) view.findViewById(R.id.id_college);
        mProfession= (TextView) view.findViewById(R.id.id_profession);
        mName= (TextView) view.findViewById(R.id.id_name);
        mCard= (TextView) view.findViewById(R.id.id_card);
        mClass= (TextView) view.findViewById(R.id.id_class);
        mBirth= (TextView) view.findViewById(R.id.id_birth);
        //退出按钮
        mExit= (Button) view.findViewById(R.id.exit_number);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineManager.getInstance().logout();
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
                getActivity().finish();
            }
        });
        //设置按钮
        mSetting= (ImageButton) view.findViewById(R.id.id_setting);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        //头像
        mHeader= (ImageView) view.findViewById(R.id.id_header);
    }


}
