package com.comment;

import java.io.IOException;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.os.StrictMode;

@SuppressLint("NewApi")
public class AppFactory {
	//服务器地址
	private static String HOST;
	//action名称
	private static String ACTION_PREFIX;
	//版本号
	private static String VERSION;
	//数据库名称
	private static String DB_NAME;
	//数据库路径
	private static String DB_PATH;
	//数据库版本号
	private static String DB_VERSION;
	//是否打印
	private static String LOGGER;
	//打印tag
	private static String LOG_TAG;

	static {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			Properties p = new Properties();
			p.load(AppFactory.class
					.getResourceAsStream("/application.properties"));

			HOST = p.getProperty("host");
			ACTION_PREFIX = p.getProperty("actionPrefix");
			VERSION = p.getProperty("version");
			LOG_TAG = p.getProperty("logTag");
			DB_PATH= p.getProperty("dbPath");
			DB_NAME= p.getProperty("db");
			LOGGER= p.getProperty("log");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static String getHOST() {
		return HOST;
	}

	public static void setHOST(String hOST) {
		HOST = hOST;
	}

	public static String getACTION_PREFIX() {
		return ACTION_PREFIX;
	}

	public static void setACTION_PREFIX(String aCTION_PREFIX) {
		ACTION_PREFIX = aCTION_PREFIX;
	}

	public static String getVERSION() {
		return VERSION;
	}

	public static void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public static String getDB_NAME() {
		return DB_NAME;
	}

	public static void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public static String getDB_PATH() {
		return DB_PATH;
	}

	public static void setDB_PATH(String dB_PATH) {
		DB_PATH = dB_PATH;
	}

	public static String getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(String lOGGER) {
		LOGGER = lOGGER;
	}

	public static String getLOG_TAG() {
		return LOG_TAG;
	}

	public static void setLOG_TAG(String lOG_TAG) {
		LOG_TAG = lOG_TAG;
	}

	public static String getDB_VERSION() {
		return DB_VERSION;
	}

	public static void setDB_VERSION(String dB_VERSION) {
		DB_VERSION = dB_VERSION;
	}

	

}
