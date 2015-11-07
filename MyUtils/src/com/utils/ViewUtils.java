package com.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

public class ViewUtils {
	
	
	public static ViewGroup getRootViewGroup(Context context){
		ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity) context)
				.getWindow().getDecorView().findViewById(android.R.id.content))
				.getChildAt(0);
		return viewGroup;
	}

}
