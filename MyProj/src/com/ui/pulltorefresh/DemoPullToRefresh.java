package com.ui.pulltorefresh;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.comment.Logger;
import com.example.myproj.R;
import com.json.RowObject;
import com.okhttp.AsynActionInvorker;
import com.okhttp.AsynActionInvorker.GetTextListener;
import com.ui.pulltorefresh.BaseFillRecycleViewAdapter.OnItemClickListener;
import com.ui.pulltorefresh.BaseFillRecycleViewAdapter.ViewHolder;
import com.ui.pulltorefresh.PullToRefresh.OnLoadDataListener;
import com.ui.pulltorefresh.PullToRefresh.OnRefreshListener;

public class DemoPullToRefresh extends Activity {
	
	
	private LinearLayoutManager layoutManager;
	private RecyclerView recyclerView;
	
	private PullToRefresh pulltorefresh;
	
	private Context context;
	
	private MyAdapter adapter;
	
//	private int pageNum=1;
//	
//	private int pageSize=30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_pull_to_refresh);
		context=this;
		initRecycleView();
		getData(1,15,"refresh");
		initPullToRefresh();
		
	}

	
	/**
	 * 数据列表
	 */
	public void initRecycleView() {
		recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
		// 创建一个线性布局管理器
		layoutManager = new LinearLayoutManager(context);
		// 默认是Vertical，可以不写
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		// 创建Adapter，并指定数据集
		
		adapter = new MyAdapter("",R.layout.supervision_record_item);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(ViewHolder viewHolder, RowObject row, View itemView) {
				Toast.makeText(context, row.get("COMPANY_NAME")+"", Toast.LENGTH_SHORT).show();
				pulltorefresh.endRefresh();
				//pageNum=pageNum+1;
				//getData(pageNum,30);
			}
		});
		recyclerView.setAdapter(adapter);
	}
	
	private void initPullToRefresh() {
		pulltorefresh=new PullToRefresh(this,recyclerView);
		pulltorefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void refresh(int pageNum, int pageSize) {
				// TODO Auto-generated method stub
				getData(pageNum,pageSize,"refresh");
				Logger.info("pageNum=="+pageNum+"  pageSize=="+pageSize);
			}
		});
		pulltorefresh.setOnLoadDataListener(new OnLoadDataListener() {
			
			@Override
			public void loadData(int pageNum, int pageSize) {
				// TODO Auto-generated method stub
				getData(pageNum,pageSize,"load");
				Logger.info("pageNum=="+pageNum+"  pageSize=="+pageSize);
			}
		});
	}

	
	/**
	 * 列表数据源
	 */
	public void getData(int pageNum,int pageSize,final String action){
		AsynActionInvorker ai=new AsynActionInvorker("mpMpNormalRecordAction");
		 ai.setGetTextListener(new GetTextListener() {
			@Override
			public void onResult(String text) {
				Logger.info("111111111111111111"+text);
				//adapter.addJsonData(text);
				if(action.equals("refresh")){
					pulltorefresh.refreshJsonList(text);
				}
				if(action.equals("load")){
					pulltorefresh.addJsonList(text);
				}
			}
		});
		ai.addParam("pageNum", pageNum+"");
		ai.addParam("pageSize", pageSize+"");
		ai.invorkerForString("getNormalRecordList");
	}
	
	

}
