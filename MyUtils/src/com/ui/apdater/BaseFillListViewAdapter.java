package com.ui.apdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.comment.AppFactory;
import com.comment.Logger;
import com.google.gson.reflect.TypeToken;
import com.json.JSONSerializer;
import com.json.RowObject;
import com.utils.ViewUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-11-13
 * @Descrition RecycleViewAdapter封装，根据布局的id和json中key的字段来填充数据
 */
public abstract class BaseFillListViewAdapter extends BaseAdapter {

	private Context context;
	// 数据集 List<Map<?,?>>类型
	private List<RowObject> rows;
	// 布局文件
	private int layout;
	// item监听
	private OnItemClickListener onItemClickListener;

	

	public BaseFillListViewAdapter(Context context,List<RowObject> rows, int layout) {
		super();
		this.context=context;
		this.rows = rows;
		this.layout = layout;
	}

	public BaseFillListViewAdapter(Context context,String jsonStr, int layout) {
		super();
		this.context=context;
		this.rows = JSONSerializer.getRows(jsonStr);
		this.layout = layout;
	}

	
	
	@Override
	public int getCount() {
		if(rows==null){
			return 0;
		}
		return rows.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Map<String, View> holderViews;
		if (convertView == null) {
			holder = new ViewHolder();
			holderViews=new HashMap<String, View>();
			convertView = LayoutInflater.from(context).inflate(layout, null);
			Map<String, View> views = holder.getViews();
			
			for (String id : rows.get(position).keySet()) {
				View view = convertView.findViewById(ViewUtils.getIdByName(
						context, id));
				if (canFillType(view)) {
					views.put(id, view);
				}
			}
			setConvertView(convertView,holderViews);
			holder.setHolderViews(holderViews);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holderViews = holder.getHolderViews();
			// Log.i("CosmeticsCheckItemAdapter", "===" + position + "===");
		}
		RowObject row = rows.get(position);
		for (String id : holder.getViews().keySet()) {
			View view = holder.getViews().get(id);
			String value = row.get(id) + "";
			Logger.info("id====" + id + "   value===" + value + "   i======="
					+ position);
			if (canFillType(view)) {
				// TODO
				viewToSetText(view, value);
			}
		}
		setItem(convertView, row, position,holderViews);
		convertView.setOnClickListener(new mCick(convertView, row));
		return convertView;
	}

	public abstract void setConvertView(View convertView, Map<String, View> views);


	/**
	 * 添加数据
	 * 
	 * @param jsonStr
	 */
	public void addJsonData(String jsonStr) {
		// TODO 只支持jsonObject类型
		List<RowObject> list = JSONSerializer.getRows(jsonStr);
		if (rows == null) {
			rows = new ArrayList<RowObject>();
		}
		if (list != null) {
			rows.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param jsonStr
	 */
	public void addListData(List<RowObject> list) {
		if (rows == null) {
			rows = new ArrayList<RowObject>();
		}
		if (list != null) {
			rows.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 清除数据
	 * 
	 * @param jsonStr
	 */
	public void clearData() {
		if (rows != null) {
			rows.clear();
			notifyDataSetChanged();
		}
	}


	/**
	 * ItemView初始化
	 */
	public class ViewHolder {
		// private List<RowObject> rows;
		//数据填充的View
		private Map<String, View> views=new HashMap<String, View>();
		//不需要数据填充的View
		private Map<String, View> holderViews=new HashMap<String, View>();

		public Map<String, View> getViews() {
			return views;
		}

		public void setViews(Map<String, View> views) {
			this.views = views;
		}

		public Map<String, View> getHolderViews() {
			return holderViews;
		}

		public void setHolderViews(Map<String, View> holderViews) {
			this.holderViews = holderViews;
		}
		
		

	}

	/**
	 * 选择所需填充的控件类型， 默认填充TextView、EditText类型,如需改变可以重写该方法但该View必须有setText()方法
	 * 
	 * @param view
	 * @return
	 */
	public boolean canFillType(View view) {
		boolean canfill = false;
		if (view instanceof TextView || view instanceof EditText) {
			canfill = true;
		}
		return canfill;
	}

	/**
	 * 设置文字
	 * 
	 * @param view
	 * @param text
	 */
	public void viewToSetText(View view, String text) {
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		} else if (view instanceof EditText) {
			((EditText) view).setText(text);
		} else if (view instanceof Button) {
			((Button) view).setText(text);
		} else if (view instanceof CheckBox) {
			((CheckBox) view).setText(text);
		} else if (view instanceof RadioButton) {
			((RadioButton) view).setText(text);
		} else if (view instanceof ToggleButton) {
			((ToggleButton) view).setText(text);
		} else if (customViewToSetText(view, text)) {

		} else {
			Logger.info("没有找到" + view.getClass().getName()
					+ "的类型，自定义view类型请重写customViewToSetText方法");
		}
	}

	/**
	 * 自定义View填充数据
	 * 
	 * @param view
	 * @param text
	 * @return
	 */
	public boolean customViewToSetText(View view, String text) {
		return false;
	}

	class mCick implements OnClickListener {
		private View convertView;
		private RowObject row;

		/**
		 * 回调 item 监听接口
		 */
		public mCick(View convertView, RowObject row) {
			this.convertView = convertView;
			this.row = row;
		}

		@Override
		public void onClick(View v) {
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(convertView, row, v);
			}
		}
	}

	/**
	 * item 监听接口
	 */
	public interface OnItemClickListener {
		void onItemClick(View convertView, RowObject row, View itemView);
	}

	/**
	 * item 自定义
	 * @param views 
	 */
	public abstract void setItem(View convertView, RowObject row, int position, Map<String, View> views);

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

}
