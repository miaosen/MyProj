package com.ui.tabmenu;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-6-16
 * @Descrition Tab样式表
 */
public class TabStyleBean {
	
	/*
	 * 是否可以滑动，默认不可滑动
	 */
	private boolean canSlide=false;
	/**
	 * 默认字体颜色，灰色
	 */
	private String textColorDf="#8D8D8D";
	/**
	 * 字体选中时颜色，蓝色
	 */
	private String textColorPr="#0E81E1";
	/**
	 * 菜单上的线条的颜色，灰色
	 */
	private String lineColor="#bbbbbb";
	/**
	 * 默认tab字体大小
	 */
	private int textSize=10;
	/**
	 * 单个菜单占屏幕的几分之一，此参数大于菜单个数时菜单可以滑动，等于0为不可滑动，当菜单过多时看以启用改参数
	 */
	private int menuSlide=0;
	/**
	 * 没有设置点击图片时，默认把图片的颜色转化为蓝色
	 */
	private String dfCheckImageColor="#417FDB";
	

	public boolean isCanSlide() {
		return canSlide;
	}

	public void setCanSlide(boolean canSlide) {
		this.canSlide = canSlide;
	}

	public String getTextColorDf() {
		return textColorDf;
	}

	public void setTextColorDf(String textColorDf) {
		this.textColorDf = textColorDf;
	}

	public String getTextColorPr() {
		return textColorPr;
	}

	public void setTextColorPr(String textColorPr) {
		this.textColorPr = textColorPr;
	}

	public String getLineColor() {
		return lineColor;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public int getMenuSlide() {
		return menuSlide;
	}

	public void setMenuSlide(int menuSlide) {
		this.menuSlide = menuSlide;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public String getDfCheckImageColor() {
		return dfCheckImageColor;
	}

	public void setDfCheckImageColor(String dfCheckImageColor) {
		this.dfCheckImageColor = dfCheckImageColor;
	}
	

}
