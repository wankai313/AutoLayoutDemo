package com.wankai.android_autolayout_demo.presenter;

import java.util.List;

import com.wankai.android_autolayout_demo.model.MainModel;
import com.wankai.android_autolayout_demo.userview.MainView;
import com.wankai.android_autolayout_demo.userview.UserView;

public class MainPresener implements Presenter {

	private MainView mainView;

	private MainModel mainModel;

	public void loadData() {
		List<String> list = mainModel.loadData();
		mainView.showData(list);
	}

}
