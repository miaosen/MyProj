package com.main;

import com.example.myproj.R;
import com.ui.header.Header;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class HeaderDemo extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_header);
		Header hd=new Header(this);
	}
	
//	class myHeader extends Header{
//		public myHeader(Context context) {
//			super(context);
//			
//		}
//		
//	}

}
