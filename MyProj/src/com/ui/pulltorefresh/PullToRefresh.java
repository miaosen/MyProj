package com.ui.pulltorefresh;

import java.util.List;

import com.comment.Logger;
import com.example.myproj.R;
import com.json.RowObject;
import com.ui.apdater.BaseFillRecycleViewAdapter;
import com.utils.DPUtils;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PullToRefresh extends LinearLayout {

	private Context context;

	private LinearLayout lnRefreshHead;
	private LinearLayout lnRefreshTail;
	// 是否正在刷新
	private boolean isRefresh = false;
	// 是否正在加载更多数据
	private boolean isGetMore = false;
	// 是否到达顶部
	boolean isShowTop = false;
	// 是否到达底部
	boolean isShowBottom = false;

	// 上拉刷新接口
	private OnRefreshListener onRefreshListener;
	// RecyclerView列表数据源
	private OnLoadDataListener onLoadDataListener;

	public int dafulePageNum = 1;

	private int pageNum = 1;

	private int pageSize = 30;

	private RecyclerView recyclerView;

	private BaseFillRecycleViewAdapter adapter;

	private LinearLayoutManager layoutManager;

	public PullToRefresh(Context context, RecyclerView recyclerView) {
		super(context);
		this.context = context;
		this.recyclerView = recyclerView;
		recyclerView.setOnScrollListener(new mOnScrollListener());
		layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
		initView();
	}

	// public PullToRefresh(Context context, AttributeSet attributeSet) {
	// super(context,attributeSet);
	// this.context = context;
	// initView();
	// }

	// public void setRecyclerView(RecyclerView recyclerView) {
	// refreshLayout.addView(recyclerView,1);
	// recyclerView.setOnScrollListener(new mOnScrollListener());
	// layoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
	// }

	private void initView() {
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(getHeader());

		ViewGroup vg = (ViewGroup) recyclerView.getParent();
		vg.removeAllViews();
		recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		adapter = (BaseFillRecycleViewAdapter) recyclerView.getAdapter();
		this.addView(recyclerView);
		this.addView(getTail());
		vg.addView(this);
	}

	/**
	 * 尾部
	 */
	public LinearLayout getTail() {
		lnRefreshTail = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_tail, null);
		lnRefreshTail.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, DPUtils.dip2px(context, 50)));

		return lnRefreshTail;
	}

	/**
	 * 顶部
	 */
	public LinearLayout getHeader() {
		lnRefreshHead = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, null);
		lnRefreshHead.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, DPUtils.dip2px(context, 50)));
		// lnRefreshHead.setVisibility(View.GONE);

		return lnRefreshHead;
	}

	/**
	 * 滑动监听
	 */
	class mOnScrollListener implements OnScrollListener {

		@Override
		public void onScrolled(int arg0, int arg1) {
			// Logger.info("findFirstVisibleItemPosition==="+layoutManager.findFirstVisibleItemPosition());
			Logger.info("findFirstCompletelyVisibleItemPosition==="
					+ layoutManager.findFirstCompletelyVisibleItemPosition());
			// Logger.info("findLastVisibleItemPosition==="+layoutManager.findLastVisibleItemPosition());
			Logger.info("findLastCompletelyVisibleItemPosition==="
					+ layoutManager.findLastCompletelyVisibleItemPosition());
			Logger.info("onScrollStateChanged arg1==" + arg1);
			if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
				Logger.info("滑动到顶部");
				isShowTop = true;
			} else {
				isShowTop = false;
			}
			//TODO findLastCompletelyVisibleItemPosition有时候为-1(item比较大时)
			if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter
					.getItemCount() - 1) {
				Logger.info("滑动到底部");
				isShowBottom = true;
			} else {
				isShowBottom = false;
			}
		}

		@Override
		public void onScrollStateChanged(int scrollState) {
			// 第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
			// 第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
			// 第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
			// TODO 刷新
			if (scrollState == 0 && isShowTop) {
				Logger.info("上拉刷新");
				refresh();
			}
			if (scrollState == 0 && isShowBottom) {
				Logger.info("下拉加载更多");
				getMore();

			}
			Logger.info("onScrollStateChanged scrollState==" + scrollState);
		}
	}

	/**
	 * 下拉刷新
	 */
	private void refresh() {
		// TODO
		Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
		// if(lnRefreshHead)
		if (onRefreshListener != null) {
			pageNum = 1;
			onRefreshListener.refresh(pageNum, pageSize);
		}
		if (lnRefreshHead.getVisibility() == View.GONE) {
			lnRefreshHead.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 上拉加载更多
	 */
	private void getMore() {
		if (onLoadDataListener != null) {
			onLoadDataListener.loadData(pageNum, pageSize);
		}
		if (lnRefreshTail.getVisibility() == View.GONE) {
			lnRefreshTail.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 结束刷新
	 */
	public void endRefresh() {
		if (lnRefreshHead.getVisibility() == View.VISIBLE) {
			lnRefreshHead.setVisibility(View.GONE);
		}
		if (lnRefreshTail.getVisibility() == View.VISIBLE) {
			lnRefreshTail.setVisibility(View.GONE);
		}
	}

	/**
	 * 加载更多
	 * 
	 * @param list
	 */
	public void addList(List<RowObject> list) {
		if (list != null && list.size() > 0) {
			pageNum = pageNum + 1;
			adapter.addListData(list);
		} else {
			// TODO
			Logger.info("没有更多数据了");
		}
		endRefresh();
	}

	/**
	 * 加载更多
	 * 
	 * @param strJson
	 */
	public void addJsonList(String strJson) {
		if (strJson != null && !strJson.equals("[]")) {
			pageNum = pageNum + 1;
			adapter.addJsonData(strJson);

		} else {
			// TODO
			Logger.info("没有更多数据了");
		}
		endRefresh();

	}

	/**
	 * 刷新列表
	 * 
	 * @param list
	 */
	public void refreshList(List<RowObject> list) {
		pageNum = 1;
		adapter.clearData();
		if (list != null) {
			adapter.addListData(list);
		}
		endRefresh();
	}

	/**
	 * 刷新列表
	 * 
	 * @param strJson
	 */
	public void refreshJsonList(String strJson) {
		pageNum = 1;
		adapter.clearData();
		adapter.addJsonData(strJson);
		endRefresh();
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public OnRefreshListener getOnRefreshListener() {
		return onRefreshListener;
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	public OnLoadDataListener getOnLoadDataListener() {
		return onLoadDataListener;
	}

	public void setOnLoadDataListener(OnLoadDataListener onLoadDataListener) {
		this.onLoadDataListener = onLoadDataListener;
	}

	public interface OnRefreshListener {
		public void refresh(int pageNum, int pageSize);
	}

	public interface OnLoadDataListener {
		public void loadData(int pageNum, int pageSize);
	}

}
