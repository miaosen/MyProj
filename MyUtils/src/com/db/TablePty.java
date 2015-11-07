package com.db;

import java.io.Serializable;
import java.util.List;

import android.content.ContentValues;


public class TablePty implements Serializable {
	
	private static final long serialVersionUID = -2011640088351651370L;
	
	public String name;
	
	public List<ContentValues> listVc;
	
	public ContentValues value;
	
	public TablePty(String name,ContentValues value){
		this.name=name;
		this.value=value;
	}
	
	public TablePty(String name,List<ContentValues> listVc){
		this.name=name;
		this.listVc=listVc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContentValues> getListVc() {
		return listVc;
	}

	public void setListVc(List<ContentValues> listVc) {
		this.listVc = listVc;
	}

	public ContentValues getValue() {
		return value;
	}

	public void setValue(ContentValues value) {
		this.value = value;
	}
	
	

}
