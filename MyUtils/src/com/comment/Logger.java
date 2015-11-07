package com.comment;


import android.util.Log;

public class Logger {

	public static boolean isLogger = Boolean.parseBoolean(AppFactory
			.getLOGGER());

	public static void info(String message) {
		if (message != null && isLogger) {
			Log.i(AppFactory.getLOG_TAG(), message);
		}
	}

}
