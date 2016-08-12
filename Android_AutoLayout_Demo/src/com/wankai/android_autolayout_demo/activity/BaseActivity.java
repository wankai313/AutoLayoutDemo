package com.wankai.android_autolayout_demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseActivity extends FragmentActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(initLayout());
		initView();
		init();
		loadData();
	}

	/**
	 * 初始化视图
	 * 
	 * @return
	 */
	protected abstract int initLayout();

	/**
	 * 初始化view
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void init();

	/**
	 * 加载数据
	 */
	protected abstract void loadData();

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
