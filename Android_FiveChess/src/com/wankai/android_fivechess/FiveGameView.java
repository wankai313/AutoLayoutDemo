package com.wankai.android_fivechess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.LauncherApps.Callback;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class FiveGameView extends SurfaceView implements OnGestureListener {

	private Point[][] points = null;

	private float[] lines; // 棋盘

	private int width; // 画布宽
	private int height; // 画布高

	private int magrin = 30;

	private Paint mPaint;

	private Rect mRect;

	private GestureDetector detector;

	private static DisplayMetrics outMetrics;

	private static Bitmap whiteBm;

	private static Bitmap blackBm;

	private static Rect bmRect;

	private Map<int[], Point> showList;

	private Map<Point, Integer> showType;

	private int type = 0; // 0 是白色，1是黑色

	private boolean isFinish = false;

	private WCallBack mCallback;

	public FiveGameView(Context context) {
		this(context, null);
	}

	public FiveGameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FiveGameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		mPaint.setStrokeWidth(1);
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mRect = new Rect();

		detector = new GestureDetector(getContext(), this);

		whiteBm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_black);
		blackBm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white);

		bmRect = new Rect(0, 0, whiteBm.getWidth(), whiteBm.getHeight());
		setShowList(new HashMap<int[], Point>());
		setShowType(new HashMap<Point, Integer>());
	}

	public void setCallBack(WCallBack callBack) {
		mCallback = callBack;
	}

	public void replace() {
		setFinish(false);
		setType(0);
		getShowList().clear();
		getShowType().clear();
		invalidate();
	}

	private boolean isFrist = true;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int wMode = MeasureSpec.getMode(widthMeasureSpec);
		int wSize = MeasureSpec.getSize(widthMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);
		int hSize = MeasureSpec.getSize(heightMeasureSpec);
		if (wMode == MeasureSpec.EXACTLY) {
			width = wSize;
		} else {
			width = getMetrics().widthPixels;
		}
		if (hMode == MeasureSpec.EXACTLY) {
			height = hSize;
		} else {
			height = getMetrics().heightPixels;
		}

		if (width > height) {
			width = height;
		} else {
			height = width;
		}
		if (width != 0 && isFrist) {
			mRect = new Rect(0, 0, width, height);
			int w = width - magrin * 2;
			isFrist = false;
			points = new Point[9][9];
			lines = new float[72];
			int count = 0;
			int index = 0;
			for (int i = 0; i < 9; i++) { // y
				float y = magrin + i * 1.0f * w / 8;
				for (int j = 0; j < 9; j++) { // x
					float x = magrin + j * 1.0f * w / 8;
					points[i][j] = new Point(Math.round(x), Math.round(y));

					// showBm.put(index, points[index]);
					index++;
					if (index == 81) {
						break;
					}
				}
				for (int j = 0; j < 2; j++) {
					float x = magrin + j * 1.0f * w;
					lines[count++] = x;
					lines[count++] = y;
				}

				float x = magrin + i * 1.0f * w / 8;
				for (int j = 0; j < 2; j++) {
					float y2 = magrin + j * 1.0f * w;
					lines[count++] = x;
					lines[count++] = y2;
				}

			}
			if (showList != null && !showList.isEmpty()) {
			}
		}

		setMeasuredDimension(width, height);

	}

	private DisplayMetrics getMetrics() {
		if (outMetrics == null) {
			outMetrics = new DisplayMetrics();
			((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
		}

		return outMetrics;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.WHITE);
		canvas.drawRect(mRect, mPaint);
		mPaint.setColor(Color.BLACK);
		canvas.drawLines(lines, mPaint);
		if (getShowList() != null && !getShowList().isEmpty()) {
			for (int i = 0; i < getShowList().size(); i++) {
				Iterator<int[]> iter = getShowList().keySet().iterator();
				while (iter.hasNext()) {
					int[] count = iter.next();
					Point point = getShowList().get(count);
					if (getShowType().get(point) == 0) {
						canvas.drawBitmap(whiteBm, point.x - whiteBm.getWidth() / 2, point.y - whiteBm.getHeight() / 2, mPaint);
					} else {
						canvas.drawBitmap(blackBm, point.x - whiteBm.getWidth() / 2, point.y - whiteBm.getHeight() / 2, mPaint);
					}
				}

			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// 判断在哪个点有效范围
		if (!isFinish()) {
			int[] choose = choosePosition(e);
			if (choose != null) {
				Point point = points[choose[0]][choose[1]];
				getShowList().put(choose, point);
				getShowType().put(point, getType());
				// 判断一下这个point周围是不是满足五个
				if (isWin(choose, getType())) {
					// Toast.makeText(getContext(), "win",
					// 1).show();
					setFinish(true);

					if (mCallback != null) {
						String msg = null;
						if (getType() == 0) {
							msg = "白";
						} else {
							msg = "黑";
						}
						mCallback.postResult(getType(), msg + "");
					}

				}
				setType((getType() + 1) % 2);
				this.invalidate();

			}
			return true;
		}

		return false;

	}

	private boolean isWin(int[] choose, int type) {
		int yC = choose[0];
		int xC = choose[1];
		return isWinByHen(yC, xC) || isWinByLie(yC, xC) || isWinByXie(yC, xC);
	}

	private boolean isWinByXie(int yC, int xC) {
		Integer mType = getShowType().get(points[yC][xC]);
		if (mType != null) {
			int startX = yC - 5;
			int endX = yC + 5;
			if (startX < 0) {
				startX = 0;
			}
			if (endX > 9) {
				endX = 9;
			}
			int count = 0;
			for (int i = startX; i < endX; i++) {
				int x = i + xC - yC;
				if (x >= 0 && x < 9) {
					Integer type = getShowType().get(points[i][x]);
					if (type != null && type == mType) {
						count++;
						if (count >= 5) {
							return true;
						}
					} else {
						count = 0;
						if (endX - i < 5) {
							break;
						}
					}
				}
			}
			count = 0;

			for (int i = startX; i < endX; i++) {
				int x = xC + (yC - i);
				if (x >= 0 && x < 9) {
					Log.i("123", "x,y--->" + i + "," + x);
					Integer type = getShowType().get(points[i][x]);
					if (type != null && type == mType) {
						count++;
						if (count >= 5) {
							return true;
						}
					} else {
						count = 0;
						if (endX - i < 5) {
							break;
						}
					}
				}
			}

		}

		return false;
	}

	private boolean isWinByHen(int yC, int xC) {
		Integer mType = getShowType().get(points[yC][xC]);
		if (mType != null) {
			int start = xC - 5;
			int end = xC + 5;
			if (start < 0) {
				start = 0;
			}
			if (end > 9) {
				end = 9;
			}
			int count = 0;
			for (int i = 0; i < end; i++) {
				Integer type = getShowType().get(points[yC][i]);
				if (type != null && type == mType) {
					count++;
					if (count >= 5) {
						return true;
					}
				} else {
					count = 0;
					if (end - i < 5) {
						break;
					}
				}
			}
		}
		return false;
	}

	private boolean isWinByLie(int yC, int xC) {
		Integer mType = getShowType().get(points[yC][xC]);
		if (mType != null) {
			int start = yC - 5;
			int end = yC + 5;
			if (start < 0) {
				start = 0;
			}
			if (end > 9) {
				end = 9;
			}
			int count = 0;
			for (int i = 0; i < end; i++) {
				Integer type = getShowType().get(points[i][xC]);
				if (type != null && type == mType) {
					count++;
					if (count >= 5) {
						return true;
					}
				} else {
					count = 0;
					if (end - i < 5) {
						break;
					}
				}
			}
		}
		return false;
	}

	private int[] choosePosition(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		int yCount = (int) Math.round((x - magrin) / (1.0f / 8 * (width - magrin * 2)));
		int xCount = (int) Math.round((y - magrin) / (1.0f / 8 * (height - magrin * 2)));
		Point point = points[xCount][yCount];
		if (x > point.x - magrin && x < point.x + magrin && y > point.y - magrin && y < point.y + magrin) {
			if (getShowList().isEmpty() || !getShowList().containsValue(point)) {
				return new int[] { xCount, yCount };
			} else {
				Toast.makeText(getContext(), "已经存在啦", 1).show();
			}
		}
		return null;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<int[], Point> getShowList() {
		return showList;
	}

	public void setShowList(Map<int[], Point> showList) {
		this.showList = showList;
	}

	public Map<Point, Integer> getShowType() {
		return showType;
	}

	public void setShowType(Map<Point, Integer> showType) {
		this.showType = showType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public interface WCallBack {
		void postResult(int type, String msg);
	}

}
