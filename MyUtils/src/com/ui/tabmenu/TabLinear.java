package com.ui.tabmenu;

import java.util.List;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.myutils.R;

public class TabLinear extends LinearLayout {

	private String TGA="TabLinear";
	private Context context;
	private List<TabBean> list;
	private TabHost tabHost;
	private GestureDetector gestureDetector;
	private View.OnTouchListener listener;
	private FrameLayout framly;
	private View line;
	private boolean canSlide=false;
	private String textColorDf;
	private String textColorPr;
	private String lineColor;
	private String dfCheckImageColor;
	private int textSize;
	private int menuSlide;
	
	

	public TabLinear(Context context, AttributeSet atr) {
		super(context, atr);
		this.context = context;
		setTab(list);
	}

	/**
	 * @param context 上下文
	 * @param list Intent数组
	 * @param style tab样式  TabStyleBean
	 */
	public TabLinear(Context context, List<TabBean> list, TabStyleBean style) {
		super(context);
		this.context = context;
		this.list = list;
		setStyle(style);
		setTab(list);
	}

	/*
	 * 全局样式设置
	 */
	private void setStyle(TabStyleBean style) {
		if(style!=null){
			canSlide=style.isCanSlide();
			textColorDf=style.getTextColorDf();
			textColorPr=style.getTextColorPr();
			lineColor=style.getLineColor();
			textSize=style.getTextSize();
			menuSlide=style.getMenuSlide();
			dfCheckImageColor=style.getDfCheckImageColor();
		}else{
			Log.v(TGA, "默认样式不能为空！");
		}
	}

