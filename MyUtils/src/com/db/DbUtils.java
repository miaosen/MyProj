package com.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

import com.comment.AppFactory;
import com.comment.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DbUtils {

	public DbOpenHelp dbHelp;
	public SQLiteDatabase db;
	public Context context = null;
	private final static String TAG = "DbUtils";
	private String dbName;
	private String dbPath;

	/**
	 * 默认数据库工具栏，使用src下的配置文件进行创建数据库
	 * @param context
	 */
	public DbUtils(Context context) {
		this.context = context;
		this.dbName=AppFactory.getDB_NAME();
		this.dbPath=AppFactory.getDB_PATH();
		getDbOpenHelp();
		getDataBase();
	}
	

	/**
	 * 默认数据库工具栏，使用src下的配置文件进行创建数据库
	 * @param context
	 */
	public DbUtils(Context context,String dbName,String dbPath) {
		this.context = context;
		this.dbName=dbName;
		this.dbPath=dbPath;
		getDbOpenHelp();
		getDataBase();
	}

	/**
	 * 获取 SQLiteOpenHelper
	 */
	public DbOpenHelp getDbOpenHelp() {
		dbHelp = DbOpenHelp.getInstance(context,dbName,dbPath);
		return dbHelp;
	}

	/**
	 * 获取 SQLiteDatabase
	 */
	public SQLiteDatabase getDataBase() {
		db = getDbOpenHelp().getReadableDatabase();
		return db;
	}

	/**
	 * 表格数据添加
	 * 
	 * @param tableName
	 *            表名
	 * @param values
	 *            数据
	 */
	public long addRow(String tableName, ContentValues values) {
		long success = 1;
		// 插入数据库中
		if (isExitsTable(tableName)) {
			success = db.insert(tableName, null, values);
		} else {
			success = -1;
		}
		if (success == -1) {
			Logger.info("表 " + tableName + " 数据添加失败!");
		}
		return success;
	}

	/**
	 * 查询所有数据
	 * 
	 * @param tableName
	 *            要查询的表
	 * @return Cursor资源集合
	 */
	public Cursor query(String tableName) {
		// 获取Cursor
		Cursor c = db
				.query(tableName, null, null, null, null, null, null, null);
		return c;
	}

	/**
	 * 分页查询数据
	 * 
	 * @param tableName
	 *            表名
	 * @param pageIndex
	 *            开始条数
	 * @param pageNum
	 *            条数
	 */
	public Cursor PageQuery(String tableName, String pageIndex, String pageNum) {
		int index = Integer.parseInt(pageIndex) - 1;
		Integer.parseInt(pageNum);
		// 获取Cursor
		String sql = "Select * From " + tableName + " Limit " + pageNum
				+ " Offset " + index;
		Log.i(TAG, sql);
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 */
	public void execSQL(String sql) {
		try {
			db.execSQL(sql);
			Toast.makeText(context, "执行成功！", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * map数据插入(map转ContentValues)
	 * 
	 * @param tableName
	 * @param map
	 */
	public long insertMapToTable(String tableName, Map<String, Object> map) {
		ContentValues values = new ContentValues();
		if (map != null) {
			for (String key : map.keySet()) {
				values.put(key, map.get(key) + "");
			}
		} else {
			Toast.makeText(context, "map参数为空！", Toast.LENGTH_SHORT).show();
		}
		long success = addRow(tableName, values);
		return success;
	}

	/**
	 * 根据字段查找数据库
	 * 
	 * @param tableName
	 *            表名
	 * @param rowName
	 *            字段名称
	 * @param values
	 *            字段对应的值
	 * @return
	 */
	public Cursor findByParam(String tableName, String rowName, String values) {
		Cursor cursor = db.rawQuery("select * from " + tableName + " where "
				+ rowName + "=?", new String[] { values });
		return cursor;
	}

	/**
	 * 根据多个字段查找数据库
	 * 
	 * @param tableName
	 *            表名
	 * @param rowName
	 *            字段名称
	 * @param values
	 *            字段对应的值
	 * @return
	 */
//	public Cursor findByParams(String tableName, Map<String, Object> map) {
//		Cursor cursor=null;
//		if (map == null || map.size() == 0) {
//		} else {
//			
//			for (String key : map.keySet()) {
//				String rowName = map.keySet() + "";
//				String values = map.get(key) + "";
//				
//			}
//			 cursor = db.rawQuery("select * from " + tableName
//						+ " where " + rowName + "=?", new String[] { values });
//				return cursor;
//		}
//		return null;
//	}

	/**
	 * 判断表格是否存在
	 * 
	 * @param table
	 *            表名
	 * @return 存在则返回true
	 */
	public boolean isExitsTable(String table) {
		boolean exits = false;
		String sql = "SELECT COUNT(*) FROM sqlite_master where type='table' and name='"
				+ table + "'";
		// Logger.info(sql);
		// 获取SQLiteDatabase实例
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			if (cursor.getInt(0) != 0) {
				exits = true;
			} else {
				Toast.makeText(context, table + "表格不存在！", Toast.LENGTH_SHORT)
						.show();
			}
		}
		return exits;
	}

	/**
	 * 根据Cursor返回json数据，json格式为JSONArray包含一层JSONObject
	 * 
	 * @param cursor
	 * @return
	 */
	public String cursorToJson(Cursor cursor) {
		String str = null;
		JSONArray jsonArray = new JSONArray();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				String name = cursor.getColumnName(i);
				String value = cursor.getString(i);
				if ("".equals(value) || null == value || "null".equals(value)) {
				} else {
					HashMap<String, Object> map = null;
					map = new HashMap<String, Object>();
					map.put(name, value);
					listMap.add(map);
				}
			}
			try {
				JSONObject jsonObj = null;
				jsonObj = new JSONObject();
				for (int j = 0; j < listMap.size(); j++) {
					Entry<String, Object> entry = listMap.get(j).entrySet()
							.iterator().next();
					String strname = entry.getKey();
					String strvalue = listMap.get(j).get(strname) + "";
					jsonObj.put(strname, strvalue);
				}
				jsonArray.put(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		str = jsonArray.toString();
		cursor.close();
		return str;
	}

	/**
	 * 创建事务
	 * 
	 * @return
	 */
	public DbTransaction createTrastation() {
		DbTransaction dbTransaction = new DbTransaction(this);
		return dbTransaction;
	}

	/**
	 * 创建事务
	 * 
	 * @return
	 */
	public AsyncFindDb createAsyncFindDb() {
		AsyncFindDb asyncFindDb = new AsyncFindDb(this);
		return asyncFindDb;
	}

}
