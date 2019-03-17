package com.example.testchart1;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 */
public class MYChart extends View implements OnTouchListener {
	public static String Tag = "MYHistogram_sleep";

	private int width;// �߶�
	private int height;// ���
	private Paint pIndicate;// ָʾ��
	private Paint PBar;// ��״����
	private Paint pInaxle;// ������
	private Paint pLable;// ����

	private int color_indicate;// ָʾ����ɫ
	private int color_bar_deep;// ��״����˯��ɫ
	private int color_bar_light;// ��״��ǳ˯��ɫ
	private int color_bar_wake;// ��״��ǳ˯��ɫ
	private int color_bar_press;// ��״��������ɫ
	private int color_inaxle;// ��������ɫ
	private int color_lable;// ������ɫ

	private Bitmap bitmap_moon;// ����ͼ��
	private Bitmap bitmap_sun;// ̫��ͼ��
	private RectF bitmap_moon_rec;// ����ͼ��λ��
	private RectF bitmap_sun_rec;// ̫��ͼ��λ��

	private List<Smartband_sleepCalitem> data;// ����
	private PointF[][] YPoints;// Y���Ӧ����

	private int padLeft, padRight, padTop, padBot;// �������¼��
	private int lable_txt_size;// �����С
	private String[] sleepTips;// ��˯��ǳ˯������

	// ��ָʾ��ʱ��x����
	private float xTuch = -1;
	private int showIndicateIndex = -1;

	public void setData(List<Smartband_sleepCalitem> data) {
		this.data = data;
		requestLayout();
	}

	public MYChart(Context context) {
		super(context);
	}

	public MYChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MYChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(0xff27a6e9);
		if (YPoints == null) {
			return;
		}

		int iy = (height - padTop - padBot) / 2;// Y��̶ȼ��
		// ��X����
		canvas.drawLine(padLeft, height - padBot, width - padRight, height - padBot, pInaxle);

		/* ��X��Lable */
		canvas.drawBitmap(bitmap_moon, null, bitmap_moon_rec, PBar);// ����
		canvas.drawBitmap(bitmap_sun, null, bitmap_sun_rec, PBar);// ����
		String timeStart = TimeUtil.format(data.get(0).getTimeStart(), "yyyy-MM-dd HH:mm:ss", "HH:mm");
		String timeEnd = TimeUtil.format(data.get(data.size() - 1).getTimeEnd(), "yyyy-MM-dd HH:mm:ss", "HH:mm");
		float txtWidth_end = pLable.measureText(timeEnd);
		canvas.drawText(timeStart, padLeft + lable_txt_size, height - (padBot - lable_txt_size) / 2f, pLable);
		canvas.drawText(timeEnd, width - padRight - txtWidth_end, height - (padBot - lable_txt_size) / 2f, pLable);

		/* ������ */
		for (int i = 0; i < YPoints.length; i++) {
			if (showIndicateIndex == i) {
				PBar.setColor(color_bar_press);
			} else if (data.get(i).getSleepQualityAvg() == 1) {
				PBar.setColor(color_bar_deep);
			} else if (data.get(i).getSleepQualityAvg() == 2) {
				PBar.setColor(color_bar_light);
			} else {
				PBar.setColor(color_bar_wake);
			}

			canvas.drawRect(YPoints[i][0].x, YPoints[i][0].y, YPoints[i][1].x, height - padBot, PBar);
		}

