package com.wankai.android_autolayout_demo.model;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements Model {

	public List<String> loadData() {

		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		return list;
	}
}
