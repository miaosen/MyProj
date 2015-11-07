package com.ui.date;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YearAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Integer> listItem;
	private int clickPosition;
	int screenWidth;

	public YearAdapter(Context context,ArrayList<Integer> listItem,int screenWidth) {
		this.context=context;
		this.listItem=listItem;
		this.screenWidth=screenWidth;
	}

	@Override
	public int getCount() {
		return listItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		TextView item_tv=new TextView(context);
		item_tv.setText(listItem.get(position)+"");
		int w=screenWidth*3/5/5;
		item_tv.setWidth(w);
		item_tv.setHeight(w*2/3);
		item_tv.setTextSize(14);
		item_tv.setGravity(Gravity.CENTER);
		item_tv.setBackgroundColor(Color.parseColor("#eeeeee"));
		item_tv.setTextColor(Color.parseColor("#858585"));
		int num=Integer.parseInt(item_tv.getText().toString());
		Calendar c = Calendar.getInstance();
		if(num== (c.get(Calendar.YEAR))){
			item_tv.setBackgroundColor(Color.parseColor("#BBBBBB"));
			item_tv.setTextColor(Color.parseColor("#ffffff"));
		}
		if(listItem.get(position)==clickPosition){
			item_tv.setBackgroundColor(Color.parseColor("#61A8FA"));
			item_tv.setTextColor(Color.parseColor("#ffffff"));
		}
		view=item_tv;
		return view;
	}

	public int getClickPosition() {
		return clickPosition;
	}

	public void setClickPosition(int clickPosition) {
		this.clickPosition = clickPosition;
	}


}
