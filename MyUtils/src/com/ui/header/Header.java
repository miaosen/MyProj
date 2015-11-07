package com.ui.header;

import com.myutils.R;
import com.utils.ViewUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-11-2
 * @Descrition 通用头部初始化
 */
public class Header {

	public Context context;

	private View header;
	private Button headerLeft;
	private Button headerRight;
	private TextView title;

	public Header(Context context) {
		this.context = context;
		initHeader();
	}
	
	public Header(Context context,int layout) {
		this.context = context;
		header = LayoutInflater.from(context).inflate(layout, null);
		initHeader();
	}

	/**
	 * 初始化
	 */
	private void initHeader() {
		header = LayoutInflater.from(context).inflate(R.layout.header, null);
		headerLeft = (Button) header.findViewById(R.id.header_left);
		headerRight = (Button) header.findViewById(R.id.header_right);
		title=(TextView) header.findViewById(R.id.title);
		ViewGroup viewGroup = ViewUtils.getRootViewGroup(context);
		viewGroup.addView(header, 0);
		
	}
	
	public void setLeft(String name){
		headerLeft.setText(name);
	}
	
	public void setRight(String name){
		headerRight.setText(name);
	}
	
	public void setTitle(String name){
		title.setText(name);
	}
	
	
	

	public View getHeader() {
		return header;
	}

	public void setHeader(View header) {
		this.header = header;
	}

	public Button getHeaderLeft() {
		return headerLeft;
	}

	public void setHeaderLeft(Button headerLeft) {
		this.headerLeft = headerLeft;
	}

	public Button getHeaderRight() {
		return headerRight;
	}

	public void setHeaderRight(Button headerRight) {
		this.headerRight = headerRight;
	}

}
