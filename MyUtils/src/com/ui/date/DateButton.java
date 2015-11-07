package com.ui.date;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.myutils.R;



public class DateButton extends Button{
	
	private Context context;
	private DateView dateView;
	
	//在xml定义时必须引用AttributeSet参数
	public DateButton(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.context=context;
		setCommonStyle();
	}

	public DateButton(Context context) {
		super(context);
		this.context=context;
		setCommonStyle();
	}
	
	/**
	 * 公共样式
	 */
	private void setCommonStyle() {
		this.setText("选择日期");
		this.setTextColor(Color.parseColor("#636363"));
		Drawable drawable= getResources().getDrawable(R.drawable.date_btn_date_left);// 获取图片资源icon_date
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置图片的x轴，y轴，宽度，高度
		this.setCompoundDrawables(drawable, null, null, null);//设置内部左边图片，同xml中的android:drawableLeft
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.date_bg_head));
		this.setPadding(10, 5, 10, 5);
		this.setOnClickListener(new mClick());
	}

	class mClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			dateView=new DateView(context,DateButton.this);
			dateView.show();
		}
	}
	
	

}
