package com.ui.date;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.R;

public class MonthAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Integer> listItem;
	private int clickPosition=100;//点击时的位置
	private int screenWidth;

	public MonthAdapter(Context context,ArrayList<Integer> listItem,int screenWidth) {
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
	public View getView(int position, View view, ViewGroup viewGroup) {
		LinearLayout ln=new LinearLayout(context);
		ln.setPadding(10, 10, 10, 10);
		ln.setGravity(Gravity.CENTER);
		TextView item_tv=new TextView(context);
		item_tv.setText(listItem.get(position)+"");
		int w=screenWidth*3/5/6;
		item_tv.setWidth(w);
		item_tv.setHeight(w);
		item_tv.setTextSize(14);
		item_tv.setGravity(Gravity.CENTER);
		item_tv.setBackgroundColor(Color.parseColor("#eeeeee"));
		item_tv.setTextColor(Color.parseColor("#858585"));
		int num=Integer.parseInt(item_tv.getText().toString());
		Calendar c = Calendar.getInstance();
		if(num== (c.get(Calendar.MONTH)+1)){//当月为灰色显示
			item_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_share_check_df));
			item_tv.setTextColor(Color.parseColor("#ffffff"));
		}
		if(position+1==clickPosition){
			item_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_share_check));
			item_tv.setTextColor(Color.parseColor("#ffffff"));
		}
		ln.addView(item_tv);
		view=ln;
		return view;
	}


	public void setClickPosition(int clickPosition) {
		this.clickPosition = clickPosition;
	}
	

}
