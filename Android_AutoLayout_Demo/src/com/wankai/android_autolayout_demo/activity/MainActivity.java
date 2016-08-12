package com.wankai.android_autolayout_demo.activity;

import java.util.List;

import com.wankai.android_autolayout_demo.R;
import com.wankai.android_autolayout_demo.presenter.MainPresener;
import com.wankai.android_autolayout_demo.userview.MainView;
import com.wankai.android_autolayout_demo.viewhorlder.TitleViewHorlder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity implements OnClickListener, MainView {

	private TitleViewHorlder tvh;

	private MainPresener mainPresener;

	@Override
	protected int initLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void loadData() {
		mainPresener.loadData();
	}

	@Override
	protected void init() {
		mainPresener = new MainPresener();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void showData(List<String> list) {

	}

}
