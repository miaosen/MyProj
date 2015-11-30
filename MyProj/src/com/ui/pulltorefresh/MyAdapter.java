package com.ui.pulltorefresh;

import java.util.List;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.comment.Logger;
import com.json.RowObject;

public class MyAdapter extends BaseFillRecycleViewAdapter{
	

	public MyAdapter(List<RowObject> rows, int layout) {
		super(rows, layout);
		
	}
	
	public MyAdapter(String json, int layout) {
		super(json, layout);
		
	}

	@Override
	public void setItem(ViewHolder viewHolder, RowObject rows, int i) {
		// TODO Auto-generated method stub
		Logger.info("i=============="+i);
		if(i%2==0){
			viewHolder.itemView.setBackgroundColor(Color.parseColor("#bbbbbb"));
		}else{
			viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		if(rows.get("CHECK_STATUS")!=null&&rows.get("CHECK_STATUS").equals("限期整改")){
			viewHolder.itemView.setBackgroundColor(Color.parseColor("#bb0000"));
		}
			
		viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	}

//	@Override
//	public boolean canFillType(View view) {
//		super.canFillType(view);
//		return (view instanceof TextView);
//	}
//	
	

}
