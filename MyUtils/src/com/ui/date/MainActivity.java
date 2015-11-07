package com.ui.date;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.myutils.R;

public class MainActivity extends Activity {

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=MainActivity.this;
		setContentView(R.layout.activity_main);
	}


}
