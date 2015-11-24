package xatu.school.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xatu.school.R;
import xatu.school.view.ChangeColor_myView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private List<Fragment> mTabs=new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    //存放所有的指示器
    private List<ChangeColor_myView> mTabIndicators=new ArrayList<ChangeColor_myView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();    //对ViewPager的初始化
        initDatas();
        mViewPager.setAdapter(mAdapter);

        initEvent(); //viewpager滑动事件
    }

    private void initEvent() {
        mViewPager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        theStudyFragment();   //学习页面
        theCourseFragment();  //课程表
        theMineFragment();    //我的信息
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position)
            {
                return mTabs.get(position);
            }
            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    private void theMineFragment() {
        MineFragment mineFragment=new MineFragment();
        mTabs.add(mineFragment);
    }

    private void theCourseFragment() {
        CourseTableFragment courseTableFragment =new CourseTableFragment();
        mTabs.add(courseTableFragment);
    }

    private void theStudyFragment() {

        StudyFragment studyFragment=new StudyFragment();
        mTabs.add(studyFragment);
    }

    private void iniView() {
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        //将四个view放入集合统一管理
        ChangeColor_myView one= (ChangeColor_myView) findViewById(R.id.id_study);
        mTabIndicators.add(one);
        ChangeColor_myView two= (ChangeColor_myView) findViewById(R.id.id_course);
        mTabIndicators.add(two);
        ChangeColor_myView three= (ChangeColor_myView) findViewById(R.id.id_me);
        mTabIndicators.add(three);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        one.setIconAlpha(1.0f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        clickTab(v);  //设置的下面四个tab的点击事件
        switch(v.getId()){   //在这里面写，我们页面其他地方的button点击事件

        }
    }
    //若将clickTab()里面代码和普通的button按钮点击事件放一起，当点击其他按钮是也会触发resetOtherTabs()方法。重置了图标颜色
    private void clickTab(View v) {
        resetOtherTabs();  //重置其他的tab图标的颜色
        switch (v.getId())
        {
            case R.id.id_study:
                mTabIndicators.get(0).setIconAlpha(1.0f); //将单击的那个图标透明度设置为不透明，其他的仍然透明
                mViewPager.setCurrentItem(0,false);//将viewpager切换到第一页，同时将smoothscroll取消
                break;
            case R.id.id_course:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.id_me:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
                break;
        }
    }
    private void resetOtherTabs()
    {
        for(int i =0;i<mTabIndicators.size();i++)
        {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if(positionOffset>0)  //也就是开始滑动时
        {
            //手指左滑动过程中,左边的图标是当前第一页，右边图标是第二页
            ChangeColor_myView left=mTabIndicators.get(position);
            ChangeColor_myView right=mTabIndicators.get(position+1);//手指有滑动时，
            //从第一页到第二页时，左边图标需要渐变透明，
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
