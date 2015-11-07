package com.ui.tabmenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class TabBean {
	
	private Intent intent;
	/**
	 * 菜单名
	 */
	private String MenuName;
	/**
	 * 没有选中时图标
	 */
	private Drawable unCheckImage;
	/**
	 * 选中时图标
	 */
	private Drawable checkImage;
	/**
	 * 选中时背景图片
	 */
	private Drawable checkBackground;
	/**
	 * 没有选中时背景图片
	 */
	private Drawable unCheckBackground;
	
	
	
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public String getMenuName() {
		return MenuName;
	}
	public void setMenuName(String menuName) {
		MenuName = menuName;
	}
	public Drawable getUnCheckImage() {
		return unCheckImage;
	}
	public void setUnCheckImage(Drawable unCheckImage) {
		this.unCheckImage = unCheckImage;
	}
	public Drawable getCheckImage() {
		return checkImage;
	}
	public void setCheckImage(Drawable checkImage) {
		this.checkImage = checkImage;
	}
	public Drawable getCheckBackground() {
		return checkBackground;
	}
	public void setCheckBackground(Drawable checkBackground) {
		this.checkBackground = checkBackground;
	}
	public Drawable getUnCheckBackground() {
		return unCheckBackground;
	}
	public void setUnCheckBackground(Drawable unCheckBackground) {
		this.unCheckBackground = unCheckBackground;
	}
	
	
	

}
