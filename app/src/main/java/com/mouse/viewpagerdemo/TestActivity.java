package com.mouse.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mouse.viewpagerdemo.adapter.BaseFragmentPagerAdapter;
import com.mouse.viewpagerdemo.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends FragmentActivity implements View.OnClickListener {
    List<Fragment> mFragmentList;
    ViewPager mViewPager;
    BaseFragmentPagerAdapter mAdapter;
    private String strPageName;
    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mViewPager = findViewById(R.id.vp);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_replace).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_append_one).setOnClickListener(this);
        findViewById(R.id.btn_append_three).setOnClickListener(this);

        strPageName = getResources().getString(R.string.str_page_name);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(getFg(String.format(strPageName, 1)));
        mFragmentList.add(getFg(String.format(strPageName, 2)));
        mFragmentList.add(getFg(String.format(strPageName, 3)));
        mFragmentList.add(getFg(String.format(strPageName, 4)));
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        curIndex = 4;
    }

    private TestFragment getFg(String a) {
        TestFragment fragment = new TestFragment();
        fragment.setTest(a);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                mAdapter.removeFragment(mViewPager.getCurrentItem());
                break;
            case R.id.btn_replace:
                curIndex = curIndex + 1;
                mAdapter.replaceFragment(mViewPager.getCurrentItem(), getFg(String.format(strPageName, curIndex)));
                break;
            case R.id.btn_insert:
                curIndex = curIndex + 1;
                mAdapter.insertFragment(mViewPager.getCurrentItem(), getFg(String.format(strPageName, curIndex)));
                break;
            case R.id.btn_append_one:
                curIndex = curIndex + 1;
                mAdapter.addFragment(getFg(String.format(strPageName, curIndex)));
                break;
            case R.id.btn_append_three:
                List<Fragment> fragmentList = new ArrayList<>(3);
                for (int i = 0; i < 3; i++) {
                    curIndex = curIndex + 1;
                    fragmentList.add(getFg(String.format(strPageName, curIndex)));
                }
                mAdapter.addFragmentList(fragmentList);
                break;
        }

        /*TestFragment eee = getFg("EEE");
        //新增
        mAdapter.addFragment(eee);
        //插入
        mAdapter.insertFragment(1, eee);
        //删除
        mAdapter.removeFragment(mViewPager.getCurrentItem());
         //删除
        mAdapter.removeFragment(mFragmentList.get(1));
        //替换
        mAdapter.replaceFragment(1, eee);*/
        /*//替换
        mAdapter.replaceFragment(mFragmentList.get(0), eee);*/
    }
}