		// ��ָʾ��&��ʾ
		if (showIndicateIndex != -1) {
			String showXValue = TimeUtil.format(data.get(showIndicateIndex).getTimeStart(), "yyyy-MM-dd HH:mm:ss", "HH:mm") + "-" + TimeUtil.format(data.get(showIndicateIndex).getTimeEnd(), "yyyy-MM-dd HH:mm:ss", "HH:mm");
			String showYvalue = sleepTips[data.get(showIndicateIndex).getSleepQualityAvg() - 1];
			String showTxt = showXValue + "(" + showYvalue + ")";
			float txtWidth = pLable.measureText(showTxt);
			canvas.drawText(showTxt, (width - txtWidth) / 2, (padTop + lable_txt_size) / 2, pLable);
			if (xTuch <= (width - padRight) && xTuch >= padLeft) {
				canvas.drawLine(xTuch, padTop, xTuch, height - padBot, pIndicate);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = measureWidth(widthMeasureSpec);
		height = measureHeight(heightMeasureSpec);

		initData();
	}

	void initData() {
		if (data == null) {
			return;
		}

		this.setOnTouchListener(this);
		lable_txt_size = dip2px(getContext(), 10);

		color_indicate = Color.parseColor("#3ca73b");
		pIndicate = new Paint();
		pIndicate.setColor(color_indicate);
		pIndicate.setStyle(Paint.Style.FILL);
		pIndicate.setStrokeWidth(2);
		pIndicate.setAntiAlias(true);
		PathEffect effects = new DashPathEffect(new float[] { 16, 16, 16 }, 1);
		pIndicate.setPathEffect(effects);

		color_bar_deep = Color.parseColor("#616886");
		color_bar_light = Color.parseColor("#9091A7");
		color_bar_wake = Color.parseColor("#A4691D");
		color_bar_press = Color.parseColor("#B0B1B7");
		PBar = new Paint();
		PBar.setAntiAlias(true);

		color_inaxle = Color.GRAY;
		pInaxle = new Paint();
		pInaxle.setColor(color_inaxle);

		color_lable = Color.GRAY;
		pLable = new Paint();
		pLable.setColor(color_lable);
		pLable.setTextSize(lable_txt_size);
		pLable.setAntiAlias(true);

		padTop = lable_txt_size * 3 / 2;
		padBot = padTop;
		padRight = lable_txt_size / 2;
		padLeft = padRight;

		sleepTips = new String[3];
		sleepTips[0] = "��˯";// context.getResources().getString(R.string.sleep_deep);
		sleepTips[1] = "ǳ˯";// context.getResources().getString(R.string.sleep_light);
		sleepTips[2] = "����";// context.getResources().getString(R.string.sleep_wake);

		bitmap_moon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.index_ic_time1);
		bitmap_sun = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.index_ic_time2);
		bitmap_moon_rec = new RectF();
		bitmap_sun_rec = new RectF();

		/* ���� */
		YPoints = new PointF[data.size()][2];
		int minutesTotle = TimeUtil.compareMinutes(data.get(data.size() - 1).getTimeEnd(), data.get(0).getTimeStart());
		for (int i = 0; i < data.size(); i++) {
			int minutes_start = TimeUtil.compareMinutes(data.get(i).getTimeStart(), data.get(0).getTimeStart());
			int minutes_end = TimeUtil.compareMinutes(data.get(i).getTimeEnd(), data.get(0).getTimeStart());
			float x1 = (float) (width - padLeft - padRight) / minutesTotle * minutes_start + padLeft;
			float x2 = (float) (width - padLeft - padRight) / minutesTotle * minutes_end + padLeft;
			float y;
			if (data.get(i).getSleepQualityAvg() == 1) {
				y = (float) padTop;
			} else {
				y = (float) (height - padBot) / 4;
			}
			YPoints[i] = new PointF[] { new PointF(x1, y), new PointF(x2, y) };
		}

		float w = lable_txt_size;
		bitmap_moon_rec.left = padLeft;
		bitmap_moon_rec.top = height - padBot / 2f - w / 2f;
		bitmap_moon_rec.right = padLeft + w;
		bitmap_moon_rec.bottom = height - padBot / 2f + w / 2f;
		float txtWidth_end = pLable.measureText("00:00");
		bitmap_sun_rec.left = width - padRight - txtWidth_end - w;
		bitmap_sun_rec.top = height - padBot / 2f - w / 2f;
		bitmap_sun_rec.right = width - padRight - txtWidth_end;
		bitmap_sun_rec.bottom = height - padBot / 2f + w / 2f;
	}

	private int measureHeight(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		return result;
	}

	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		return result;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (YPoints == null) {
			return false;
		}
		synchronized (this) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xTuch = event.getX();
				if (xTuch > (width - padRight)) {
					xTuch = width - padRight;
				} else if (xTuch <= padLeft) {
					Log.e("wqs", padLeft + "=====" + width);
					xTuch = padLeft;
				}

				for (int i = 0; i < YPoints.length; i++) {
					if (xTuch > YPoints[i][0].x && xTuch < YPoints[i][1].x) {
						showIndicateIndex = i;
						break;
					}
				}
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				showIndicateIndex = -1;
				xTuch = event.getX();
				if (xTuch > (width - padRight)) {
					xTuch = width - padRight;
				} else if (xTuch <= padLeft) {
					Log.e("wqs", padLeft + "=====" + width);
					xTuch = padLeft;
				}

				for (int i = 0; i < YPoints.length; i++) {
					if (xTuch > YPoints[i][0].x && xTuch < YPoints[i][1].x) {
						showIndicateIndex = i;
						break;
					}
				}
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				showIndicateIndex = -1;
				invalidate();
				break;
			}
		}
		return true;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
