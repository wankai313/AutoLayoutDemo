package com.wankai.android_autolayout_demo.viewhorlder;

import android.R.anim;
import android.app.Activity;
import android.view.View;

/**
 * ViewHorlder基类
 * 
 * @author wk
 * @Description
 * @date 2016年8月12日
 */
public abstract class BaseViewHorlder {

	private Activity activity;

	private View view;

	private int type;

	public BaseViewHorlder(View view) {
		this.view = view;
		type = 0;
		findView();
	}

	public BaseViewHorlder(Activity activity) {
		this.activity = activity;
		type = 1;
		findView();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public View findViewById(int id) {
		if (type == 0) {
			return view.findViewById(id);
		} else {
			return activity.findViewById(id);
		}
	}

	/**
	 * 找view
	 */
	protected abstract void findView();

}
