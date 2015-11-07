package com.main;

import com.example.myproj.R;
import com.ui.alertview.AlertView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
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
		
//		FrameLayout content=(FrameLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
//		content.getChildAt(0).setBackgroundColor(Color.parseColor("#b0000000"));
//		content.addView(alertView,1);
		
//		new Data().getInternetData(new getData());
//		AsynActionInvorker ai=new AsynActionInvorker("mpMpBaseDataAciton");
//		ai.setAfterGetTextListener(new getData());
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("parent_id", "0");
//		ai.invorkerForString("getCheckType", map);
		
		
//		new Data().getInternetData(new AfterGetTextListener() {
//			@Override
//			public void onResult(String text) {
//				Logger.info("text==========="+text);
//				List<RowObject> map=JSONSerializer.getRows(text);
//				Logger.info("ORDERID==========="+map.get(0).get("ORDERID")+"");
//			}
//		});
		
		
	}
	
	
	private void setAlertView() {
		View view=LayoutInflater.from(context).inflate(R.layout.aa, null);
		alertView =new AlertView (this,view);
		alertView.setShadow(true, true, true, true);
		
	}
	
	class mClik implements OnClickListener {
		@Override
		public void onClick(View v) {
			alertView.showAsDown(btn);
			alertView.setButtomShadowHeight(50);
//			alertView.setButtomShadowHeight(20);
//			alertView.showAsLeft(btn);
//			alertView.setRightShadowWidth(20);
//			alertView.showAsUp(btn, DPUtils.dip2px(context, 20));
//			alertView.show();
		}
		
	}
	

//	
//	class getData implements AfterGetTextListener{
//		@Override
//		public void onResult(String text) {
//			Logger.info("!!!!@@@@@@@@@"+text);
//		}
//	}
//	
	


}
