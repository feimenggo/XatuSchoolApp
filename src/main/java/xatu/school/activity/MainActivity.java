package xatu.school.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xatu.school.R;
import xatu.school.control.DefaultManager;
import xatu.school.control.MainManager;
import xatu.school.view.ChangeColorMyView;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    //存放所有的指示器
    private List<ChangeColorMyView> mTabIndicators = new ArrayList<ChangeColorMyView>();

    @Override
    protected void onStart() {
        super.onStart();
        if (DefaultManager.getInstance().isShowGuide()) {
            addGuideImage();  //添加引导页面
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInfo(); //自动刷新和版本更新

        setContentView(R.layout.activity_main);
        BaseApplication.getInstance().addActivity(this);
        iniView();    //对ViewPager的初始化
        initDatas();

        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void initInfo() {
        //自动刷新
        MainManager.getInstance(this).autoRefresh();
        //自动检查更新
        DefaultManager.getInstance().checkAutoUpdate(this);
    }

    private void addGuideImage() {
        //找到根布局
        View view = getWindow().getDecorView().findViewById(R.id.layout_contentView);
        if (view == null) return;
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout framelayout = (FrameLayout) viewParent;
            final ImageView guideImage = new ImageView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            guideImage.setLayoutParams(params);
            guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
            guideImage.setImageResource(R.mipmap.guide_bg);
            guideImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DefaultManager.getInstance().showGuided();
                    framelayout.removeView(guideImage);
                }
            });
            framelayout.addView(guideImage);
        }
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.id_viewpager, mTabs.get(0));
        transaction.commit();
    }


    private void initDatas() {
        theStudyFragment();   //添加学习页面于mTab集合众
        theCourseFragment();  //添加课程表
        theMineFragment();    //我的信息
    }

    private void theMineFragment() {
        MineFragment mineFragment = new MineFragment();
        mTabs.add(mineFragment);
    }

    private void theCourseFragment() {
        CourseTableFragment courseTableFragment = new CourseTableFragment();
        mTabs.add(courseTableFragment);
    }

    private void theStudyFragment() {
        StudyFragment studyFragment = new StudyFragment();
        mTabs.add(studyFragment);
    }

    private void iniView() {
        //将四个view放入集合统一管理
        ChangeColorMyView one = (ChangeColorMyView) findViewById(R.id.id_study);
        mTabIndicators.add(one);
        ChangeColorMyView two = (ChangeColorMyView) findViewById(R.id.id_course);
        mTabIndicators.add(two);
        ChangeColorMyView three = (ChangeColorMyView) findViewById(R.id.id_me);
        mTabIndicators.add(three);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        one.setIconAlpha(1.0f);
        one.setIconBitmap(R.mipmap.study_icon_press);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        clickTab(v);  //设置的下面四个tab的点击事件
        switch (v.getId()) {   //在这里面写，我们页面其他地方的button点击事件

        }
    }

    //若将clickTab()里面代码和普通的button按钮点击事件放一起，当点击其他按钮是也会触发resetOtherTabs()方法。重置了图标颜色
    private void clickTab(View v) {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        resetOtherTabs();  //重置其他的tab图标的颜色
        switch (v.getId()) {
            case R.id.id_study:
                mTabIndicators.get(0).setIconAlpha(1.0f); //将文字颜色改变
                mTabIndicators.get(0).setIconBitmap(R.mipmap.study_icon_press); //替换点击时图片
                transaction.replace(R.id.id_viewpager, mTabs.get(0));
                break;
            case R.id.id_course:
                mTabIndicators.get(1).setIconAlpha(1.0f); //将文字颜色改变
                mTabIndicators.get(1).setIconBitmap(R.mipmap.course_icon_press);
                transaction.replace(R.id.id_viewpager, mTabs.get(1));
                break;
            case R.id.id_me:
                mTabIndicators.get(2).setIconAlpha(1.0f); //将文字颜色改变
                mTabIndicators.get(2).setIconBitmap(R.mipmap.mine_icon_press);
                //mViewPager.setCurrentItem(2,false);

                transaction.replace(R.id.id_viewpager, mTabs.get(2));
                break;
        }
        transaction.commit();
    }

    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);  //重置文字颜色
        }

        mTabIndicators.get(0).setIconBitmap(R.mipmap.study_icon);
        mTabIndicators.get(1).setIconBitmap(R.mipmap.course_icon);
        mTabIndicators.get(2).setIconBitmap(R.mipmap.mine_icon);
    }

    //刷新结束
    public void reflushOver() {
       
    }
}
