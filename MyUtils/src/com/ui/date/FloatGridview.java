package com.ui.date;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-6-9
 * @Descrition 时间滑动gridView
 */
public class FloatGridview extends GridView {
	// 手势监听
	private GestureDetector gestureDetector;

	public FloatGridview(Context context) {
		super(context);
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));//去除选择器样式
	}

	public FloatGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));//去除选择器样式
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		return gestureDetector.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	}

	public GestureDetector getGestureDetector() {
		return gestureDetector;
	}

	public void setGestureDetector(GestureDetector gestureDetector) {
		this.gestureDetector = gestureDetector;
	}
	
	

}
