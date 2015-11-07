package com.db;


import com.comment.Logger;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class AsyncFindDb {

	/**
	 * 回调监听
	 */
	private OnLocalDataListener onLocalDataListener;

	private DbUtils dbUtils;

	public SQLiteDatabase db;
	
	private Context context;

	public AsyncFindDb(DbUtils dbUtils) {
		this.dbUtils = dbUtils;
		db = dbUtils.getDataBase();
		context=dbUtils.context;
	}

	public void findByParam(String tableName, String rowName, String values) {
		Message msg = new Message();
		String json = null;
		try {
			Cursor cursor = dbUtils.findByParam(tableName, rowName, values);
			if (cursor.getColumnCount() == 0) {
				json = "[]";
			} else {
				json = dbUtils.cursorToJson(cursor);
			}
			Logger.info("json===" + json);
			msg.what = 1;
			msg.obj = json;
		} catch (SQLException e) {
			msg.what = -1;
			e.printStackTrace();
			json = "[]";
			Toast.makeText(context, "错误"+e, Toast.LENGTH_SHORT).show();
		}
		handler.sendMessage(msg);
	}

	/**
	 * 通过一个参数查找数据
	 * 
	 * @param table
	 * @param selectParams
	 * @param params
	 * @param term
	 */
	public void query(String table, String[] selectParams, String params,
			String[] term) {
		Message msg = new Message();
		String json = null;
		try {
			Cursor cursor = db.query(table, selectParams, params, term, null,
					null, null, null);
			
			if (cursor == null || cursor.getColumnCount() == 0) {
				json = "[]";
			} else {
				json = dbUtils.cursorToJson(cursor);
			}
			Logger.info("json===" + json);
			msg.what = 1;
			msg.obj = json;
		} catch (SQLException e) {
			msg.what = -1;
			json = "[]";
			Toast.makeText(context, "错误"+e, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		handler.sendMessage(msg);
	}

	/**
	 * 通过sql语句查找数据
	 * 
	 * @param sql
	 */
	public void rowQuery(String sql) {
		Message msg = new Message();
		String json = null;
		try {
			Cursor cursor = db.rawQuery(sql, new String[] {});
			if (cursor == null || cursor.getColumnCount() == 0) {
				json = "[]";
			} else {
				json = dbUtils.cursorToJson(cursor);
			}
			Logger.info("json===" + json);
			msg.what = 1;
			msg.obj = json;
		} catch (SQLException e) {
			msg.what = -1;
			json = "[]";
			Toast.makeText(context, "错误"+e, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		handler.sendMessage(msg);
	}

	// public void findByParams(String tableName, Map<String,Object> map) {
	// if(map==null||map.size()==0){
	// }else{
	// for (String key:map.keySet()) {
	// String rowName=map.keySet()+"";
	// String values=map.get(key)+"";
	// Cursor cursor = db.rawQuery("select * from " + tableName + " where "
	// + rowName + "=?", new String[] { values });
	// return cursor;
	// }
	// }
	//
	// Cursor cursor=dbUtils.findByParam(tableName, rowName, values);
	// String json=dbUtils.cursorToJson(cursor);
	// Logger.info("json==="+json);
	// Message msg = new Message();
	// msg.what = 1;
	// msg.obj = json;
	// handler.sendMessage(msg);
	// }

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (onLocalDataListener != null) {
					onLocalDataListener.onResult((String) msg.obj);
				}
				break;
			case -1:
				Logger.info("出现异常！");
				break;
			}
		}
	};

	public OnLocalDataListener getOnLocalDataListener() {
		return onLocalDataListener;
	}

	public void setOnLocalDataListener(OnLocalDataListener onLocalDataListener) {
		this.onLocalDataListener = onLocalDataListener;
	}

	public interface OnLocalDataListener {
		public void onResult(String text);
	}
}
