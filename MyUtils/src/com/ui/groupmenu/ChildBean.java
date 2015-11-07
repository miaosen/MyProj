package com.ui.groupmenu;

import com.myutils.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class ChildBean {
	
	private int leftIcon=R.drawable.icon_msg;
	private String title;
	private int rightIcon=R.drawable.icon_jiantou;
	
	private String value;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getLeftIcon() {
		return leftIcon;
	}
	public void setLeftIcon(int leftIcon) {
		this.leftIcon = leftIcon;
	}
	public int getRightIcon() {
		return rightIcon;
	}
	public void setRightIcon(int rightIcon) {
		this.rightIcon = rightIcon;
	}
	
	
}
