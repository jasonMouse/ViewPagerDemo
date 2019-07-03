package com.mouse.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mouse.viewpagerdemo.adapter.BaseFragmentPagerAdapter;
import com.mouse.viewpagerdemo.fragment.TestFragment;
import com.mouse.viewpagerdemo.view.ExamMaterialViewPager;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends FragmentActivity implements View.OnClickListener {
    private SlidingMenu slidingMenu;
    private ExamMaterialViewPager vp_main;
    private Button btn_delete;
    private Button btn_replace;
    private Button btn_insert;
    private Button btn_append_one;
    private Button btn_append_three;
    private RelativeLayout rl_right;
    private Button btn_right;
    private List<Fragment> mFragmentList;                       // 页面列表
    private BaseFragmentPagerAdapter mAdapter;                  // 适配器
    private String strPageName;                                 // 页面名称格式
    private int curIndex;                                       // 当前最后一个页面上的数字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingmenumain);
        setSlidingMenu();       //设置SlidingMenu
        bindViews();
        initData();
        initListener();
    }

    private void setSlidingMenu() {
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingmenumain);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindWidth((int) (getResources().getDimension(R.dimen.dp200)));
        slidingMenu.setFadeEnabled(true);
        slidingMenu.setFadeDegree(0.5f);
        slidingMenu.setBehindScrollScale(0.75f);
/*        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);*/
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);    //因为方法三是把slidingmenu写入xml，所以无需attach
        slidingMenu.setMenu(R.layout.layout_menu);
        slidingMenu.setContent(R.layout.activity_test);
    }

    private void bindViews() {
        vp_main = (ExamMaterialViewPager) this.findViewById(R.id.vp_main);
        btn_delete = (Button) this.findViewById(R.id.btn_delete);
        btn_replace = (Button) this.findViewById(R.id.btn_replace);
        btn_insert = (Button) this.findViewById(R.id.btn_insert);
        btn_append_one = (Button) this.findViewById(R.id.btn_append_one);
        btn_append_three = (Button) this.findViewById(R.id.btn_append_three);
        rl_right = (RelativeLayout) this.findViewById(R.id.rl_right);
        btn_right = (Button) this.findViewById(R.id.btn_right);
    }

    private void initData() {
        strPageName = getResources().getString(R.string.str_page_name);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(getFragment(String.format(strPageName, 1)));
        mFragmentList.add(getFragment(String.format(strPageName, 2)));
        mFragmentList.add(getFragment(String.format(strPageName, 3)));
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vp_main.setAdapter(mAdapter);
        vp_main.setOffscreenPageLimit(2);
        curIndex = 3;
    }

    private void initListener() {
        btn_delete.setOnClickListener(this);
        btn_replace.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_append_one.setOnClickListener(this);
        btn_append_three.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        vp_main.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                refreshSlidingMenuTouchModeByIndex(position);
            }
        });
    }

    private TestFragment getFragment(String a) {
        TestFragment fragment = new TestFragment();
        fragment.setTest(a);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        int curPosition = vp_main.getCurrentItem();
        switch (view.getId()) {
            case R.id.btn_delete:
                if (mFragmentList.size() == 0) return;
                mAdapter.removeFragment(curPosition);
                refreshSlidingMenuTouchModeByIndex(vp_main.getCurrentItem());
                break;
            case R.id.btn_replace:
                curIndex = curIndex + 1;
                mAdapter.replaceFragment(curPosition, getFragment(String.format(strPageName, curIndex)));
                break;
            case R.id.btn_insert:
                curIndex = curIndex + 1;
                mAdapter.insertFragment(curPosition, getFragment(String.format(strPageName, curIndex)));
                refreshSlidingMenuTouchModeByIndex(vp_main.getCurrentItem());
                break;
            case R.id.btn_append_one:
                curIndex = curIndex + 1;
                mAdapter.addFragment(getFragment(String.format(strPageName, curIndex)));
                refreshSlidingMenuTouchModeByIndex(vp_main.getCurrentItem());
                break;
            case R.id.btn_append_three:
                List<Fragment> fragmentList = new ArrayList<>(3);
                for (int i = 0; i < 3; i++) {
                    curIndex = curIndex + 1;
                    fragmentList.add(getFragment(String.format(strPageName, curIndex)));
                }
                mAdapter.addFragmentList(fragmentList);
                refreshSlidingMenuTouchModeByIndex(vp_main.getCurrentItem());
                break;
            case R.id.btn_right:
                if (slidingMenu.isMenuShowing()) {
                    slidingMenu.toggle(true);
                }
                vp_main.setCurrentItem(0);
                break;
        }

        /*TestFragment eee = getFragment("EEE");
        //新增
        mAdapter.addFragment(eee);
        //插入
        mAdapter.insertFragment(1, eee);
        //删除
        mAdapter.removeFragment(curPosition);
         //删除
        mAdapter.removeFragment(mFragmentList.get(1));
        //替换
        mAdapter.replaceFragment(1, eee);*/
        /*//替换
        mAdapter.replaceFragment(mFragmentList.get(0), eee);*/
    }

    /**
     * 根据位置刷新 slidingMenu 的触碰滑动状态
     * @param index
     */
    private void refreshSlidingMenuTouchModeByIndex(int index){
        if (index == mFragmentList.size() - 1) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
