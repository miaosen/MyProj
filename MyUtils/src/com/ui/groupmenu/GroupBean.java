package com.ui.groupmenu;

import java.util.ArrayList;
import java.util.List;

public class GroupBean {
	
	private String title;
	private String flooter;
	List<ChildBean> childList=new ArrayList<ChildBean>();
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFlooter() {
		return flooter;
	}
	public void setFlooter(String flooter) {
		this.flooter = flooter;
	}
	public List<ChildBean> getChildList() {
		return childList;
	}
	public void setChildList(List<ChildBean> childList) {
		this.childList = childList;
	}
	
	

}
