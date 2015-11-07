package com.ui.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.R;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-5-11
 * @Descrition 日历适配器
 */
public class DayApdapter extends BaseAdapter {

	private Context context;
	private int year;
	private int month;
	private int dateOfMonth;
	private int line;// 显示的天数占多少行空间
	private int dayOfWeek;// 本月1号是星期几
	private ArrayList<Integer> list;
	private int todayPosition;// 今天的位置
	private int clickPosition = 100;// 点击时的位置
	int screenWidth;

	public DayApdapter(Context context, int year, int month,int screenWidth) {
		this.context = context;
		this.year = year;
		this.month = month;
		this.screenWidth=screenWidth;
		this.dateOfMonth = getSumData(year, month);
		// 以此月1号为中心填充数据
		String dateStr = this.year + "-" + this.month + "-" + "1";
		dayOfWeek = getDayOfWeek(dateStr);
		sumLine();
		initDate();
		getNowDate();
	}

	/**
	 * 初始化日历数据
	 */
	private void initDate() {
		list = new ArrayList<Integer>();
		// 添加上一月的天数
		int lastMonth = 0;
		int lastMonthOfDay;
		// 一月的上一个月
		if (month == 1) {
			lastMonthOfDay = getSumData(year - 1, 12);// 上一个月的天数
		} else {
			lastMonth = month - 1;
			lastMonthOfDay = getSumData(year, lastMonth);// 上一个月的天数
		}
		if (dayOfWeek > 0) {
			for (int i = dayOfWeek - 1; i >= 0; i--) {
				list.add(lastMonthOfDay - i);
			}
		}
		// 添加本月数据
		for (int i = 1; i <= dateOfMonth; i++) {
			list.add(i);
		}
		// 添加下一月的天数
		String endDay = year + "-" + month + "-" + dateOfMonth;
		int length = 7 - 1 - getDayOfWeek(endDay);
		if (!(length == 0)) {
			for (int i = 1; i <= length; i++) {
				list.add(i);
			}
		}
	}

	/**
	 * @param year
	 * @param month
	 * @return 获取这个月的总天数
	 */
	private int getSumData(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);// Java月份从0开始算
		int sumDay = c.getActualMaximum(Calendar.DATE);// 当前月的总天数
		return sumDay;
	}

	/**
	 * 获取今天几号
	 */
	public void getNowDate() {
		Calendar c = Calendar.getInstance();
		int todday = c.get(Calendar.DAY_OF_MONTH);
		todayPosition = todday + dayOfWeek - 1;
	}

	/**
	 * 判断是否为本月
	 */
	public Boolean isNowDate() {
		Calendar c = Calendar.getInstance();
		int nowMonth = c.get(Calendar.MONTH) + 1;
		int nowYear = c.get(Calendar.YEAR);
		if (nowYear == this.year && nowMonth == this.month) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间String转化成Date
	 * 
	 * @return date
	 */
	public Date getDate(String dateStr) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 设置显示行数,2月份占4行或者5行，其他的6行或者5行
	 */
	public void sumLine() {
		if (month == 2) {
			// 如果这个月的1号加1号前面占的天数大于28，则占4行空间
			if (dateOfMonth + dayOfWeek > 28) {
				line = 5;
			} else {
				line = 4;
			}
		} else {
			// 如果这个月的1号加1号前面占的天数大于35，则占6行空间
			if (dateOfMonth + dayOfWeek > 35) {
				line = 6;
			} else {
				line = 5;
			}
		}
	}

	/**
	 * 获取当天是星期几
	 * 
	 * @param dt
	 *            时间
	 */
	public int getDayOfWeek(String dateStr) {
		Date date = getDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}

	@Override
	public int getCount() {
		return line * 7;// gridview item个数
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
		LinearLayout ln=new LinearLayout(context);
		ln.setGravity(Gravity.CENTER);
		TextView item_tv = new TextView(context);
		item_tv.setText(list.get(position) + "");
		int w=(screenWidth-70)*3/5/7;
		item_tv.setWidth(w);
		item_tv.setHeight(w);
		item_tv.setTextSize(15);
		item_tv.setGravity(Gravity.CENTER);
		item_tv.setId(949494);
		item_tv.setBackgroundColor(Color.parseColor("#eeeeee"));
		int num = Integer.parseInt(item_tv.getText().toString());
		// 当天背景颜色为浅蓝色
		if (isNowDate()) {
			if (position == todayPosition) {
				item_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_share_check_df));
				item_tv.setTextColor(Color.WHITE);
			}
		}
		//选中是背景
		if (position < 7 && num > 7) {
			item_tv.setTextColor(Color.parseColor("#BEBBBB"));
		} else if (position > 14 && num < 7) {
			item_tv.setTextColor(Color.parseColor("#BEBBBB"));
		} else {
			item_tv.setTextColor(Color.parseColor("#858585"));
			// 点击时背景颜色高亮
			if (list.get(position) == clickPosition) {
				item_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.date_share_check));
				item_tv.setTextColor(Color.WHITE);
			}
		}
		
		ln.addView(item_tv);
		view =ln;
		return view;
	}

	public void setClickPosition(int clickPosition) {
		this.clickPosition = clickPosition;
	}

	/**
	 * gridview刷新到下个月
	 */
	public void setnextMonthPosition(int position) {
		this.clickPosition = position % 7;
	}

	/*
	 * gridview刷新到上个月
	 */
	public void setClicklastMontPosition(int position) {
		this.clickPosition = position + ((line - 1) * 7);
	}

}
