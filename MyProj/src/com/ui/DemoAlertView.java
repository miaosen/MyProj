package com.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.myproj.R;
import com.ui.alertview.AlertView;
import com.utils.DPUtils;

public class DemoAlertView extends Activity {
	private Context context;
	private AlertView alertView;
	private TextView tv;
	private Button btn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context=this;
		tv=(TextView) findViewById(R.id.tv);
		btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new mClik());
		setAlertView();
	}
	
	
	private void setAlertView() {
		//传入布局
		View view=LayoutInflater.from(context).inflate(R.layout.aa, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,btn.getWidth()));
		alertView =new AlertView (this,view);
		//设置阴影
		alertView.setShadow(true, true, true, true);
		
	}
	
	class mClik implements OnClickListener {
		@Override
		public void onClick(View v) {
//			alertView.show();
//			alertView.showAsDown(btn);
//			alertView.setButtomShadowHeight(50);
//			alertView.showAsLeftOf(btn);
			alertView.showAsUp(btn, DPUtils.dip2px(context, 20));
		}
		
	}
	
}
