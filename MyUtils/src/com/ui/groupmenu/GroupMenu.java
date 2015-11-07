package com.ui.groupmenu;

import java.util.List;

import com.myutils.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GroupMenu extends ScrollView {

	private LinearLayout ln;
	private LinearLayout lnGroup;
	private LinearLayout lnGroupTitle;
	private RelativeLayout lnGroupMenu;
	private RelativeLayout lnGroupFooter;
	private Context context;
	protected OnItemClick onItemClick;
	private String groupItemColor="#ffffff" ;
	private String groupItemLineColor="#eeeeee" ;
	
	private String flooterLineColor="#bbbbbb" ;
	private String flooterBgColor="#eeeeee" ;

	public GroupMenu(Context context, List<GroupBean> listGroup) {
		super(context);
		this.context = context;
		ln = new LinearLayout(context);
		ln.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ln.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < listGroup.size(); i++) {
			setGroup(listGroup.get(i), i);
		}
		this.addView(ln);
		this.setVerticalScrollBarEnabled(false);
	}

	/*
	 * item监听
	 */
	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	private void setGroup(GroupBean groupBean, int groupNum) {
		lnGroup = new LinearLayout(context);
		lnGroup.setOrientation(LinearLayout.VERTICAL);
		lnGroup.setBackground(getResources()
				.getDrawable(R.drawable.ic_launcher));
		lnGroup.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		// 标题
		lnGroup.addView(setTitieGroup(groupBean));

		List<ChildBean> listChild = groupBean.getChildList();
		for (int i = 0; i < listChild.size(); i++) {
			lnGroup.addView(setMenuGroup(listChild, groupNum, i));//t添加 item
		}
		if (groupBean.getFlooter() != null) {
			lnGroup.addView(setGroupFooter(groupBean));
		}
		// 加入到整个LinearLayout
		ln.addView(lnGroup);

	}

	/*
	 * 一个组的标题
	 */
	private LinearLayout setTitieGroup(GroupBean groupBean) {
		lnGroupTitle = new LinearLayout(context);
		lnGroupTitle.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getPixels(50)));
		lnGroupTitle.setBackgroundColor(Color.parseColor("#eeeeee"));
		TextView tv = new TextView(context);
		tv.setHeight(getPixels(50));
		tv.setText(groupBean.getTitle());
		tv.setTextSize(18);
		tv.setPadding(20, 0, 0, 0);
		tv.setGravity(Gravity.CENTER);
		lnGroupTitle.addView(tv);
		return lnGroupTitle;
	}

	/**
	 * @param listChild
	 * @param childNum
	 * @param groupNum
	 * @return 一个组 的item
	 */
	private RelativeLayout setMenuGroup(List<ChildBean> listChild, int groupNum,
			int childNum) {
		ChildBean childBean=listChild.get(childNum);
		lnGroupMenu = new RelativeLayout(context);
		lnGroupMenu.setBackgroundColor(Color.parseColor(groupItemColor));
		lnGroupMenu.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getPixels(50)));
		// item监听
		lnGroupMenu.setOnClickListener(new mClick(childBean, groupNum, childNum));

		// 左边图标
		ImageView iconLeft = new ImageView(context);
//		 iconLeft.setBackgroundColor(Color.parseColor("#A82884"));
		iconLeft.setBackground(getResources().getDrawable(childBean.getLeftIcon()));
		iconLeft.setId(1000 + childNum);
		RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(
				getPixels(35), getPixels(35));
		lp0.addRule(RelativeLayout.CENTER_VERTICAL);
		lp0.setMargins(getPixels(10), 0, getPixels(10), 0);

		// 内容
		TextView tv1 = new TextView(context);
		tv1.setText(childBean.getTitle());
		tv1.setTextSize(16);
		tv1.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp1.addRule(RelativeLayout.RIGHT_OF, 1000 + childNum);

		// 右边图标
		ImageView iconRight = new ImageView(context);
		iconRight.setImageDrawable(getResources().getDrawable(childBean.getRightIcon()));
		// iconRight.setBackgroundColor(Color.parseColor("#A82884"));
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, getPixels(20));
		lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp2.addRule(RelativeLayout.CENTER_VERTICAL);
		lp2.setMargins(0, 0, getPixels(10), 0);

		// 分隔线
		View v1 = new View(context);
		v1.setBackgroundColor(Color.parseColor(groupItemLineColor));
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getPixels(1f));
		lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		if(listChild.size()-1==childNum){
		}else{
			lp3.addRule(RelativeLayout.RIGHT_OF,1000+childNum);
//			lp3.setMargins(getPixels(10), 0, 0, 0);
		}
	
		

		// 添加进LinearLayout
		lnGroupMenu.addView(iconLeft,lp0);
		lnGroupMenu.addView(tv1, lp1);
		lnGroupMenu.addView(iconRight, lp2);
		lnGroupMenu.addView(v1, lp3);
		return lnGroupMenu;
	}
	
	/*
	 * 一个组的页脚
	 */
	private View setGroupFooter(GroupBean groupBean) {
		lnGroupFooter = new RelativeLayout(context);
		lnGroupFooter.setBackgroundColor(Color.parseColor(flooterBgColor));
		lnGroupFooter.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getPixels(30)));
		// 分隔线
		View v1 = new View(context);
		v1.setBackgroundColor(Color.parseColor(flooterLineColor));
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, getPixels(1f));
		lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		TextView tv = new TextView(context);
		tv.setHeight(getPixels(50));
		tv.setText(groupBean.getFlooter());
		tv.setTextSize(14);
		tv.setGravity(Gravity.CENTER);
		lnGroupFooter.addView(tv);
		lnGroupFooter.addView(v1, lp3);
		return lnGroupFooter;
	}

	/*
	 * dp转pixels
	 */
	public int getPixels(float dp) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		float valueDips = dp;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);
		return valuePixels;
	}
	

	class mClick implements OnClickListener {
		private ChildBean childBean;
		private int groupNum;
		private int childNum;

		public mClick(ChildBean childBean, int groupNum, int childNum) {
			this.childBean = childBean;
			this.groupNum = groupNum;
			this.childNum = childNum;
		}
		@Override
		public void onClick(View v) {
			if (onItemClick != null) {
				onItemClick.onItemClick(v, groupNum, childNum, childBean);
			}
		}
	}

	/*
	 * 定义监听接口
	 */
	public interface OnItemClick {
		public void onItemClick(View view, int groupPosition,
				int childPosition, ChildBean childBean);
	}
	

}
