package com.wankai.android_fivechess;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.wankai.android_fivechess.FiveGameView.WCallBack;

public class MainActivity extends Activity implements OnClickListener {

	private FiveGameView fgv;

	private Button btnReplace;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fgv = (FiveGameView) findViewById(R.id.main_fv_info);
		fgv.setCallBack(new WCallBack() {

			@Override
			public void postResult(int type, String msg) {
				if (type == 0) {
					Toast.makeText(getApplicationContext(), "白方获胜", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "黑方获胜", Toast.LENGTH_SHORT).show();
				}

			}
		});

		btnReplace = (Button) findViewById(R.id.main_btn_replace);
		btnReplace.setOnClickListener(this);

		if (savedInstanceState != null) {
			Map<int[], Point> showList = (HashMap<int[], Point>) savedInstanceState.getSerializable("showList");
			Map<Point, Integer> showType = (HashMap<Point, Integer>) savedInstanceState.getSerializable("showType");
			boolean isFinish = savedInstanceState.getBoolean("isFinish");
			int type = savedInstanceState.getInt("type");
			fgv.setShowList(showList);
			fgv.setShowType(showType);
			fgv.setType(type);
			fgv.setFinish(isFinish);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isFinish", fgv.isFinish());
		outState.putInt("type", fgv.getType());
		outState.putSerializable("showList", (Serializable) fgv.getShowList());
		outState.putSerializable("showType", (Serializable) fgv.getShowType());
		// super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_replace:
			fgv.replace();
			break;

		default:
			break;
		}

	}
}
