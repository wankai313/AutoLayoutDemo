package com.wankai.android_autolayout_demo.viewhorlder;

import com.wankai.android_autolayout_demo.R;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 标题Viewhorlder
 * 
 * @author wk
 * @Description
 * @date 2016年8月12日
 */
public class TitleViewHorlder extends BaseViewHorlder {

	public LinearLayout llBack;
	public TextView tvBack;
	public ImageView ivBack;

	public LinearLayout llJump;
	public TextView tvJump;
	public ImageView ivJump;

	private TextView tvTitle;

	public TitleViewHorlder(View view) {
		super(view);
	}

	public TitleViewHorlder(Activity activity) {
		super(activity);
	}

	@Override
	protected void findView() {
		llBack = (LinearLayout) findViewById(R.id.default_ll_back);
		tvBack = (TextView) findViewById(R.id.default_tv_back);
		ivBack = (ImageView) findViewById(R.id.default_iv_back);
		llJump = (LinearLayout) findViewById(R.id.default_ll_jump);
		tvJump = (TextView) findViewById(R.id.default_tv_jump);
		ivJump = (ImageView) findViewById(R.id.default_iv_jump);
		tvTitle = (TextView) findViewById(R.id.default_tv_title);

	}
}
