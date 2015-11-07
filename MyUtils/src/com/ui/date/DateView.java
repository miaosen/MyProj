package com.ui.date;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.myutils.R;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-5-11
 * @Descrition gridview自定义日历弹窗
 */
public class DateView extends Dialog implements OnGestureListener {

	String TAG = "DateView";
	private Context context;
	private FloatGridview gridview;
	private Button btnYear;
	private Button btnMonth;
	private ImageButton btnSure;
	private Button btnRetrun;
	private Button btnClock;
	private Button btnCheckTime;
	private EditText edMonth;
	private EditText edYear;
	private EditText edDay;
	private TextView tvHour;
	private TextView tvMinute;
	private TextView tvSeconds;
	private TimePicker tp;
	private LinearLayout lnClock;
	private LinearLayout lnWeek;
	private LinearLayout lnTime;
	private int year;// 年
	private int month;// 月
	private int day;// 日
	private int hour;// 时
	private int minute;// 分
	private int seconds;// 秒
	private DayApdapter dayApdapter;
	private View dialogLayout;
	private Button dateButton;
	private boolean isOpenClock = true;// 是否精确到时分秒
	private String selectTime = null;
	private YearAdapter yearAdapter;
	private MonthAdapter monthAdapter;
	private GestureDetector gd;
	private Animation toLeft;
	private Animation toRight;
	private boolean isCheckYear = false;// 是否打开年份选择界面
	private boolean isCheckDay = true;// 是否打开日选择界面
	private boolean isCheckMonth = false;// 是否打开月选择界面
	private int yearCount = 25;// 年适配器显示的年的个数
	private int yearS;// 年适配器滑动时的缓存
	private int screenWidth;
	private int screenHeight;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(dialogLayout);
	}

	public DateView(Context context, DateButton dateButton) {
		super(context, R.style.custom_dialog);
		this.context = context;
		this.dateButton = dateButton;
		initScreen();
		initData();
		initView();
	}

	/**
	 * 获取屏幕参数
	 */
	private void initScreen() {
		this.setCanceledOnTouchOutside(false);
		Activity activity = (Activity) context;
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay(); // 为获取屏幕宽、高
		// 窗口宽度
		screenWidth = display.getWidth();
		screenHeight=display.getHeight();
		//设置dialog大小
		if(screenWidth>screenHeight){
			screenWidth=screenHeight;
		}
	}

	/**
	 * 初始化日期
	 */
	private void initData() {
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);// 获取年份
		month = c.get(Calendar.MONTH) + 1;// 获取月份
		day = c.get(Calendar.DAY_OF_MONTH);// 获取日
		hour = c.get(Calendar.HOUR_OF_DAY);// 获取时
		minute = c.get(Calendar.MINUTE);// 获取分
		seconds = c.get(Calendar.SECOND);// 获取秒
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		dialogLayout = LayoutInflater.from(context).inflate(R.layout.date,
				null);
		tp=(TimePicker) dialogLayout.findViewById(R.id.tp);
		tp.setOnTimeChangedListener(new tpListener());
		btnYear = (Button) dialogLayout.findViewById(R.id.btn_year);
		btnYear.setOnClickListener(new mClick(dialogLayout));
		btnMonth = (Button) dialogLayout.findViewById(R.id.btn_month);
		btnMonth.setOnClickListener(new mClick(dialogLayout));
		btnRetrun = (Button) dialogLayout.findViewById(R.id.btn_retrun);
		btnClock = (Button) dialogLayout.findViewById(R.id.clock_btn);
		btnCheckTime= (Button) dialogLayout.findViewById(R.id.btn_check_time);
		btnSure = (ImageButton) dialogLayout.findViewById(R.id.btn_sure);
		lnClock = (LinearLayout) dialogLayout.findViewById(R.id.ln_clock);
		lnWeek = (LinearLayout) dialogLayout.findViewById(R.id.ln_week);
		lnTime = (LinearLayout) dialogLayout.findViewById(R.id.ln_time);
		lnTime.setWeightSum(1);
		edYear = (EditText) dialogLayout.findViewById(R.id.ed_year);
		edMonth = (EditText) dialogLayout.findViewById(R.id.ed_month);
		edDay = (EditText) dialogLayout.findViewById(R.id.ed_day);
		setEdDate(day, month, year);
		tvHour = (TextView) dialogLayout.findViewById(R.id.tv_hour);
		tvMinute = (TextView) dialogLayout.findViewById(R.id.tv_minute);
		tvSeconds = (TextView) dialogLayout.findViewById(R.id.tv_seconds);
		btnCheckTime.setOnClickListener(new mClick(dialogLayout));
		btnSure.setOnClickListener(new mClick(dialogLayout));
		btnRetrun.setOnClickListener(new mClick(dialogLayout));
		btnClock.setOnClickListener(new mClick(dialogLayout));
		setButtonStyle();
		gridview = (FloatGridview) dialogLayout.findViewById(R.id.gv);
		dayApdapter = new DayApdapter(context, year, month,screenWidth);
		gridview.setAdapter(dayApdapter);
		gridview.setOnItemClickListener(new ItemClick(context));
		gd = new GestureDetector(this);
		// gridView要实现onGestureDetector（手势监听），必须重写其内部的onTouchEvent，dispatchTouchEvent方法
		gridview.setGestureDetector(gd);
		btnRetrun.setBackgroundDrawable(context.getResources()
				.getDrawable(R.drawable.date_bg_medle_pr));
		btnRetrun.setTextColor(Color.parseColor("#ffffff"));
		dayApdapter.setClickPosition(day);
		dayApdapter.notifyDataSetChanged();
	}

	/**
	 * 设置弹窗大小
	 */
	@Override
	public void show() {
		super.show();
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		int w=(int)(screenWidth*5/6);
		lp.width=w;
		lp.gravity = Gravity.TOP;
		lp.y = 100;
		// lp.width=500;
		window.setAttributes(lp);
	}

	/**
	 * 点击某一天
	 */
	class ItemClick implements OnItemClickListener {
		Context context;

		public ItemClick(Context context) {
			this.context = context;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {

			// 日适配器操作
			if (isCheckDay) {
				int num=Integer.parseInt(((TextView)((LinearLayout) view).findViewById(949494)).getText().toString());
				day=num;
				if (position < 7 && num > 7) {
					subtract_month();
					dayApdapter.setClicklastMontPosition(position);
				} else if (position > 14 && num < 7) {
					add_month();
					dayApdapter.setnextMonthPosition(position);
				} else {
					edDay.setText((num > 9 ? num : "0" + num) + "");
					edMonth.setText((month > 9 ? month : "0" + month) + "");
					edYear.setText(year + "");
				}
			}
			// 年适配器操作
			if (isCheckYear) {
				lnWeek.setVisibility(View.VISIBLE);
				int num = Integer.parseInt(((TextView) view).getText()
						.toString());
				year = num;
				edYear.setText(num + "");
				checkDay(year,month);
				dayApdapter = new DayApdapter(context, year, month,screenWidth);
				gridview.setAdapter(dayApdapter);
				
			}
			// 月适配器操作
			if (isCheckMonth) {
				lnWeek.setVisibility(View.VISIBLE);
				int num = position+1;
				month = num;
				checkDay(year,month);
				edMonth.setText((num > 9 ? num : "0" + num) + "");
//				int day=Integer.parseInt(edDay.getText().toString());
				dayApdapter = new DayApdapter(context, year, month,screenWidth);
				gridview.setAdapter(dayApdapter);
			}
			// 点击完item后回到日的适配器
			isCheckYear = false;
			isCheckDay = true;
			isCheckMonth = false;
			gridview.setNumColumns(7);
			dayApdapter.setClickPosition(day);
			dayApdapter.notifyDataSetChanged();
		}
	}
	
	
	/**
	 * 操作年和月时，天有时候会变化，当上一次选择的天数大于这个月的天数时，则选择的天数变成这个月的最大天数
	 */
	public void checkDay(int year,int month){
		int dayOfMonth=getSumData(year,month);
		int ckDay=Integer.parseInt(edDay.getText().toString());
		if(ckDay>dayOfMonth){
			ckDay=dayOfMonth;
			edDay.setText(ckDay+"");
			day=ckDay;
		}
	}
	
	/**
	 * @param year
	 * @param month
	 * @return 获取这个月的总天数
	 */
	private int getSumData(int year,int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,year);
		c.set(Calendar.MONTH, month-1);//Java月份从0开始算
		int sumDay = c.getActualMaximum(Calendar.DATE);//当前月的总天数
		return sumDay;
	}

	class mClick implements android.view.View.OnClickListener {
		View view;
		public mClick(View view) {
			this.view = view;
		}
		@Override
		public void onClick(View v) {
			setButtonStyle();
			// 返回今天
			if(v==btnRetrun){//返回今天
				isCheckYear = false;
				isCheckDay = true;
				isCheckMonth = false;
				lnWeek.setVisibility(View.VISIBLE);
				btnRetrun.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.date_bg_medle_pr));
				btnRetrun.setTextColor(Color.parseColor("#ffffff"));
				gridview.setNumColumns(7);
				initData();
				setApdapter();
				int day=Integer.parseInt(edDay.getText().toString());
				setEdDate(day, month, year);
				dayApdapter.setClickPosition(day);
				dayApdapter.notifyDataSetChanged();
			}
			if(v==btnClock){//时间
				lnWeek.setVisibility(View.GONE);
				gridview.setVisibility(View.GONE);
				lnTime.setVisibility(View.VISIBLE);
				btnCheckTime.setText("不选时间");
				btnCheckTime.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_time_ck));
				btnClock.setTextColor(Color.parseColor("#ffffff"));
				btnClock.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_bg_right_pr));
				isOpenClock=true;
				lnClock.setVisibility(View.VISIBLE);
				tvHour.setText((hour < 10 ? "0" + hour : hour) + ":");
				tvMinute.setText(minute < 10 ? "0" + minute : minute + "");
				tvSeconds.setText(":"
						+ (seconds < 10 ? "0" + seconds : seconds));
				return;
			}
			if(v==btnYear){//年份
				isCheckYear = true;
				isCheckDay = false;
				isCheckMonth = false;
				yearS = year;
				gridview.setNumColumns(5);
				btnYear.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.date_bg_left_pr));
				btnYear.setTextColor(Color.parseColor("#ffffff"));
				checkYear(yearS);
				lnWeek.setVisibility(View.GONE);
			}
			if(v==btnMonth){//月份
				lnWeek.setVisibility(View.GONE);
				btnMonth.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.date_bg_medle_pr));
				btnMonth.setTextColor(Color.parseColor("#ffffff"));
				isCheckYear = false;
				isCheckDay = false;
				isCheckMonth = true;
				gridview.setNumColumns(4);
				checkMonth();
			}
			if(v==btnSure){//确定
				setDate();
			}
			if(v==btnCheckTime){//选择时间
				if (isOpenClock) {
					lnClock.setVisibility(View.GONE);
					tvHour.setText((hour < 10 ? "0" + hour : hour) + ":");
					tvMinute.setText(minute < 10 ? "0" + minute : minute + "");
					tvSeconds.setText(":"
							+ (seconds < 10 ? "0" + seconds : seconds));
					isOpenClock = !isOpenClock;
					btnCheckTime.setText("选择时间");
					btnCheckTime.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_time_df));
				} else {
					lnClock.setVisibility(View.VISIBLE);
					isOpenClock = !isOpenClock;
					btnCheckTime.setText("不选时间");
					btnCheckTime.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_time_ck));
				}
				return;
			}
			gridview.setVisibility(View.VISIBLE);
			lnTime.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 从底部显示栏获取时间数据
	 */
	public void setDate(){
		String slTime=null;
		String y=edYear.getText().toString();
		String m=edMonth.getText().toString();
		String d=edDay.getText().toString();
		slTime=y+"-"+m+"-"+d;
		if(isOpenClock){
			String h=tvHour.getText().toString();
			String mi=tvMinute.getText().toString();
			String s=tvSeconds.getText().toString();
			slTime=slTime+" "+h+mi+s;
		}
		
		dateButton.setText(slTime);
		DateView.this.dismiss();
	}
	
	public void setEdDate(int day,int month,int year){
		edDay.setText((day > 9 ? day : "0" + day) + "");
		edMonth.setText((month > 9 ? month : "0" + month) + "");
		edYear.setText(year + "");
	}
	

	/**
	 * 设置按钮样式
	 */
	private void setButtonStyle() {
		btnYear.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.date_bg_left_df));
		btnMonth.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.date_bg_medle_df));
		btnClock.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.date_bg_right_df));
		btnRetrun.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.date_bg_medle_df));
		btnYear.setTextColor(Color.parseColor("#858585"));
		btnMonth.setTextColor(Color.parseColor("#858585"));
		btnClock.setTextColor(Color.parseColor("#858585"));
		btnRetrun.setTextColor(Color.parseColor("#858585"));
	}

	/**
	 * 初始化年份数据
	 */
	private void checkYear(int year) {
		ArrayList<Integer> listItem = new ArrayList<Integer>();
		// 初始化数据
		for (int i = 0; i < yearCount; i++) {
			listItem.add(year + i);
		}
		yearAdapter = new YearAdapter(context, listItem,screenWidth);
		gridview.setAdapter(yearAdapter);
		int ckYear=Integer.parseInt(edYear.getText().toString());
		yearAdapter.setClickPosition(ckYear);
		yearAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化月份数据
	 */
	private void checkMonth() {
		ArrayList<Integer> listItem = new ArrayList<Integer>();
		// 初始化数据
		for (int i = 0; i < 12; i++) {
			listItem.add(1 + i);
		}
		monthAdapter = new MonthAdapter(context, listItem,screenWidth);
		gridview.setAdapter(monthAdapter);
		int month=Integer.parseInt(edMonth.getText().toString());
		monthAdapter.setClickPosition(month);
		monthAdapter.notifyDataSetChanged();
	}

	/**
	 * 验证时钟格式
	 */
	private Boolean verificatClock(String hour, String minute, String seconds) {
		try {
			int h = Integer.parseInt(hour);
			int m = Integer.parseInt(minute);
			int s = Integer.parseInt(seconds);
			if (h > 24 || h < 0 || m > 60 || m < 0 || s > 60 || s < 0) {
				Toast.makeText(context, "数字大小超出范围", Toast.LENGTH_SHORT).show();
				return false;
			} else {
				selectTime = selectTime + " " + (h < 10 ? "0" + h : h) + ":"
						+ (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
				dateButton.setText(selectTime);
				return true;
			}
		} catch (Exception e) {// 捕捉异常判断是不是数字
			Toast.makeText(context, "时钟格式不正确！请检查空格或者是否为阿拉伯数字！",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/**
	 * 验证年的格式
	 */
//	private Boolean verificatYear(String yaer) {
//		try {
//			int y = Integer.parseInt(yaer);
//			if (y < 1000 || y > 6000) {
//				Toast.makeText(context, "年份1000-6000，大小超出范围",
//						Toast.LENGTH_SHORT).show();
//				return false;
//			} else {
//				btnYear.setText(y + "");
//				lnWeek.setVisibility(View.VISIBLE);
//				gridview.setNumColumns(7);
//				dateBaseApdapter = new DayApdapter(context, y, month);
//				gridview.setAdapter(dateBaseApdapter);
//				isCheckYear = false;
//				isCheckDay = true;
//				isCheckMonth = false;
//				return true;
//			}
//		} catch (Exception e) {// 捕捉异常判断是不是数字
//			Toast.makeText(context, "年格式不正确！请检查空格或者是否为阿拉伯数字！",
//					Toast.LENGTH_SHORT).show();
//			return false;
//		}
//	}

	/**
	 * 刷新页面和适配器
	 */
	public void setApdapter() {
		dayApdapter = new DayApdapter(context, year, month,screenWidth);
		dayApdapter.notifyDataSetChanged();
		gridview.setAdapter(dayApdapter);
	}

	/*
	 * 增加一个月
	 */
	public void add_month() {
		if (month == 12) {
			year = year + 1;
			month = 1;
		} else {
			month = month + 1;
		}
		edYear.setText(year + "");
		edMonth.setText((month>9?month:"0"+month).toString());
		edDay.setText(day+"");
		toLeft = AnimationUtils.loadAnimation(context, R.drawable.anm_to_left);
		gridview.setAnimation(toLeft);
		gridview.setNumColumns(7);
		setApdapter();
		checkDay(year,month);
		dayApdapter.setClickPosition(day);
		dayApdapter.notifyDataSetChanged();
	}

	/**
	 * 减少一个月
	 */
	public void subtract_month() {
		if (month == 1) {
			year = year - 1;
			month = 12;
		} else {
			month = month - 1;
		}
		edYear.setText(year + "");
		edMonth.setText((month>9?month:"0"+month).toString());
		edDay.setText(day+"");
		toRight = AnimationUtils.loadAnimation(context, R.drawable.anm_to_right);
		gridview.setAnimation(toRight);
		gridview.setNumColumns(7);
		setApdapter();
		checkDay(year,month);
		dayApdapter.setClickPosition(day);
		dayApdapter.notifyDataSetChanged();
		
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (isCheckMonth) {
		} else {
			if (e1.getX() > e2.getX()) {// 向左滑动
				if (isCheckDay) {
					add_month();
				}
				if (isCheckYear) {
					yearS = yearS + yearCount;
					checkYear(yearS);
					toLeft = AnimationUtils.loadAnimation(context,
							R.drawable.anm_to_left);
					gridview.setAnimation(toLeft);
				}
			} else {// 向有滑动
				if (isCheckDay) {
					subtract_month();
				}
				if (isCheckYear) {
					yearS = yearS - yearCount;
					toRight = AnimationUtils.loadAnimation(context,
							R.drawable.anm_to_right);
					gridview.setAnimation(toRight);
					checkYear(yearS);

				}
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	class tpListener implements OnTimeChangedListener{
		@Override
		public void onTimeChanged(TimePicker tp, int arg1, int arg2) {
			int h=tp.getCurrentHour();
			int m=tp.getCurrentMinute();
			tvHour.setText((h < 10 ? "0" + h : h)+":");
			tvMinute.setText((m < 10 ? "0" + m : m).toString());
		}
	}
	
}
