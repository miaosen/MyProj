package com.db;


import com.comment.AppFactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelp extends SQLiteOpenHelper {

	//private static String DB_NAME = AppFactory.getDB_NAME();
	
	private static int DB_VERSION = Integer.parseInt(AppFactory.getDB_VERSION());

	private static DbOpenHelp mInstance;

	public synchronized static DbOpenHelp getInstance(Context context,String dbName,String dbPath) {
		if (mInstance == null) {
			mInstance = new DbOpenHelp(context,dbName,dbPath);
		}
		return mInstance;
	};

	public DbOpenHelp(Context context,String dbName,String dbPath) {
		super(new CustomPathDatabaseContext(context,dbPath), dbName, null, DB_VERSION);
	}

	// 回调函数，第一次创建时才会调用此函数，创建一个数据库
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("Create Database===", db.getPath());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


}