	/**
	 * 添加TabHost
	 * 
	 * @param list
	 *            activity的菜单配置
	 */
	private void setTab(List<TabBean> list) {
		tabHost = (TabHost) LayoutInflater.from(context).inflate(
				R.layout.tab_linear, null);
		// 获取加载activity部分，设置滑动动画,滑动监听
		framly = (FrameLayout) tabHost.findViewById(android.R.id.tabcontent);
		line=tabHost.findViewById(R.id.line);
		line.setBackgroundColor(Color.parseColor(lineColor));
		tabHost.setup(((TabActivity) context).getLocalActivityManager());
		//获取Intent
		for (int i = 0; i < list.size(); i++) {
			tabHost.addTab(tabHost.newTabSpec("1").setIndicator(getTabHost(i))
					.setContent(list.get(i).getIntent()));
		}
		View view = tabHost.getTabWidget().getChildAt(0);
		view.setPadding(0, 5, 0, 3);
		// 图标
		ImageView imageView = (ImageView) view.findViewById(0);
		// 如果tab没有设置点击后的图片，则默认把图片颜色转化为蓝色
		if (null == list.get(0).getCheckImage()) {
			imageView.setImageBitmap(createRGBImage(list.get(0)
					.getUnCheckImage(), dfCheckImageColor));
		} else {
			imageView.setImageDrawable(list.get(0).getCheckImage());
		}
		// 文字
		TextView textView = (TextView) view.findViewById(0x0010 + 0);
		textView.setTextColor(Color.parseColor(textColorPr));
		// 背景图片
		tabHost.setBackground(list.get(0).getCheckBackground());
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				updateTab(tabHost);
			}
		});
		// 设置手势监听
		if(canSlide){
			setGesture();
		}
		addView(tabHost);
	}

	private void setGesture() {
		gestureDetector = new GestureDetector(new MyListener());
		listener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				if (gestureDetector.onTouchEvent(ev)) {
					ev.setAction(MotionEvent.ACTION_CANCEL);
				}
				return true;
			}
		};
		// 只监听tabcontent
		framly.setOnTouchListener(listener);
	}

	private View getTabHost(int i) {
		LinearLayout view = new LinearLayout(context);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setBackground(list.get(i).getUnCheckBackground());
		if(menuSlide==0){
			view.setLayoutParams(new LinearLayout.LayoutParams(
					((int) initScreen() / list.size()), LayoutParams.MATCH_PARENT));
		}else{
			view.setLayoutParams(new LinearLayout.LayoutParams(
					((int) initScreen() / menuSlide), LayoutParams.MATCH_PARENT));
		}
		
		ImageView iv = new ImageView(context);
		iv.setId(i);
		iv.setImageDrawable(list.get(i).getUnCheckImage());
		iv.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 3));
		TextView tv = new TextView(context);
		tv.setText(list.get(i).getMenuName());
		tv.setTextSize(textSize);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextColor(Color.parseColor(textColorDf));
		tv.setId(0x0010 + i);
		iv.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		view.addView(iv);
		view.addView(tv);
		return view;
	}

	/**
	 * 获取屏幕参数
	 */
	private int initScreen() {
		Activity activity = (Activity) context;
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay(); // 为获取屏幕宽、高
		// 窗口宽度
		int screenWidth = display.getWidth();
		return screenWidth;
	}

	/**
	 * Tab点击事件监听
	 * 
	 * @param tabHost
	 *            菜单列表
	 */
	private void updateTab(TabHost tabHost) {
		int curr = tabHost.getCurrentTab();
		View view = tabHost.getTabWidget().getChildAt(curr);
		view.setBackground(list.get(curr).getCheckBackground());
		ImageView imageView = (ImageView) view.findViewById(curr);
		// 如果tab没有设置点击后的图片，则默认把图片颜色转化为蓝色
		if (null == list.get(curr).getCheckImage()) {
			imageView.setImageBitmap(createRGBImage(list.get(curr)
					.getUnCheckImage(), dfCheckImageColor));
		} else {
			imageView.setImageDrawable(list.get(curr).getCheckImage());
		}
		TextView textView = (TextView) view.findViewById(0x0010 + curr);
		textView.setTextColor(Color.parseColor(textColorPr));
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			if (i != curr) {
				View curr_view = tabHost.getTabWidget().getChildAt(i);
				curr_view.setBackground(list.get(i).getUnCheckBackground());
				ImageView curr_imageView = (ImageView) curr_view
						.findViewById(i);
				curr_imageView.setImageDrawable(list.get(i).getUnCheckImage());
				TextView curr_textView = (TextView) curr_view
						.findViewById(0x0010 + i);
				curr_textView.setTextColor(Color.parseColor(textColorDf));
			}
		}
	}

	/**
	 * 将图片透明之外的颜色改变成其他的颜色
	 * 
	 * @param drawable
	 *            要修改的图标
	 * @param dfCheckImageColor
	 *            要变成的颜色
	 */
	public static final Bitmap createRGBImage(Drawable drawable,
			String dfCheckImageColor) {
		int color = Color.parseColor(dfCheckImageColor);
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		int bitmap_w = bitmap.getWidth();
		int bitmap_h = bitmap.getHeight();
		int[] arrayColor = new int[bitmap_w * bitmap_h];
		int count = 0;
		for (int i = 0; i < bitmap_h; i++) {
			for (int j = 0; j < bitmap_w; j++) {
				int color1 = bitmap.getPixel(j, i);
				// 获取原图透明度 A
				int alpha = color1 & 0xFF000000;
				// 获取传入颜色的RGB
				int red = (color & 0x00FF0000) >> 16;
				int green = (color & 0x0000FF00) >> 8;
				int blue = (color & 0x000000FF);
				// 合成颜色
				int newColor = alpha | (red << 16) | (green << 8) | blue;
//				System.out.println("A1: " + alpha + "  R1: " + red + "  G1: "
//						+ green + "  B1: " + blue);
				//像素点加入颜色
				arrayColor[count] = newColor;
				count++;
			}
		}
		bitmap = Bitmap.createBitmap(arrayColor, bitmap_w, bitmap_h,
				Config.ARGB_4444);
		return bitmap;
	}


	/**
	 * 手势监听
	 */
	class MyListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (e1.getX() > e2.getX()) {// 向左滑动
					if (tabHost.getCurrentTab() == list.size()) {
						// 滑动到第一个时不能滑动
					} else {
						Animation toLeft = AnimationUtils.loadAnimation(
								context, R.drawable.anm_to_left);
						framly.setAnimation(toLeft);
						tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
						initScreen();
					}
				} else {// 向有滑动
					if (tabHost.getCurrentTab() == 0) {
						// 滑动到最后一个时不能滑动
					} else {
						Animation toRight = AnimationUtils.loadAnimation(
								context, R.drawable.anm_to_right);
						framly.setAnimation(toRight);
						tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
					}
				}
			} catch (Exception e) {
			}
			return false;
		}
	}

}
